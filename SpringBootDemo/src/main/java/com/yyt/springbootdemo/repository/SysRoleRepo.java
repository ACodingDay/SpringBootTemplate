package com.yyt.springbootdemo.repository;

import com.yyt.springbootdemo.domain.SysRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author yyt
 * @date 2021年10月28日 18:39
 * 角色数据仓库接口
 */
@Repository
public interface SysRoleRepo extends JpaRepository<SysRole, String> {
    // 根据 uuid 查找角色信息
    SysRole findByUuid(String uuid);

    // 验证账号是否已经存在（已经存在就返回 0，否则返回 1）
    @Query(value = "select 1 from sysuser where username=?1 limit 1", nativeQuery = true)
    int validateUsername(String username);

    // 根据 uuid 删除角色
    @Modifying
    @Query(value = "delete from sysrole where uuid=?1", nativeQuery = true)
    void deleteByUuid(String uuid);

    // 根据 uuid 删除角色权限关联表中的对应记录
    @Modifying
    @Query(value = "delete from sysrole_sys_auths where sys_role_uuid=?1", nativeQuery = true)
    void deleteMapTableByUuid(String uuid);
}
