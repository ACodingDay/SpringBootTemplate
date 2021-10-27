package com.yyt.springbootdemo.service;

import com.yyt.springbootdemo.domain.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

/**
 * @author yyt
 * @date 2021年10月17日 23:48
 * 公司业务层接口
 */
public interface CompanyService {

    // 保存公司
    void save(Company company);

    // 根据 uuid 删除公司
    @Transactional
    void delete(String uuid);

    // 更新
    @Transactional
    void update(Company company);

    // 查找全部
    List<Company> findAll();

    // 执行原生 SQL 语句的查询
    List<Company> findByNativeSQL(String companyname);

    // 更新公司的地址
    @Transactional
    void updateByName(String comaddress, String comname);

    // 简单的分页查询
    Page<Company> findAllSimplePages(Pageable pageable);

    // 验证邮箱唯一性
    boolean validateEmail(String contactEmail);

    // 复杂条件动态查询
    Page<Company> queryDynamically(Map<String, Object> reqMap, Pageable pageable);
}
