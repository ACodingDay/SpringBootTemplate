package com.yyt.springbootdemo.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.yyt.springbootdemo.domain.SysAuth;
import com.yyt.springbootdemo.domain.SysRole;
import com.yyt.springbootdemo.domain.Ztree;
import com.yyt.springbootdemo.repository.SysAuthRepo;
import com.yyt.springbootdemo.repository.SysRoleRepo;
import com.yyt.springbootdemo.service.SysAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yyt
 * @date 2021年10月28日 20:32
 * 权限业务层实现类
 */
@Service("sysAuthService")
public class SysAuthServiceImpl implements SysAuthService {

    @Autowired
    private SysAuthRepo saRepo;

    @Autowired
    private SysRoleRepo srRepo;

    @Override
    public List<SysAuth> findAll() {
        return saRepo.findAll();
    }

    @Override
    public List<Ztree> findAllTozTree(String roleid) {
        List<Ztree> listZtree = new ArrayList<>();
        // 根据角色的 uuid 获取角色对应的权限
        SysRole sysRole = srRepo.findByUuid(roleid);
        // 查询全部的权限
        List<SysAuth> sysAuths = findAll();
        // 遍历全部权限，用来设定 open 和 checked 属性
        for (SysAuth sa : sysAuths){
            // 遍历角色对应的权限，比较是否在全部权限之中
            boolean ifChecked = false;
            if (!"nouuid".equals(roleid)){
                String strRoleAuthName = sa.getPowername();
                for (SysAuth roleAuth : sysRole.getSysAuths()){
                    if(roleAuth.getPowername().equals(strRoleAuthName)){
                        ifChecked = true;
                        break;
                    }
                }
            }
            Ztree z = new Ztree();
            z.id = sa.getId();
            z.pId = sa.getPid();
            z.name = sa.getTreename();
            z.open = true;
            z.checked = ifChecked;
            listZtree.add(z);
        }
        return listZtree;
    }

    @Override
    public String saveChildAuth(int id, String childname) {
        // 一级权限
        if (id == 0) {
            SysAuth sysAuth_Child = saRepo.findByPowername(childname);
            // 子节点已经存在
            if(sysAuth_Child != null){
                JSONObject result = new JSONObject();
                result.put("msg", "exist");
                return result.toJSONString();
            }else{
                // 不存在
                SysAuth newAuth = new SysAuth();
                newAuth.setPowername(childname);
                newAuth.setPid(id);
                newAuth.setId(findMaxId(id));
                newAuth.setTreename(childname);
                saRepo.save(newAuth);

                JSONObject result = new JSONObject();
                result.put("msg", "ok");
                return result.toJSONString();
            }
        }else {
            // 非一级权限，先找到父节点
            SysAuth sysAuth_Parent = saRepo.findById(id);
            String strChildName = sysAuth_Parent.getPowername() + "_" + childname;
            SysAuth sysAuth_Child = saRepo.findByPowername(strChildName);
            // 子节点已经存在
            if(sysAuth_Child != null){
                JSONObject result = new JSONObject();
                result.put("msg", "exist");
                return result.toJSONString();
            }else{
                // 不存在
                SysAuth newAuth = new SysAuth();
                newAuth.setPowername(strChildName);
                newAuth.setPid(id);
                newAuth.setId(findMaxId(id));
                newAuth.setTreename(childname);
                saRepo.save(newAuth);

                JSONObject result = new JSONObject();
                result.put("msg", "ok");
                return result.toJSONString();
            }
        }
    }

    @Override
    public SysAuth findById(int id) {
        return saRepo.findById(id);
    }

    @Override
    public SysAuth findByPowername(String powername) {
        return saRepo.findByPowername(powername);
    }

    @Override
    public int findMaxId(int pid) {
        // 判断是否有子节点
        List<SysAuth> sysAuths = saRepo.findAllChildByPid(pid);
        // 没有子节点
        if(sysAuths.size() == 0){
            // 自行设定的规则
            return pid * 10 + 1;
        }else{
            return saRepo.findMaxId(pid) + 1;
        }
    }

    @Override
    @Transactional
    public String deleteByChild(int id) {
        SysAuth sysAuth = saRepo.findById(id);
        // 先批量删除中间表的对应记录
        List<SysAuth> sysAuthList = saRepo.findAllByPowerName(sysAuth.getPowername() + "%");
        for(SysAuth sa : sysAuthList){
            saRepo.deleteMapTableByUuid(sa.getUuid());
        }
        // 再从权限表中批量删除节点及其子节点
        saRepo.deleteByName(sysAuth.getPowername() + "%");
        // 为 AOP 日志准备
        return JSONObject.toJSONString(sysAuth);
    }

    @Override
    @Transactional
    public void deleteByName(String name) {
        saRepo.deleteByName(name + "%");
    }

    @Override
    public void editRole(String uuid, String authsinfo) {
        // 得到角色信息
        SysRole sysRole = srRepo.findByUuid(uuid);
        // 根据勾选的权限节点 id，得到对应的权限对象，并赋值
        List<SysAuth> list = new ArrayList<>();
        String[] arrAuthid = authsinfo.split("\\$");
        for (String s : arrAuthid) {
            SysAuth sysAuth = saRepo.findById(Integer.parseInt(s));
            list.add(sysAuth);
        }
        sysRole.setSysAuths(list);
        // 保存更新信息
        srRepo.save(sysRole);
    }
}
