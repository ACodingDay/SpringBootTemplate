package com.yyt.springbootdemo.domain;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @author yyt
 * @date 2021年10月28日 16:54
 * 权限表实体类
 */
@Data
@Entity
@Table(name="sysauth")
public class SysAuth {

    @Id
    @GeneratedValue(generator = "myuuid")
    @GenericGenerator(name = "myuuid", strategy = "uuid")
    @Column(length = 32)
    private String uuid;

    @Column(length = 200)
    private String powername;

    @Column(length = 20)
    private String treename;

    @Column
    private int id;

    @Column
    private int pid;
}
