package com.yyt.springbootdemo.domain;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;

/**
 * @author yyt
 * @date 2021年10月17日 16:57
 * 公司实体类
 */
@Data
@Entity
@Table(name = "company")
public class Company {

    @Id
    @GeneratedValue(generator = "myuuid")
    @GenericGenerator(name = "myuuid", strategy = "uuid")
    @Column(length = 32)
    private String uuid;

    @Column(length = 60)
    private String comname;

    @Column(length = 120)
    private String comaddress;

    @Column(length = 30)
    private String comurl;

    @Column(length = 30)
    private String comtelephone;

    // 公司成立日期，格式：2020.10.01
    @Column(columnDefinition = "char(10)")
    private String establishdate;

    @Column
    private int employeenumber;

    // 总产值，单位：万元。
    @Column(columnDefinition = "float(20,4) default '0.0000'")
    private float totaloutput;

    @Column(columnDefinition = "text")
    private String comdesc;

    @Column(columnDefinition = "char(4)")
    private String comstatus;

    @Column(length = 20)
    private String contactname;

    @Column(columnDefinition = "char(11)")
    private String contactmobile;

    @Column(length = 30)
    private String contactemail;

    @Column(length = 120)
    private String uploadurl;
}
