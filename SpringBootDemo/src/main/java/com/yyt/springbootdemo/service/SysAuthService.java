package com.yyt.springbootdemo.service;

import com.yyt.springbootdemo.domain.SysAuth;
import com.yyt.springbootdemo.domain.Ztree;

import java.util.List;

/**
 * @author yyt
 * @date 2021年10月28日 20:01
 */
public interface SysAuthService {
    // 查找全部权限
    List<SysAuth> findAll();

    // 查找全部权限，给 zTree 插件准备的集合
    List<Ztree> findAllTozTree(String roleid);

    // 添加节点的子节点
    String saveChildAuth(int id, String childname);

    // 根据 id 查找对应的权限信息
    SysAuth findById(int id);

    // 根据节点全称查找
    SysAuth findByPowername(String powername);

    // 根据父节点的 id，获取其子节点的最大 id
    int findMaxId(int pid);

    // 根据节点的 id 删除节点以及子节点
    String deleteByChild(int id);

    // 根据 name 删除指定的节点以及子节点
    void deleteByName(String name);

    // 给选定的角色赋予权限，其中 authsinfo 是以 $ 分割的节点 id 字符串
    void editRole(String uuid, String authsinfo);
}
