package com.ifoxhub.admin.modules.ums.service.impl;

import com.ifoxhub.admin.modules.ums.model.*;
import com.ifoxhub.admin.modules.ums.repository.UmsMenuRepository;
import com.ifoxhub.admin.modules.ums.repository.UmsResourceRepository;
import com.ifoxhub.admin.modules.ums.repository.UmsRoleRepository;
import com.ifoxhub.admin.modules.ums.service.UmsAdminCacheService;
import com.ifoxhub.admin.modules.ums.service.UmsRoleMenuRelationService;
import com.ifoxhub.admin.modules.ums.service.UmsRoleResourceRelationService;
import com.ifoxhub.admin.modules.ums.service.UmsRoleService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zhangxl
 * @version v1.0
 * @date 2021/1/6
 */
@Service
public class UmsRoleServiceImpl implements UmsRoleService {

    @Autowired
    private UmsAdminCacheService adminCacheService;

    @Autowired
    private UmsRoleMenuRelationService roleMenuRelationService;

    @Autowired
    private UmsRoleResourceRelationService roleResourceRelationService;

    @Resource
    private UmsResourceRepository resourceRepository;

    @Resource
    private UmsRoleRepository roleRepository;

    @Resource
    private UmsMenuRepository menuRepository;

    @Override
    public boolean create(UmsRole role) {
        role.setCreateTime(new Date());
        role.setAdminCount(0);
        role.setSort(0);
        roleRepository.save(role);
        return true;
    }

    @Override
    @Transactional
    public boolean delete(List<Long> ids) {
        roleRepository.deleteAllByIdIn(ids);
        adminCacheService.delResourceListByRoleIds(ids);
        return true;
    }

    @Override
    public Page<UmsRole> list(String keyword, Integer pageSize, Integer pageNum) {
        Pageable pageable = PageRequest.of(--pageNum, pageSize);
        if(Strings.isNotEmpty(keyword)){
            return roleRepository.findAllByNameContains(keyword, pageable);
        }
        return  roleRepository.findAll(pageable);
    }

    @Override
    public List<UmsMenu> getMenuList(Long adminId) {
        return menuRepository.findAllByAdminId(adminId);
    }

    @Override
    public List<UmsMenu> listMenu(Long roleId) {
        return menuRepository.findAllByRoleId(roleId);
    }

    @Override
    public List<UmsResource> listResource(Long roleId) {
        return resourceRepository.findAllByRoleId(roleId);
    }

    @Override
    @Transactional
    public int allocMenu(Long roleId, List<Long> menuIds) {
        //先删除原有关系
        roleMenuRelationService.remove(roleId);
        //批量插入新关系
        List<UmsRoleMenuRelation> relationList = new ArrayList<>();
        for (Long menuId : menuIds) {
            UmsRoleMenuRelation relation = new UmsRoleMenuRelation();
            relation.setRoleId(roleId);
            relation.setMenuId(menuId);
            relationList.add(relation);
        }
        roleMenuRelationService.saveBatch(relationList);
        return menuIds.size();
    }

    @Override
    @Transactional
    public int allocResource(Long roleId, List<Long> resourceIds) {
        //先删除原有关系
        roleResourceRelationService.remove(roleId);
        //批量插入新关系
        List<UmsRoleResourceRelation> relationList = new ArrayList<>();
        for (Long resourceId : resourceIds) {
            UmsRoleResourceRelation relation = new UmsRoleResourceRelation();
            relation.setRoleId(roleId);
            relation.setResourceId(resourceId);
            relationList.add(relation);
        }
        roleResourceRelationService.saveBatch(relationList);
        adminCacheService.delResourceListByRole(roleId);
        return resourceIds.size();
    }

    @Override
    public boolean updateById(UmsRole role) {
        roleRepository.save(role);
        return true;
    }

    @Override
    public Iterable<UmsRole> list() {
        return roleRepository.findAll();
    }
}
