package com.yyt.springbootdemo.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yyt
 * @date 2021年11月01日 22:35
 * 过滤实体中的非基本属性
 */
public class FilterEntityUtil {
    // 基本类与包装类
    static String[] baseTypes = {"class java.lang.Boolean", "class java.lang.Byte", "class java.lang.Character",
            "class java.lang.Double", "class java.lang.Float", "class java.lang.Integer", "class java.lang.Long",
            "class java.lang.Short", "class java.lang.String", "byte", "int", "short", "long", "float", "double", "char", "boolean"
    };

    /**
     * 根据类的名称，得到类的属性与属性值
     * @param obj 类
     * @return java.util.Map<java.lang.String,java.lang.Object> 集合
     */
    public static Map<String, Object> getKeyAndValue(Object obj) {
        Map<String, Object> result = new HashMap<>();
        // 得到类对象
        Class userClass = obj.getClass();
        // 得到类中的属性集合
        Field[] fields = userClass.getDeclaredFields();
        for (Field field : fields){
            // 关闭安全检查可达到提升反射速度的目的
            field.setAccessible(true);
            // 遍历判断属性是否为基本属性
            String typeName = field.getGenericType().toString();
            // 判断结果
            boolean ifBasicType = false;
            for (String t : baseTypes){
                if (t.equals(typeName)){
                    ifBasicType = true;
                    break;
                }
            }
            if (!ifBasicType){
                continue;
            }

            Object val = new Object();
            try {
                // 得到属性值
                val = field.get(obj);
                result.put(field.getName(), val);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

}
