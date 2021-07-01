package com.aop.interceptors.demo.persistence.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "activity_log")
public class ActivityLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "className")
    private String className;
    @Column(name = "methodName")
    private String methodName;
    @Column(name = "request")
    private String request;
    @Column(name = "executionTime")
    private long executionTime;
}
