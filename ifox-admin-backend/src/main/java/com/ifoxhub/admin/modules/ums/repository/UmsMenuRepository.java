package com.ifoxhub.admin.modules.ums.repository;

import com.ifoxhub.admin.modules.ums.model.UmsMenu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * @author zhangxl
 * @version v1.0
 * @date 2021/1/5 5:20 下午
 */
public interface UmsMenuRepository extends PagingAndSortingRepository<UmsMenu, Long> {

    Page<UmsMenu> findAllByParentId(Long parentId, Pageable pageable);

    @Query("SELECT m FROM UmsAdminRoleRelation arr\n" +
            "      LEFT JOIN UmsRole r ON arr.roleId = r.id\n" +
            "      LEFT JOIN UmsRoleMenuRelation rmr ON r.id = rmr.roleId\n" +
            "      LEFT JOIN UmsMenu m ON rmr.menuId = m.id\n" +
            "WHERE arr.adminId = :adminId AND m.id IS NOT NULL\n" +
            "GROUP BY m.id")
    List<UmsMenu> findAllByAdminId(Long adminId);

    @Query("SELECT m FROM UmsRoleMenuRelation rmr\n" +
            "    LEFT JOIN UmsMenu m ON rmr.menuId = m.id\n" +
            "WHERE rmr.roleId = :roleId  AND m.id IS NOT NULL\n" +
            "GROUP BY m.id")
    List<UmsMenu> findAllByRoleId(Long roleId);

}
