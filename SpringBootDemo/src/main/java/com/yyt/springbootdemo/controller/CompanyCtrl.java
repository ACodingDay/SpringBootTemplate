package com.yyt.springbootdemo.controller;

import com.alibaba.fastjson.JSONObject;
import com.yyt.springbootdemo.annotation.LogRecordAnnotation;
import com.yyt.springbootdemo.domain.Company;
import com.yyt.springbootdemo.domain.EChartsData;
import com.yyt.springbootdemo.service.CompanyService;
import com.yyt.springbootdemo.utils.ReceiveUploadFileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yyt
 * @date 2021年10月18日 16:21
 * 公司控制层
 */
@Controller
@RequestMapping(value = "/CompanyModule")
public class CompanyCtrl {
    // 业务层注入
    @Resource(name = "companyService")
    private CompanyService csi;

    @Autowired
    private ReceiveUploadFileUtil ruf;

    @PostMapping("/save")
    @ResponseBody
    @LogRecordAnnotation(operateType="新增", operateDesc="新增了一家公司")
    public void save(Company company) {
        csi.save(company);
    }

    @GetMapping("/delete")
    @ResponseBody
    @LogRecordAnnotation(operateType="删除", operateDesc="删除了一家公司")
    public void delete(@RequestParam String uuid) {
        csi.delete(uuid);
    }

    @RequestMapping("/findAll")
    @ResponseBody
    @LogRecordAnnotation(operateType="查询", operateDesc="查询了所有的公司")
    public List<Company> findAll() {
        // 不限制访问方式，GET 或 POST 都可以
        return csi.findAll();
    }

    @RequestMapping("/validateEmail")
    @ResponseBody
    public String validateEmail(@RequestParam String inputConEmail) {
       boolean isValid = csi.validateEmail(inputConEmail);
       JSONObject result = new JSONObject();
       result.put("valid", isValid);
       return result.toString();
    }

    @PostMapping("/upload")
    @ResponseBody
    @LogRecordAnnotation(operateType="其他", operateDesc="上传了文件")
    public String uploadFile(@RequestParam MultipartFile file) {
        /**
         * 接收上传文件，返回存储路径名
         */
        return ruf.receiveFileName(file, "test");
    }

    @PostMapping("/findAllSimplePage")
    @ResponseBody
    public Page<Company> findAllSimplePage(@RequestParam(name = "page", required = false, defaultValue = "1") int page,
                                           @RequestParam(name = "size", required = false, defaultValue = "2") int size) {
        /**
         * @param page 查询几页
         * @param size 每一页显示多少条记录
         * 简单分页查询 1
         * 集合中存储的是排序条件，构造方法：排序规则，排序对象
         */
        List<Sort.Order> orderList = new ArrayList<>();
        // 条件 1：公司名按照降序
        orderList.add(new Sort.Order(Sort.Direction.DESC, "comname"));
        // 条件 2：公司地址按照升序
        orderList.add(new Sort.Order(Sort.Direction.ASC, "comaddress"));
        return csi.findAllSimplePages(PageRequest.of(page, size, Sort.by(orderList)));
    }

    @PostMapping("/findAllSimplePageMap")
    @ResponseBody
    public String findAllSimplePageMap(@RequestBody(required = false) Map<String, Object> reqMap) {
        // 简单分页查询 2
        int page = 0;
        int size = 3;
        if (reqMap != null) {
            if (reqMap.get("page").toString() != null) {
                page = Integer.parseInt(reqMap.get("page").toString());
            }
            if (reqMap.get("size").toString() != null) {
                size = Integer.parseInt(reqMap.get("size").toString());
            }
        }
        List<Sort.Order> orderList = new ArrayList<>();
        // 条件 1：公司名按照降序
        orderList.add(new Sort.Order(Sort.Direction.DESC, "comname"));
        // 条件 2：公司地址按照升序
        orderList.add(new Sort.Order(Sort.Direction.ASC, "comaddress"));
        Page<Company> allSimplePages = csi.findAllSimplePages(PageRequest.of(page, size, Sort.by(orderList)));
        // 结果集合
        List<Company> companies = allSimplePages.getContent();
        // 转 JSON 格式
        JSONObject result = new JSONObject();
        // “rows” 和 “total” 这两个属性是为了前端列表插件 “bootstrap-table” 服务的
        result.put("rows", companies);
        result.put("total", allSimplePages.getTotalElements());
        return result.toString();
    }

