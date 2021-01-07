package com.ifoxhub.admin.modules.ums.service.impl;

import com.ifoxhub.admin.modules.ums.model.UmsResourceCategory;
import com.ifoxhub.admin.modules.ums.repository.UmsResourceCategoryRepository;
import com.ifoxhub.admin.modules.ums.service.UmsResourceCategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author zhangxl
 * @version v1.0
 * @date 2021/1/7
 */
@Service
public class UmsResourceCategoryServiceImpl implements UmsResourceCategoryService {

    @Resource
    private UmsResourceCategoryRepository resourceCategoryRepository;

    @Override
    public List<UmsResourceCategory> listAll() {
        return resourceCategoryRepository.findAllByOrderBySortDesc();
    }

    @Override
    public boolean create(UmsResourceCategory umsResourceCategory) {
        umsResourceCategory.setCreateTime(new Date());
        resourceCategoryRepository.save(umsResourceCategory);
        return true;
    }

    @Override
    public boolean updateById(UmsResourceCategory umsResourceCategory) {
        resourceCategoryRepository.save(umsResourceCategory);
        return true;
    }

    @Override
    public boolean removeById(Long id) {
        resourceCategoryRepository.deleteById(id);
        return true;
    }
}
