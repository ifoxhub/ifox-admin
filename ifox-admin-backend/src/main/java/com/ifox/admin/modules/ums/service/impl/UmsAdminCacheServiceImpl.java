package com.ifox.admin.modules.ums.service.impl;

import com.ifox.admin.common.service.CacheService;
import com.ifox.admin.modules.ums.model.UmsAdmin;
import com.ifox.admin.modules.ums.model.UmsAdminRoleRelation;
import com.ifox.admin.modules.ums.model.UmsResource;
import com.ifox.admin.modules.ums.repository.UmsAdminRepository;
import com.ifox.admin.modules.ums.repository.UmsAdminRoleRelationRepository;
import com.ifox.admin.modules.ums.service.UmsAdminCacheService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhangxl
 * @version v1.0
 * @date 2021/1/6
 */
@Service
public class UmsAdminCacheServiceImpl implements UmsAdminCacheService {

    private final CacheService cacheService;

    @Resource
    private UmsAdminRepository adminRepository;

    @Resource
    private UmsAdminRoleRelationRepository adminRoleRelationRepository;

    private static final String CACHE_DATABASE = "ifox";
    private static final Long CACHE_EXPIRE = 86400L;
    private static final String CACHE_KEY_ADMIN = "ums:admin";
    private static final String CACHE_KEY_RESOURCE_LIST = "ums:resources";

    public UmsAdminCacheServiceImpl(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    @Override
    public void delAdmin(Long adminId) {
        UmsAdmin admin = adminRepository.findById(adminId).orElse(null);
        if (admin != null) {
            String key = CACHE_DATABASE + ":" + CACHE_KEY_ADMIN + ":" + admin.getUsername();
            cacheService.delete(key);
        }
    }

    @Override
    public void delResourceList(Long adminId) {
        String key = CACHE_DATABASE + ":" + CACHE_KEY_RESOURCE_LIST + ":" + adminId;
        cacheService.delete(key);
    }

    @Override
    public void delResourceListByRole(Long roleId) {
        List<UmsAdminRoleRelation> relationList = adminRoleRelationRepository.findAllByRoleId(roleId);
        if (!CollectionUtils.isEmpty(relationList)) {
            String keyPrefix = CACHE_DATABASE + ":" + CACHE_KEY_RESOURCE_LIST + ":";
            List<String> keys = relationList.stream().map(relation -> keyPrefix + relation.getAdminId()).collect(Collectors.toList());
            cacheService.delete(keys);
        }
    }

    @Override
    public void delResourceListByRoleIds(List<Long> roleIds) {
        List<UmsAdminRoleRelation> relationList = adminRoleRelationRepository.findAllByRoleIdIn(roleIds);
        if (!CollectionUtils.isEmpty(relationList)) {
            String keyPrefix = CACHE_DATABASE + ":" + CACHE_KEY_RESOURCE_LIST + ":";
            List<String> keys = relationList.stream().map(relation -> keyPrefix + relation.getAdminId()).collect(Collectors.toList());
            cacheService.delete(keys);
        }
    }

    @Override
    public void delResourceListByResource(Long resourceId) {
        List<Long> adminIdList = adminRepository.getAdminIdList(resourceId);
        if (!CollectionUtils.isEmpty(adminIdList)) {
            String keyPrefix = CACHE_DATABASE + ":" + CACHE_KEY_RESOURCE_LIST + ":";
            List<String> keys = adminIdList.stream().map(adminId -> keyPrefix + adminId).collect(Collectors.toList());
            cacheService.delete(keys);
        }
    }

    @Override
    public UmsAdmin getAdmin(String username) {
        String key = CACHE_DATABASE + ":" + CACHE_KEY_ADMIN + ":" + username;
        return (UmsAdmin) cacheService.get(key);
    }

    @Override
    public void setAdmin(UmsAdmin admin) {
        String key = CACHE_DATABASE + ":" + CACHE_KEY_ADMIN + ":" + admin.getUsername();
        cacheService.set(key, admin, CACHE_EXPIRE);
    }

    @Override
    public List<UmsResource> getResourceList(Long adminId) {
        String key = CACHE_DATABASE + ":" + CACHE_KEY_RESOURCE_LIST + ":" + adminId;
        return (List<UmsResource>) cacheService.get(key);
    }

    @Override
    public void setResourceList(Long adminId, List<UmsResource> resourceList) {
        String key = CACHE_DATABASE + ":" + CACHE_KEY_RESOURCE_LIST + ":" + adminId;
        cacheService.set(key, resourceList, CACHE_EXPIRE);
    }
}
