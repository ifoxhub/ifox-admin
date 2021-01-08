package com.ifox.admin.modules.ums.repository;

import com.ifox.admin.modules.ums.model.UmsResourceCategory;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author zhangxl
 * @version v1.0
 * @date 2021/1/5 5:22 下午
 */
public interface UmsResourceCategoryRepository extends CrudRepository<UmsResourceCategory, Long> {

    List<UmsResourceCategory> findAllByOrderBySortDesc();

}
