package com.yyt.springbootdemo.domain;

import lombok.Data;

/**
 * @author yyt
 * @date 2021年10月24日 22:35
 * ECharts 数据对象
 */
@Data
public class EChartsData {
    private String name;
    private float value;

    public EChartsData(String name, float value) {
        this.name = name;
        this.value = value;
    }
}
