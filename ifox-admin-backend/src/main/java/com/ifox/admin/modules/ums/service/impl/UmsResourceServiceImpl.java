package com.ifox.admin.modules.ums.service.impl;

import com.google.common.collect.Lists;
import com.ifox.admin.modules.ums.model.UmsResource;
import com.ifox.admin.modules.ums.repository.UmsResourceRepository;
import com.ifox.admin.modules.ums.service.UmsAdminCacheService;
import com.ifox.admin.modules.ums.service.UmsResourceService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 后台资源管理Service实现类
 * Created by macro on 2020/2/2.
 */
@Service
public class UmsResourceServiceImpl implements UmsResourceService {

    @Autowired
    private UmsAdminCacheService adminCacheService;

    @Resource
    private UmsResourceRepository resourceRepository;

    @Override
    public boolean create(UmsResource umsResource) {
        umsResource.setCreateTime(new Date());
        resourceRepository.save(umsResource);
        return true;
    }

    @Override
    public boolean update(Long id, UmsResource umsResource) {
        umsResource.setId(id);
        resourceRepository.save(umsResource);
        adminCacheService.delResourceListByResource(id);
        return true;
    }

    @Override
    public boolean delete(Long id) {
        resourceRepository.deleteById(id);
        adminCacheService.delResourceListByResource(id);
        return true;
    }

    @Override
    public Page<UmsResource> list(Long categoryId, String nameKeyword, String urlKeyword, Integer pageSize, Integer pageNum) {
        Pageable pageable = PageRequest.of(--pageNum, pageSize);
        Specification<UmsResource> specification = new Specification<UmsResource>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<UmsResource> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                // 添加搜索条件
                if (null != categoryId) {
                    predicates.add(criteriaBuilder.equal(root.get("category_id"), categoryId));
                }
                if (Strings.isNotEmpty(nameKeyword)) {
                    predicates.add(criteriaBuilder.like(root.get("name"), "%" + nameKeyword + "%"));
                }
                if (Strings.isNotEmpty(urlKeyword)) {
                    predicates.add(criteriaBuilder.like(root.get("url"), "%" + urlKeyword + "%"));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        return resourceRepository.findAll(specification, pageable);
    }

    @Override
    public List<UmsResource> list() {
        return Lists.newArrayList(resourceRepository.findAll());
    }

    @Override
    public UmsResource getById(Long id) {
        return resourceRepository.findById(id).orElse(null);
    }
}
