package com.aop.interceptors.demo.advise;

import com.aop.interceptors.demo.persistence.entity.ActivityLog;
import com.aop.interceptors.demo.persistence.repository.ActivityLogRepo;
import com.aop.interceptors.demo.utils.ObjectMapperUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@Aspect
@RequiredArgsConstructor
public class LogginAdvice {

    private final ActivityLogRepo repo;

    @Pointcut(value="execution(* com.aop.interceptors.demo.web.CustomerController.*(..) )")
    public void myPointcut() {     }

    @Around("myPointcut()")
    public Object applyLogger(ProceedingJoinPoint pjp) throws Throwable {
        long start = System.nanoTime();
        Object retval = pjp.proceed();
        long end = System.nanoTime();
        //method returns everything you need to get the actual class name, method name
        String methodName = pjp.getSignature().getName();
        String className = pjp.getTarget().getClass().toString();
        String arg = pjp.getArgs().toString();
        ObjectMapper ob = new ObjectMapper();
        String reqVal = ob.writeValueAsString(pjp.getArgs());
        log.info("Execution of " + className+ "Method:" + methodName + " took " +
                TimeUnit.NANOSECONDS.toMillis(end - start) + " ms" + arg);

        long executionTime = end - start;
        saveLog(className, methodName, reqVal, executionTime);
        return retval;
    }

    // execute it in a separate thread and submit this method's logic as a separate path of execution
    @Async
    protected void saveLog(String className, String methodName, String request, long executionTime){
        ActivityLog activity = new ActivityLog();
        activity.setClassName(className);
        activity.setMethodName(methodName);
        activity.setRequest(request);
        activity.setExecutionTime(executionTime);

        try{
            repo.save(activity);
        }catch (Exception ex){
            log.error("Database error:" + ex);
        }
    }
    @Before(value = "execution(* com.aop.interceptors.demo.web.CustomerController.*(..) )")
    public void beforeAdvice(JoinPoint joinPoint) {
        // Jackson ObjectMapper parse(split) JSON from a string and create a Java object
        //ObjectMapper provides functionality for reading and writing JSON, either to and from basic POJOs
        String value = ObjectMapperUtils.map(joinPoint.getSignature(), String.class);
        Object[] arr = joinPoint.getArgs();
        log.info("Before Execution " + joinPoint.getTarget().getClass().toString() + " : " + joinPoint.getSignature().getName()
                + "()" + "arguments : " + value);


    }
    @AfterReturning(pointcut = "execution(* com.aop.interceptors.demo.web.CustomerController.*(..) )", returning = "result")
    public void AfterAdvice(JoinPoint joinPoint, Object result) {
        String className = joinPoint.getTarget().getClass().toString();
        String methodName = joinPoint.getSignature().getName();
        String value = ObjectMapperUtils.map(joinPoint.getSignature(), String.class);
        log.info("After Execution : Class Name" + className + " : " + methodName + "()" + "Response : "
            + value    );

    }
    @Before(value = "execution(*  com.aop.interceptors.demo.service.CustomerService.*(..) )")
    public void beforeAdviceForExecute(JoinPoint joinPoint) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String value = mapper.writeValueAsString(joinPoint.getArgs());
        log.info("Before Execution of Service-> Class Name :" + joinPoint.getTarget().getClass().toString() + " || Method Name: " + joinPoint.getSignature().getName()
                + "()" + "  || Arguments : " + value);
    }
    @AfterReturning(pointcut = "execution(* com.aop.interceptors.demo.service.CustomerService.*(..) )", returning = "result")
    public void AfterAdviceExecute(JoinPoint joinPoint, Object result) throws JsonProcessingException {
        String className = joinPoint.getTarget().getClass().toString();
        ObjectMapper mapper = new ObjectMapper();
        String value = mapper.writeValueAsString(result);
        String methodName = joinPoint.getSignature().getName();
        log.info("After Execution of Service-> Class Name :" + className + " || Method Name: " + methodName + "() " + "|| Response : "
                + value);
    }
}
