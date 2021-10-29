package com.yyt.springbootdemo.service.impl;

import com.yyt.springbootdemo.domain.SysRole;
import com.yyt.springbootdemo.repository.SysRoleRepo;
import com.yyt.springbootdemo.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author yyt
 * @date 2021年10月28日 22:01
 * 角色业务层实现类
 */
@Service("sysRoleService")
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private SysRoleRepo srRepo;

    @Override
    public SysRole findByUUID(String uuid) {
        return srRepo.findByUuid(uuid);
    }

    @Override
    public List<SysRole> findAll() {
        return srRepo.findAll();
    }

    @Override
    public void save(SysRole sysRole) {
        srRepo.save(sysRole);
    }

    @Override
    @Transactional
    public void deleteByUUID(String uuid) {
        srRepo.deleteByUuid(uuid);
    }

    @Override
    @Transactional
    public void deleteMapTableByUUID(String uuid) {
        srRepo.deleteMapTableByUuid(uuid);
    }
}
