package com.yyt.springbootdemo.service;

import com.yyt.springbootdemo.domain.SysLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

/**
 * @author yyt
 * @date 2021年11月01日 17:31
 * 操作日志业务层接口
 */
public interface SysLogService {

    // 保存日志
    void save(SysLog sysLog);

    // 多条件查询
    Page<SysLog> queryDynamically(Map<String, Object> reqMap, Pageable pageable);
}
