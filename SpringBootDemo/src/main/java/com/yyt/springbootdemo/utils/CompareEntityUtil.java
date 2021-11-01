package com.yyt.springbootdemo.utils;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yyt
 * @date 2021年11月01日 22:34
 * 比较两个实体类的属性值 value 的差别，用于记录修改前后的结果
 */
public class CompareEntityUtil {

    // 修改详情
    private String jsonInfo;
    // 修改前的实体
    private Object beforeModifiedObj;
    // 修改后的实体
    private Object afterModifiedObj;

    public CompareEntityUtil(Object beforeModifiedObj, Object afterModifiedObj) {
        this.beforeModifiedObj = beforeModifiedObj;
        this.afterModifiedObj = afterModifiedObj;
    }

    public String outputInfo() {
        // 修改前的实体信息
        Map<String, Object> beforeMap = FilterEntityUtil.getKeyAndValue(beforeModifiedObj);
        // 修改之后的实体信息
        Map<String, Object> afterMap = FilterEntityUtil.getKeyAndValue(afterModifiedObj);
        // 结果存储修改的属性
        Map<String, String> result = new HashMap<String, String>();
        // 遍历比较
        for(String key : beforeMap.keySet()) {
            // 修改前的值
            String strBefore = "";
            if(beforeMap.get(key) != null){
                strBefore = beforeMap.get(key).toString();
            }
            // 修改后的值
            String strAfter = "";
            if(afterMap.get(key) != null){
                strAfter = afterMap.get(key).toString();
            }
            // 有修改
            if(!strBefore.equals(strAfter)){
                // 如果不是修改了密码或者修改了uuid
                if (!("password".equals(key) || "roleuuid".equals(key))){
                    result.put(key, strBefore + " ==> " + strAfter);
                }
            }
        }
        return JSON.toJSONString(result);
    }
}
