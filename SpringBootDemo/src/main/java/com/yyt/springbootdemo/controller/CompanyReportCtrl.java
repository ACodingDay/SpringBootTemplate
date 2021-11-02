package com.yyt.springbootdemo.controller;

import com.yyt.springbootdemo.service.CompanyService;
import com.yyt.springbootdemo.utils.JasperExportUtil;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author yyt
 * @date 2021年11月02日 20:23
 */
@Controller
@RequestMapping(value = "/CompanyReport")
public class CompanyReportCtrl {

    @Resource(name = "companyService")
    private CompanyService cs;

    @RequestMapping("/showHtml")
    public String showHTML(){
        return "reportTemplates/CompanyReport.html";
    }

    @RequestMapping("/html")
    public void reportHtml(@RequestBody(required = false) Map<String, Object> reqMap,
                           HttpServletRequest request, HttpServletResponse response) throws JRException, IOException {
        JRDataSource dataSource = new JRBeanCollectionDataSource(cs.findAll());
        JasperExportUtil.exportToHTML("static/reports/test1.jasper", reqMap, dataSource, request, response);
    }

    @RequestMapping("/excel")
    public void reportExcel(@RequestBody(required = false) Map<String, Object> reqMap,
                           HttpServletRequest request, HttpServletResponse response) throws JRException, IOException {
        JRDataSource dataSource = new JRBeanCollectionDataSource(cs.findAll());
        JasperExportUtil.exportToXls("static/reports/test1.jasper", "公司列表", reqMap, dataSource, response);
    }
}
