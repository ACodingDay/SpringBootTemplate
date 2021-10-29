package com.yyt.springbootdemo.domain;

import lombok.Data;

/**
 * @author yyt
 * @date 2021年10月28日 20:08
 * zTree 插件的节点实体
 */
@Data
public class Ztree {
    // 自身的 id
    public int id;
    // 父节点的 id
    public int pId;
    // 名称
    public String name;
    // 是否展开
    public boolean open;
    // 是否选中
    public boolean checked;
}
