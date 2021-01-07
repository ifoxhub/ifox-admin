package com.ifoxhub.admin.modules.ums.service;


import com.ifoxhub.admin.modules.ums.model.UmsResourceCategory;

import java.util.List;

/**
 * @author zhangxl
 * @version v1.0
 * @date 2021/1/7
 */
public interface UmsResourceCategoryService {

    /**
     * 获取所有资源分类
     */
    List<UmsResourceCategory> listAll();

    /**
     * 创建资源分类
     */
    boolean create(UmsResourceCategory umsResourceCategory);

    boolean updateById(UmsResourceCategory umsResourceCategory);

    boolean removeById(Long id);
}
