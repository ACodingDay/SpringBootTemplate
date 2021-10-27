package com.yyt.springbootdemo.utils;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Properties;

/**
 * @author yyt
 * @date 2021年10月23日 17:29
 * 接收上传的文件
 */
@Component
public class ReceiveUploadFile {
    /**
     * 接收上传的文件，上传成功后，返回文件的保存路径
     *
     * @param file           上传的文件
     * @param fileNamePrefix 保存文件的前缀名
     * @return java.lang.String
     * @author yyt
     * @date 2021/10/23 18:48
     */
    public String receiveFileName(MultipartFile file, String fileNamePrefix) {
        JSONObject result = new JSONObject();
        if (file.isEmpty()) {
            result.put("code", 0);
            result.put("msg", "文件是空的");
            return result.toJSONString();
        }

        // 排除的后缀名格式
        HashSet<String> suffixList = new HashSet<>();
        suffixList.add("exe");
        suffixList.add("dll");
        suffixList.add("com");
        suffixList.add("bat");
        suffixList.add("apk");
        suffixList.add("sys");

        // 获取文件名
        String fileName = file.getOriginalFilename();
        // 文件的后缀名转小写
        String fileNameSuffix = fileName.substring(fileName.lastIndexOf('.')).toLowerCase();
        if (suffixList.contains(fileNameSuffix)) {
            result.put("code", 0);
            result.put("msg", "文件格式不正确");
            return result.toJSONString();
        }

        // 获取当前的年月日
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        String strFolderPath = dateFormat.format(new Date());

        // 判断文件夹是否存在，不存在就创建
        // 分隔符 unix / , windows \
        if (isOSLinux()) {
            // Linux 环境
            strFolderPath = File.separator + "home" + File.separator + "uploadFiles" +
                    File.separator + strFolderPath + File.separator;
        } else {
            // windows 环境
            strFolderPath = "D:" + File.separator + "desktop" + File.separator
                    + strFolderPath + File.separator;
        }
        File dir = new File(strFolderPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // 获取当前时间，精确到毫秒
        SimpleDateFormat timeFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        // 保存的文件名
        String strSaveFileName = timeFormat.format(new Date()) + fileNameSuffix;

        // 保存文件
        try {
            String strFileFullPath = strFolderPath + fileNamePrefix + strSaveFileName;
            File f = new File(strFileFullPath);
            file.transferTo(f);
            result.put("code", 1);
            result.put("msg", strFileFullPath);
            return result.toJSONString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        result.put("code", 0);
        result.put("msg", "上传文件失败");
        return result.toJSONString();
    }

    /**
     * 判断当前环境是不是 Linux，是则返回 true
     *
     * @return boolean
     * @author yyt
     * @date 2021/10/23 18:48
     */
    private boolean isOSLinux() {
        Properties prop = System.getProperties();
        String os = prop.getProperty("os.name");
        if (os != null && os.toLowerCase().contains("linux")) {
            return true;
        } else {
            return false;
        }
    }

}
