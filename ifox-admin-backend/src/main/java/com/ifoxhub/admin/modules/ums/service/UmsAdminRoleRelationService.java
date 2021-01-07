package com.ifoxhub.admin.modules.ums.service;

import com.ifoxhub.admin.modules.ums.model.UmsAdminRoleRelation;

import java.util.List;

/**
 * @author zhangxl
 * @version v1.0
 * @date 2021/1/7
 */
public interface UmsAdminRoleRelationService {

    void remove(Long adminId);

    void saveBatch(List<UmsAdminRoleRelation> list);

}
