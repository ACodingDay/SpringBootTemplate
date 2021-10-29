package com.yyt.springbootdemo.service;

import com.yyt.springbootdemo.domain.SysRole;

import java.util.List;

/**
 * @author yyt
 * @date 2021年10月28日 20:17
 * 角色业务层接口
 */
public interface SysRoleService {
    // 根据 uuid 查找
    SysRole findByUUID(String uuid);

    // 查找全部
    List<SysRole> findAll();

    // 保存角色
    void save(SysRole sysRole);

    // 根据 uuid 删除
    void deleteByUUID(String uuid);

    // 根据角色的 uuid 删除角色权限关联表中对应的记录
    void deleteMapTableByUUID(String uuid);
}
