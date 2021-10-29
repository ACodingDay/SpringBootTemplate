package com.yyt.springbootdemo.service;

import com.yyt.springbootdemo.domain.SysUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

/**
 * @author yyt
 * @date 2021年10月28日 20:22
 * 账号业务层接口
 */
public interface SysUserService {
    // 保存新账号
    void save(SysUser sysUser);

    // 根据条件查询账号
    SysUser findByUsernameOrUseremailOrUsermobile(String username, String email, String mobile);

    // 带查询条件的分页查询
    Page<SysUser> queryDynamically(Map<String, Object> reqMap, Pageable pageable);

    // 验证账号唯一性（如果已经存在则返回 false，否则返回 true）
    boolean validateUsername(String username);

    // 验证邮箱唯一性（如果已经存在则返回 false，否则返回 true）
    boolean validateEmail(String email);

    // 验证手机号唯一性（如果已经存在则返回 false，否则返回 true）
    boolean validateMobile(String mobile);
}
