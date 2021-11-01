package com.yyt.springbootdemo.security;

import com.yyt.springbootdemo.domain.SysAuth;
import com.yyt.springbootdemo.domain.SysUser;
import com.yyt.springbootdemo.service.SysAuthService;
import com.yyt.springbootdemo.service.SysRoleService;
import com.yyt.springbootdemo.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yyt
 * @date 2021年10月31日 2:00
 * 自定义用户加载类，用于验证用户，验证通过后，加载其拥有的全部权限
 */
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Resource(name = "sysAuthService")
    private SysAuthService sas;

    @Resource(name = "sysUserService")
    private SysUserService sus;

    @Autowired
    private HttpSession session;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        // 验证方式：三者之一皆可
        SysUser sysUser = sus.findByUsernameOrUseremailOrUsermobile(s, s, s);
        if(sysUser == null){
            throw new UsernameNotFoundException("用户名/密码错误");
        }

        // 账号用户存在，则创建 session
        session.setAttribute("userInfo", sysUser);

        // 获取用户的对应角色的权限
        List<SysAuth> sysAuthList = new ArrayList<>();
        if ("超级管理员".equals(sysUser.getSysRole().getRolename())){
            sysAuthList = sas.findAll();
        }else {
            for (SysAuth sysAuth : sysUser.getSysRole().getSysAuths()){
                sysAuthList.add(sysAuth);
            }
        }

        // 将权限信息添加到 GrantedAuthority 对象中
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
        for (SysAuth sysAuth : sysAuthList){
            if (sysAuth != null && sysAuth.getPowername() != null){
                GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(sysAuth.getPowername());
                grantedAuthorityList.add(grantedAuthority);
            }
        }

        return new User(sysUser.getUsername(), sysUser.getPassword(), grantedAuthorityList);
    }
}
