package com.ifox.admin.modules.ums.service.impl;

import com.ifox.admin.modules.ums.model.UmsRoleMenuRelation;
import com.ifox.admin.modules.ums.repository.UmsRoleMenuRelationRepository;
import com.ifox.admin.modules.ums.service.UmsRoleMenuRelationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhangxl
 * @version v1.0
 * @date 2021/1/7
 */
@Service
public class UmsRoleMenuRelationServiceImpl implements UmsRoleMenuRelationService {

    @Resource
    private UmsRoleMenuRelationRepository roleMenuRelationRepository;

    @Override
    public void remove(Long roleId) {
        roleMenuRelationRepository.deleteAllByRoleId(roleId);
    }

    @Override
    public void saveBatch(List<UmsRoleMenuRelation> relationList) {
        roleMenuRelationRepository.saveAll(relationList);
    }
}
