package com.ifoxhub.admin.modules.ums.service;

import com.ifoxhub.admin.modules.ums.model.UmsRoleResourceRelation;

import java.util.List;

/**
 * @author zhangxl
 * @version v1.0
 * @date 2021/1/7
 */
public interface UmsRoleResourceRelationService {

    void remove(Long roleId);

    void saveBatch(List<UmsRoleResourceRelation> relationList);
}
