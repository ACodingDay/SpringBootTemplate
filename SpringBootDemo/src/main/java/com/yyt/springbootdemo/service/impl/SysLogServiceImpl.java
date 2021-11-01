package com.yyt.springbootdemo.service.impl;

import cn.hutool.core.util.StrUtil;
import com.yyt.springbootdemo.domain.SysLog;
import com.yyt.springbootdemo.repository.SysLogRepo;
import com.yyt.springbootdemo.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author yyt
 * @date 2021年11月01日 17:35
 * 操作日志业务层接口的实现类
 */
@Service("sysLogService")
public class SysLogServiceImpl implements SysLogService {

    @Autowired
    private SysLogRepo slRepo;

    @Override
    public void save(SysLog sysLog) {
        slRepo.save(sysLog);
    }

    @Override
    public Page<SysLog> queryDynamically(Map<String, Object> reqMap, Pageable pageable) {
        // lambda 表达式重写其中的 toPredicate() 方法
        Specification<SysLog> specification = (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            // 设置查询条件
            if (!StrUtil.hasEmpty(reqMap.get("username").toString())){
                // 用户名称，精确查询
                predicates.add(criteriaBuilder.equal(root.get("username"), reqMap.get("username").toString()));
            }
            if (!"全部".equals(reqMap.get("operateType").toString())){
                // 操作类型，精确查询 equal
                predicates.add(criteriaBuilder.equal(root.get("operateType"), reqMap.get("operateType").toString()));
            }
            if (!"全部".equals(reqMap.get("operateDesc").toString())){
                // 操作描述，模糊查询 like
                predicates.add(criteriaBuilder.like(root.get("operateDesc"), "%" + reqMap.get("operateDesc").toString() + "%"));
            }
            // 还可以设置操作时间区间段条件
            // 返回
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        // 这里使用 JpaSpecificationExecutor 接口的分页查询方法
        return slRepo.findAll(specification, pageable);
    }
}
