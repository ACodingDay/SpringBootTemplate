package com.yyt.springbootdemo.domain;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.List;

/**
 * @author yyt
 * @date 2021年10月28日 17:03
 * 角色实体类
 */
@Data
@Entity
@Table(name="sysrole")
public class SysRole {

    @Id
    @GeneratedValue(generator = "myuuid")
    @GenericGenerator(name = "myuuid", strategy = "uuid")
    @Column(length = 32)
    private String uuid;

    @Column(length = 30)
    private String rolename;

    @Column(length = 200)
    private String roledesc;

    @ManyToMany(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
    private List<SysAuth> sysAuths;
}
