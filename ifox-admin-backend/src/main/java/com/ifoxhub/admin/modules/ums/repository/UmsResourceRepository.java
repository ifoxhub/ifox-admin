package com.ifoxhub.admin.modules.ums.repository;

import com.ifoxhub.admin.modules.ums.model.UmsResource;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * @author zhangxl
 * @version v1.0
 * @date 2021/1/5 5:21 下午
 */
public interface UmsResourceRepository extends PagingAndSortingRepository<UmsResource, Long>, JpaSpecificationExecutor<UmsResource> {

    @Query("SELECT ur FROM UmsAdminRoleRelation ar\n" +
            "    LEFT JOIN UmsRole r ON ar.roleId = r.id\n" +
            "    LEFT JOIN UmsRoleResourceRelation rrr ON r.id = rrr.roleId\n" +
            "    LEFT JOIN UmsResource ur ON ur.id = rrr.resourceId\n" +
            "WHERE ar.adminId = :adminId AND ur.id IS NOT NULL\n" +
            "GROUP BY ur.id")
    List<UmsResource> findAllByAdminId(Long adminId);

    @Query("SELECT r FROM UmsRoleResourceRelation rrr\n" +
            "    LEFT JOIN UmsResource r ON rrr.resourceId = r.id\n" +
            "WHERE rrr.roleId = :roleId  AND r.id IS NOT NULL\n" +
            "GROUP BY r.id")
    List<UmsResource> findAllByRoleId(Long roleId);

}
