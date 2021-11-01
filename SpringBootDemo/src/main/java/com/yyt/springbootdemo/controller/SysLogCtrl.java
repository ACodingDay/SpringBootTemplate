package com.yyt.springbootdemo.controller;

import com.alibaba.fastjson.JSONObject;
import com.yyt.springbootdemo.annotation.LogRecordAnnotation;
import com.yyt.springbootdemo.domain.Company;
import com.yyt.springbootdemo.domain.SysLog;
import com.yyt.springbootdemo.service.SysLogService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author yyt
 * @date 2021年11月01日 17:48
 * 操作日志控制层
 */
@Controller
@RequestMapping(value = "/LogModule")
public class SysLogCtrl {

    @Resource(name = "sysLogService")
    private SysLogService slService;

    @RequestMapping("/listLog")
    public String showIndexPage() {
        return "log/ListLog.html";
    }

    @PostMapping("/queryDynamically")
    @ResponseBody
    @LogRecordAnnotation(operateType="查询", operateDesc="查询操作日志")
    public String queryDynamically(@RequestBody(required = false) Map<String, Object> reqMap){
        // 多条件分页查询
        int page = 0;
        int size = 3;
        if (reqMap != null) {
            if (reqMap.get("page") != null) {
                page = Integer.parseInt(reqMap.get("page").toString());
            }
            if (reqMap.get("size") != null) {
                size = Integer.parseInt(reqMap.get("size").toString());
            }
        }

        List<Sort.Order> orderList = new ArrayList<>();
        // 条件 1：操作时间按照降序
        orderList.add(new Sort.Order(Sort.Direction.DESC, "operateTime"));
        // 使用 queryDynamically() 方法
        Page<SysLog> allSimplePages = slService.queryDynamically(reqMap, PageRequest.of(page, size, Sort.by(orderList)));
        // 结果集合
        List<SysLog> companies = allSimplePages.getContent();
        // 转 JSON 格式
        JSONObject result = new JSONObject();
        // “rows” 和 “total” 这两个属性是为了前端列表插件 “bootstrap-table” 服务的
        result.put("rows", companies);
        result.put("total", allSimplePages.getTotalElements());
        return result.toString();
    }
}
