package com.yyt.springbootdemo.utils;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.aspectj.weaver.ast.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author yyt
 * @date 2021年11月02日 20:23
 */
public class JasperExportUtil {
    private static final String ENCODING = "UTF-8";
    private static final String XLS_CONTENT_TYPE = "application/x-msdownload";

    /**
     * 输出格式为 html
     * @param jasperPath 模板路径
     * @param params     配置模板的参数
     * @param dataSource 处理不同来源的数据
     * @param request servlet 请求
     */
    public static void exportToHTML(String jasperPath, Map<String, Object> params,
                                    JRDataSource dataSource, HttpServletRequest request, HttpServletResponse response) throws IOException, JRException {
        ClassPathResource classPathResource = new ClassPathResource(jasperPath);
        InputStream is = classPathResource.getInputStream();
        // java.lang.ClassNotFoundException: net.sf.jasperreports.compilers.ReportExpressionEvaluationData
        JasperPrint jasperPrint = JasperFillManager.fillReport(is, params, dataSource);
        request.setCharacterEncoding(ENCODING);
        response.setCharacterEncoding(ENCODING);

        HtmlExporter exporter = new HtmlExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleHtmlExporterOutput(response.getWriter()));
        exporter.exportReport();
    }

    /**
     * 输出格式为 Excel
     * @param jasperPath 模板路径
     * @param fileName 用户保存的文件的名称
     * @param params 配置模板的参数
     * @param dataSource 处理不同来源的数据
     */
    public static void exportToXls(String jasperPath, String fileName, Map<String, Object> params,
                                   JRDataSource dataSource, HttpServletResponse response) throws IOException, JRException {
        File reportFile = new File(new ClassPathResource(jasperPath).getURI());
        // java.lang.ClassNotFoundException: net.sf.jasperreports.compilers.ReportExpressionEvaluationData
        JasperPrint jasperPrint = JasperFillManager.fillReport(reportFile.getPath(), params, dataSource);
        response.setCharacterEncoding(ENCODING);
        response.setContentType(XLS_CONTENT_TYPE);
        // 浏览器提示是下载保存还是直接打开
        response.setHeader("Content-disposition", "attachment;filename=" + new String(fileName.getBytes(StandardCharsets.UTF_8)));

        JRAbstractExporter exporter = new JRXlsExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
        exporter.exportReport();
    }
}
