package com.ifox.admin.modules.ums.service.impl;

import com.ifox.admin.modules.ums.model.UmsAdminRoleRelation;
import com.ifox.admin.modules.ums.repository.UmsAdminRoleRelationRepository;
import com.ifox.admin.modules.ums.service.UmsAdminRoleRelationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhangxl
 * @version v1.0
 * @date 2021/1/7
 */
@Service
public class UmsAdminRoleRelationServiceImpl implements UmsAdminRoleRelationService {

    @Resource
    private UmsAdminRoleRelationRepository adminRoleRelationRepository;

    @Override
    public void remove(Long adminId) {
        adminRoleRelationRepository.deleteAllByAdminId(adminId);
    }

    @Override
    public void saveBatch(List<UmsAdminRoleRelation> list) {
        adminRoleRelationRepository.saveAll(list);
    }
}
