package com.yyt.springbootdemo.domain;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @author yyt
 * @date 2021年11月01日 16:47
 * 操作日志实体类
 */
@Data
@Entity
@Table(name="syslog")
public class SysLog {

    @Id
    @GeneratedValue(generator = "myuuid")
    @GenericGenerator(name = "myuuid", strategy = "uuid")
    @Column(length = 32)
    private String uuid;

    @Column(length = 100)
    private String username;

    @Column(columnDefinition = "char(19)")
    private String operateTime;

    @Column(length = 20)
    private String operateType;

    @Column(length = 100)
    private String operateDesc;

    @Column(columnDefinition = "text")
    private String operateDetail;

    @Column(length = 20)
    private String osType;

    @Column(length = 30)
    private String browserType;

    @Column(columnDefinition = "varbinary(16)")
    private String ipAddr;
}
