package com.yyt.springbootdemo.repository;

import com.yyt.springbootdemo.domain.SysAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author yyt
 * @date 2021年10月28日 18:11
 */
@Repository
public interface SysAuthRepo extends JpaRepository<SysAuth, String> {

    // 根据节点的 id 查找权限信息
    SysAuth findById(int id);

    // 根据节点的全称查询
    SysAuth findByPowername(String powername);

    // 根据父节点的 id，获取其子节点的最大 id
    @Query(value = "select max(id) from sysauth where pid=?1", nativeQuery = true)
    int findMaxId(int pid);

    // 根据父节点的 id，获取其全部的子节点
    @Query(value = "select * from sysauth where pid=?1", nativeQuery = true)
    List<SysAuth> findAllChildByPid(int pid);

    // 根据节点的 id 删除节点以及子节点
    @Modifying
    @Query(value = "delete from sysauth where id=?1", nativeQuery = true)
    String deleteByChild(int id);

    // 根据 name 删除指定的节点以及子节点
    @Modifying
    @Query(value = "delete from sysauth where powername like ?1", nativeQuery = true)
    void deleteByName(String name);

    // 根据 name 查找指定的节点以及子节点，用于删除关联表中的记录
    @Query(value = "select * from sysauth where powername like ?1", nativeQuery = true)
    List<SysAuth> findAllByPowerName(String name);

    // 根据权限的 uuid 删除角色权限关联表中的对应记录
    @Modifying
    @Query(value = "delete from sysrole_sys_auths where sys_auths_uuid=?1", nativeQuery = true)
    void deleteMapTableByUuid(String uuid);
}
