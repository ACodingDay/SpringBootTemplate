package com.yyt.springbootdemo.repository;

import com.yyt.springbootdemo.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author yyt
 * @date 2021年10月17日 19:46
 * 公司数据仓库接口
 */
@Repository
public interface CompanyRepo extends JpaRepository<Company, String>, JpaSpecificationExecutor {

    // 原生 SQL 语句查询
    @Query(value = "select * from company where comname=?1", nativeQuery = true)
    List<Company> findByNativeSQL(String comname);

    // 原生 SQL 语句查询：模糊查询
    @Query(value = "select * from company where comname like '%?1%'", nativeQuery = true)
    List<Company> findByNativeSQL1(String comname);

    // 原生 SQL 语句操作涉及到数据变动的，例如更新，删除；都必须加上注解 @Modifying
    @Modifying
    @Query(value = "update company set comaddress =?1 where comname =?2", nativeQuery = true)
    void updateByName(String comaddress, String comname);

    // 验证邮箱唯一性（如果已经存在，返回 0，否则返回 1）
    // 查找是否存在别用 select count(*)，改用 limit 1
    @Query(value = "select 1 from company where contactemail=?1 limit 1", nativeQuery = true)
    int validateEmail(String contactemail);
}
