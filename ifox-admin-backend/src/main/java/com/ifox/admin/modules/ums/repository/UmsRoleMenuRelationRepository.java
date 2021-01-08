package com.ifox.admin.modules.ums.repository;

import com.ifox.admin.modules.ums.model.UmsRoleMenuRelation;
import org.springframework.data.repository.CrudRepository;

/**
 * @author zhangxl
 * @version v1.0
 * @date 2021/1/5 5:23 下午
 */
public interface UmsRoleMenuRelationRepository extends CrudRepository<UmsRoleMenuRelation, Long> {

    void deleteAllByRoleId(Long roldId);

}
