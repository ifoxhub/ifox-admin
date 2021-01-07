package com.ifoxhub.admin.modules.ums.service;

import com.ifoxhub.admin.modules.ums.model.UmsMenu;
import com.ifoxhub.admin.modules.ums.model.UmsResource;
import com.ifoxhub.admin.modules.ums.model.UmsRole;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author zhangxl
 * @version v1.0
 * @date 2021/1/6
 */
public interface UmsRoleService {
    /**
     * 添加角色
     */
    boolean create(UmsRole role);

    /**
     * 批量删除角色
     */
    boolean delete(List<Long> ids);

    /**
     * 分页获取角色列表
     */
    Page<UmsRole> list(String keyword, Integer pageSize, Integer pageNum);

    /**
     * 根据管理员ID获取对应菜单
     */
    List<UmsMenu> getMenuList(Long adminId);

    /**
     * 获取角色相关菜单
     */
    List<UmsMenu> listMenu(Long roleId);

    /**
     * 获取角色相关资源
     */
    List<UmsResource> listResource(Long roleId);

    /**
     * 给角色分配菜单
     */
    @Transactional
    int allocMenu(Long roleId, List<Long> menuIds);

    /**
     * 给角色分配资源
     */
    @Transactional
    int allocResource(Long roleId, List<Long> resourceIds);

    boolean updateById(UmsRole role);

    Iterable<UmsRole> list();
}