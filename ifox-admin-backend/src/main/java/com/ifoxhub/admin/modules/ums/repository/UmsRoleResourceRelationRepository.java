package com.ifoxhub.admin.modules.ums.repository;

import com.ifoxhub.admin.modules.ums.model.UmsRoleResourceRelation;
import org.springframework.data.repository.CrudRepository;

/**
 * @author zhangxl
 * @version v1.0
 * @date 2021/1/5 5:24 下午
 */
public interface UmsRoleResourceRelationRepository extends CrudRepository<UmsRoleResourceRelation, Long> {

    void deleteAllByRoleId(Long roleId);
}