    @PostMapping("/queryDynamically")
    @ResponseBody
    @LogRecordAnnotation(operateType="查询", operateDesc="查询符合条件的公司")
    public String queryDynamically(@RequestBody(required = false) Map<String, Object> reqMap) {
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
        // 条件 1：公司名按照降序
        orderList.add(new Sort.Order(Sort.Direction.DESC, "comname"));
        // 条件 2：公司地址按照升序
        orderList.add(new Sort.Order(Sort.Direction.ASC, "comaddress"));
        // 使用 queryDynamically() 方法
        Page<Company> allSimplePages = csi.queryDynamically(reqMap, PageRequest.of(page, size, Sort.by(orderList)));
        // 结果集合
        List<Company> companies = allSimplePages.getContent();
        // 转 JSON 格式
        JSONObject result = new JSONObject();
        // “rows” 和 “total” 这两个属性是为了前端列表插件 “bootstrap-table” 服务的
        result.put("rows", companies);
        result.put("total", allSimplePages.getTotalElements());
        return result.toString();
    }

    @PostMapping("/echarts")
    @ResponseBody
    @LogRecordAnnotation(operateType="查询", operateDesc="查询了公司图表数据")
    public Map<String, List<EChartsData>> echart(){
        /**
         * 为 ECharts 图表展示，准备数据
         */
        Map<String, List<EChartsData>> map = new HashMap<>();
        // 员工数量集合
        List<EChartsData> listEmployeenNmber = new ArrayList<>();
        // 公司产值集合
        List<EChartsData> listTotalOut = new ArrayList<>();
        List<Company> list = csi.findAll();
        // 组装数据
        for (Company company : list){
            listEmployeenNmber.add(new EChartsData(company.getComname(), company.getEmployeenumber()));
            listTotalOut.add(new EChartsData(company.getComname(), company.getTotaloutput()));
        }
        // 赋值给 map 集合
        map.put("listPerson", listEmployeenNmber);
        map.put("listOutput", listTotalOut);
        return map;
    }

    @RequestMapping("/showP1")
    public String showPublicHtml() {
        // 测试返回公共文件夹 public 中的页面
        return "redirect:/demo.html";
    }

    @RequestMapping("/showP2")
    public String showPrivateHtml() {
        // 测试返回安全文件夹 templates 中的页面
        return "/mydemo1.html";
    }

    @RequestMapping("/listCompany")
    public String showTemplatesHtml() {
        // 返回公司信息表格
        return "/company/ListCompany.html";
    }

    @RequestMapping("/addCompany")
    public String addCompanyHtml() {
        // 返回新增公司信息表单
        return "/company/AddCompany.html";
    }

    @RequestMapping("/eChartsCompany")
    public String chartCompanyHtml() {
        // 返回公司信息图表
        return "/company/EChartsCompany.html";
    }

    // RESTful 风格接口
    @GetMapping(".company/{comname}")
    @ResponseBody
    public List<Company> queryList1(@PathVariable String comname) {
        // GET，查询，单参数
        System.out.println(comname);
        return csi.findByNativeSQL(comname);
    }

    @PutMapping("/company/{comaddress}/{comname}")
    @ResponseBody
    public String query2(@PathVariable String comaddress, @PathVariable String comname) {
        // PUT，修改，多参数
        System.out.println(comaddress);
        System.out.println(comname);
        csi.updateByName(comaddress, comname);
        return "公司地址：" + comaddress + "，公司名称：" + comname;
    }
}
