package com.yyt.springbootdemo.domain;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @author yyt
 * @date 2021年10月28日 17:54
 * 账户实体类
 */
@Data
@Entity
@Table(name="sysuser")
public class SysUser {

    @Id
    @GeneratedValue(generator = "myuuid")
    @GenericGenerator(name = "myuuid", strategy = "uuid")
    @Column(length = 32)
    private String uuid;

    @Column(length = 100)
    private String username;

    @Column(length = 100)
    private String password;

    @Column(length = 100)
    private String useremail;

    @Column(length = 100)
    private String usermobile;

    @Column(length = 30)
    private String sysrolename;

    @Column(length = 32)
    private String sysroleid;

    @OneToOne
    @JoinColumn(name = "sysroleid", referencedColumnName = "uuid", insertable = false, updatable = false)
    private SysRole sysRole;
}
