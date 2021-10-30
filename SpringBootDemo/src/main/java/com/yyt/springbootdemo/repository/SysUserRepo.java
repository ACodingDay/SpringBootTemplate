package com.yyt.springbootdemo.repository;

import com.yyt.springbootdemo.domain.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author yyt
 * @date 2021年10月28日 18:59
 * 账号数据仓库接口
 */
@Repository
public interface SysUserRepo extends JpaRepository<SysUser, String>, JpaSpecificationExecutor {

    // 根据用户名或邮箱或手机号查找用户信息
    SysUser findByUsernameOrUseremailOrUsermobile(String username, String email, String mobile);

    // 验证账号是否已经存在（已经存在就返回 0，否则返回 1）
    @Query(value = "select count(*) from sysuser where username=?1", nativeQuery = true)
    int validateUsername(String username);

    // 验证邮箱是否已经存在（已经存在就返回 0，否则返回 1）
    @Query(value = "select count(*) from sysuser where useremail=?1", nativeQuery = true)
    int validateEmail(String email);

    // 验证手机号是否已经存在（已经存在就返回 0，否则返回 1）
    @Query(value = "select count(*) from sysuser where usermobile=?1", nativeQuery = true)
    int validateMobile(String mobile);
}
