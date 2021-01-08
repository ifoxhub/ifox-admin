package com.ifox.admin.modules.ums.repository;

import com.ifox.admin.modules.ums.model.UmsAdminRoleRelation;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author zhangxl
 * @version v1.0
 * @date 2021/1/5 5:19 下午
 */
public interface UmsAdminRoleRelationRepository extends CrudRepository<UmsAdminRoleRelation, Long> {

    List<UmsAdminRoleRelation> findAllByRoleId(Long roleId);

    List<UmsAdminRoleRelation> findAllByRoleIdIn(List<Long> roleIds);

    void deleteAllByAdminId(Long adminId);

}
