package com.yyt.springbootdemo.repository;

import com.yyt.springbootdemo.domain.SysLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author yyt
 * @date 2021年11月01日 17:29
 */
@Repository
public interface SysLogRepo extends JpaRepository<SysLog, String>, JpaSpecificationExecutor {
}
