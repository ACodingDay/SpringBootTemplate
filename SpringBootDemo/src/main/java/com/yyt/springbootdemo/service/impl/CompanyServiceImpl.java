package com.yyt.springbootdemo.service.impl;

import com.yyt.springbootdemo.domain.Company;
import com.yyt.springbootdemo.repository.CompanyRepo;
import com.yyt.springbootdemo.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author yyt
 * @date 2021年10月18日 15:00
 * 公司业务层实现类
 */
@Service("companyService")
public class CompanyServiceImpl implements CompanyService{

    @Autowired
    private CompanyRepo companyRepo;

    @Override
    public void save(Company company) {
        companyRepo.save(company);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void delete(String uuid) {
        companyRepo.deleteById(uuid);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void update(Company company) {
        companyRepo.save(company);
    }

    @Override
    public List<Company> findAll() {
        return companyRepo.findAll();
    }

    @Override
    public List<Company> findByNativeSQL(String companyname) {
        // 自定义的方法
        return companyRepo.findByNativeSQL(companyname);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void updateByName(String comaddress, String comname) {
        companyRepo.updateByName(comaddress, comname);
    }

    @Override
    public Page<Company> findAllSimplePages(Pageable pageable) {
        return companyRepo.findAll(pageable);
    }

    @Override
    public boolean validateEmail(String contactEmail) {
        int number = companyRepo.validateEmail(contactEmail);
        return  number == 0;
    }

    @Override
    public Page<Company> queryDynamically(Map<String, Object> reqMap, Pageable pageable) {

        // lambda 表达式重写其中的 toPredicate() 方法
        Specification<Company> specification = (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            // 设置查询条件
            if (!"".equals(reqMap.get("comname").toString())){
                // 公司名称，模糊查询 like，前后都加 % 就是全模糊
                predicates.add(criteriaBuilder.like(root.get("comname"), "%"+reqMap.get("comname").toString()+"%"));
            }
            if (!"全部".equals(reqMap.get("comstatus").toString())){
                // 营运状态，精确查询 equals
                predicates.add(criteriaBuilder.equal(root.get("comstatus"), reqMap.get("comstatus").toString()));
            }
            if (!"".equals(reqMap.get("employeenumber").toString())){
                int num = Integer.parseInt(reqMap.get("employeenumber").toString());
                // 员工人数，大于某数值
                predicates.add(criteriaBuilder.greaterThan(root.get("employeenumber"), num));
            }
            // 返回
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        // 这里使用 JpaSpecificationExecutor 接口的分页查询方法
        return companyRepo.findAll(specification, pageable);
    }
}
