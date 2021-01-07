package com.ifoxhub.admin.modules.ums.service;

import com.ifoxhub.admin.modules.ums.model.UmsRoleMenuRelation;

import java.util.List;

/**
 * @author zhangxl
 * @version v1.0
 * @date 2021/1/7
 */
public interface UmsRoleMenuRelationService {

    void remove(Long roleId);

    void saveBatch(List<UmsRoleMenuRelation> relationList);

}
