package com.yyt.springbootdemo.controller;

import com.yyt.springbootdemo.annotation.LogRecordAnnotation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author yyt
 * @date 2021年10月31日 1:59
 */
@Controller
public class LoginCtrl {

    @RequestMapping(value = "/login")
    public String login() {
        // 登录界面
        return "/login.html";
    }

    @RequestMapping(value = "/index")
    @LogRecordAnnotation(operateType="登录", operateDesc="用户登录后台管理系统")
    public String index() {
        // 首页界面
        return "/index.html";
    }
}
