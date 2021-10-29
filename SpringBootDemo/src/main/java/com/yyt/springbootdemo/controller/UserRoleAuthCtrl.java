package com.yyt.springbootdemo.controller;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.yyt.springbootdemo.domain.SysRole;
import com.yyt.springbootdemo.domain.SysUser;
import com.yyt.springbootdemo.domain.Ztree;
import com.yyt.springbootdemo.service.SysAuthService;
import com.yyt.springbootdemo.service.SysRoleService;
import com.yyt.springbootdemo.service.SysUserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author yyt
 * @date 2021年10月28日 23:58
 * 用户-角色-权限控制层
 */
@Controller
@RequestMapping(value = "/security")
public class UserRoleAuthCtrl {

    @Resource(name = "sysAuthService")
    private SysAuthService sas;

    @Resource(name = "sysRoleService")
    private SysRoleService srs;

    @Resource(name = "sysUserService")
    private SysUserService sus;

    @RequestMapping(value = "/ListUserRoleAuth")
    public String findAllRole(Model model) {
        /**
         * 查询全部数据，然后跳转页面
         */
        List<SysRole> list = srs.findAll();
        model.addAttribute("sysRoles", list);
        return "/security/ListUserRoleAuth.html";
    }

    @PostMapping("/listuser")
    @ResponseBody
    public String queryDynamically(@RequestBody Map<String, Object> reqMap) {
        // 多条件分页查询
        int page = 0;
        int size = 3;
        if (!StrUtil.hasEmpty(reqMap.get("page").toString())) {
            page = Integer.parseInt(reqMap.get("page").toString());
        }
        if (!StrUtil.hasEmpty(reqMap.get("size").toString())) {
            size = Integer.parseInt(reqMap.get("size").toString());
        }
        List<Sort.Order> orderList = new ArrayList<>();
        // 条件 1
        orderList.add(new Sort.Order(Sort.Direction.DESC, "username"));
        // 使用 queryDynamically() 方法
        Page<SysUser> allSimplePages = sus.queryDynamically(reqMap, PageRequest.of(page, size, Sort.by(orderList)));
        // 结果集合
        List<SysUser> listsysUser = allSimplePages.getContent();
        // 转 JSON 格式
        JSONObject result = new JSONObject();
        // “rows” 和 “total” 这两个属性是为了前端列表插件 “bootstrap-table” 服务的
        result.put("rows", listsysUser);
        result.put("total", allSimplePages.getTotalElements());
        return result.toJSONString();
    }

    @RequestMapping("/listauth")
    @ResponseBody
    public List<Ztree> findAllTozTree(String roleid){
        // 以树形节点形式返回全部权限
        return sas.findAllTozTree(roleid);
    }

    @PostMapping("/saveRole")
    @ResponseBody
    public String save(SysRole sysRole) {
        // 保存角色
        srs.save(sysRole);
        return "OK";
    }

    @PostMapping("/deleteRole")
    @ResponseBody
    public String delete(String uuid) {
        // 删除角色
        srs.deleteByUUID(uuid);
        srs.deleteMapTableByUUID(uuid);
        return "OK";
    }

    @PostMapping("/saveChildAuth")
    @ResponseBody
    public String saveChildAuth(@RequestParam int id, String name) {
        // 保存子节点
        return sas.saveChildAuth(id, name);
    }

    @PostMapping("/deleteByChild")
    @ResponseBody
    public String deleteByChild(@RequestParam int id) {
        // 根据 id 删除节点以及其子节点
        sas.deleteByChild(id);
        return "OK";
    }

    @PostMapping("/editRole")
    @ResponseBody
    public String editRole(@RequestParam String uuid, String authinfo) {
        // 保存角色对应的权限信息
        sas.editRole(uuid, authinfo);
        return "OK";
    }

    @RequestMapping("/findAllRoles")
    @ResponseBody
    public List<SysRole> findAllRoles(){
        // 查询全部的角色
        List<SysRole> list = srs.findAll();
        return list;
    }

    @PostMapping("/validateUsername")
    @ResponseBody
    public String validateUsername(@RequestParam String username){
        // 验证用户名是否唯一，已经存在就返回 false，否则返回 true
        boolean result = sus.validateUsername(username);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("valid", result);
        return jsonObject.toJSONString();
    }

    @PostMapping("/validateEmail")
    @ResponseBody
    public String validateEmail(@RequestParam String useremail){
        // 验证邮箱是否唯一
        boolean result = sus.validateEmail(useremail);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("valid", result);
        return jsonObject.toJSONString();
    }

    @PostMapping("/validateMobile")
    @ResponseBody
    public String validateMobile(@RequestParam String usermobile){
        // 验证手机号是否唯一
        boolean result = sus.validateUsername(usermobile);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("valid", result);
        return jsonObject.toJSONString();
    }

    @RequestMapping(value = "/toAddSysUser")
    public String toAddSysUser() {
        // 跳转到增加用户界面
        return "/security/AddSysUser.html";
    }

    @RequestMapping("/saveSysUser")
    @ResponseBody
    public String saveSysUser(SysUser sysUser){
        // 保存账户
        sus.save(sysUser);
        return "OK";
    }

}
