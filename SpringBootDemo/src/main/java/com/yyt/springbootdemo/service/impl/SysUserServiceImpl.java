package com.yyt.springbootdemo.service.impl;

import cn.hutool.core.util.StrUtil;
import com.yyt.springbootdemo.domain.SysRole;
import com.yyt.springbootdemo.domain.SysUser;
import com.yyt.springbootdemo.repository.SysRoleRepo;
import com.yyt.springbootdemo.repository.SysUserRepo;
import com.yyt.springbootdemo.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author yyt
 * @date 2021年10月28日 22:22
 * 账号业务层实现类
 */
@Service("sysUserService")
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysRoleRepo srRepo;

    @Autowired
    private SysUserRepo suRepo;

    @Override
    public void save(SysUser sysUser) {
        // 密码加密，不使用 MD5
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashPassword = passwordEncoder.encode(sysUser.getPassword());
        sysUser.setPassword(hashPassword);
        // 根据账号对应的角色 id，得到角色信息
        SysRole sysRole = srRepo.findByUuid(sysUser.getSysroleid());
        sysUser.setSysrolename(sysRole.getRolename());
        sysUser.setSysRole(sysRole);
        // 保存更新
        suRepo.save(sysUser);
    }

    @Override
    public SysUser findByUsernameOrUseremailOrUsermobile(String username, String email, String mobile) {
        return suRepo.findByUsernameOrUseremailOrUsermobile(username, email, mobile);
    }

    @Override
    public Page<SysUser> queryDynamically(Map<String, Object> reqMap, Pageable pageable) {
        // lambda 表达式重写其中的 toPredicate() 方法
        Specification<SysUser> querySpec = (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            // 不为 null 且不是空字符串
            // !(reqMap.get("username") == null || "".equals(reqMap.get("username").toString()))
            if (reqMap.get("username") != null && !StrUtil.hasEmpty(reqMap.get("username").toString())){
                // 账号名称，模糊查询 like，前后都加 % 就是全模糊
                predicates.add(criteriaBuilder.like(root.get("username"), "%" + reqMap.get("username").toString()+"%"));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        return suRepo.findAll(querySpec, pageable);
    }

    @Override
    public boolean validateUsername(String username) {
        int num = suRepo.validateUsername(username);
        // 等于 0 就返回 true，不等就返回 false
        return num == 0;
    }

    @Override
    public boolean validateEmail(String email) {
        int num = suRepo.validateEmail(email);
        return num == 0;
    }

    @Override
    public boolean validateMobile(String mobile) {
        int num = suRepo.validateMobile(mobile);
        return num == 0;
    }
}
