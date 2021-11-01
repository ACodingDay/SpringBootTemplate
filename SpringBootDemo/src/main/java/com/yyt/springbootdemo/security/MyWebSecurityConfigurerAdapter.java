package com.yyt.springbootdemo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author yyt
 * @date 2021年10月31日 2:42
 * 自定义安全配置类，访问任何 URL 之前都要验证权限
 */
@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class MyWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

    @Bean
    UserDetailsService UDS(){
        return new MyUserDetailsService();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder amb) throws Exception {
        // 配置认证管理器
        amb.userDetailsService(UDS()).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 配置安全过滤器
        // 关闭 CSRF 防护
        http.csrf().disable();
        http.authorizeRequests()
                // 允许访问的静态资源路径
                .antMatchers("/public/**","/custom/**", "/libs/**", "/druid/**").permitAll()
                .anyRequest().authenticated()
                .and()
                // 允许 iframe 嵌套
                .headers().frameOptions().disable()
                .and()
                .rememberMe()
                .and()
                .formLogin()
                // 设置登录页面的路径
                .loginPage("/login")
                // 登录成功后的跳转路径
                .successForwardUrl("/index")
                // 登录失败后的跳转路径
                .failureUrl("/login")
                .permitAll()
                .and()
                .logout()
                // 注销登录后的跳转路径
                .logoutSuccessUrl("/login")
                .permitAll();

        http.sessionManagement()
                // 设置单点登录
                .maximumSessions(1)
                // 强制让之前登录的同一个账号退出
                .maxSessionsPreventsLogin(false)
                // session 过期后的跳转路径
                .expiredUrl("/login");

        // 设置允许加载静态资源文件
        http.headers().contentTypeOptions().disable();

    }
}
