package com.aop.interceptors.demo.persistence.repository;


import com.aop.interceptors.demo.persistence.entity.ActivityLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface ActivityLogRepo extends JpaRepository <ActivityLog, String >{


}
