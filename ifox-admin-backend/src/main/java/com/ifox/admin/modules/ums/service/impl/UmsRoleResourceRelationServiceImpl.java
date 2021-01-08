package com.ifox.admin.modules.ums.service.impl;

import com.ifox.admin.modules.ums.model.UmsRoleResourceRelation;
import com.ifox.admin.modules.ums.repository.UmsRoleResourceRelationRepository;
import com.ifox.admin.modules.ums.service.UmsRoleResourceRelationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhangxl
 * @version v1.0
 * @date 2021/1/7
 */
@Service
public class UmsRoleResourceRelationServiceImpl implements UmsRoleResourceRelationService {

    @Resource
    private UmsRoleResourceRelationRepository roleResourceRelationRepository;

    @Override
    public void remove(Long roleId) {
        roleResourceRelationRepository.deleteAllByRoleId(roleId);
    }

    @Override
    public void saveBatch(List<UmsRoleResourceRelation> relationList) {
        roleResourceRelationRepository.saveAll(relationList);
    }
}
