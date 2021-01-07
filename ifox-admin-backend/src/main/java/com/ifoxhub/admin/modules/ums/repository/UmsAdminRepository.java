package com.ifoxhub.admin.modules.ums.repository;

import com.ifoxhub.admin.modules.ums.model.UmsAdmin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * @author zhangxl
 * @version v1.0
 * @date 2021/1/5 5:15 下午
 */
public interface UmsAdminRepository extends PagingAndSortingRepository<UmsAdmin, Long> {

    @Query("         SELECT\n" +
            "            DISTINCT ar.adminId\n" +
            "        FROM\n" +
            "            UmsRoleResourceRelation rr\n" +
            "                LEFT JOIN UmsAdminRoleRelation ar ON rr.roleId = ar.roleId\n" +
            "        WHERE rr.resourceId=:resourceId")
    List<Long> getAdminIdList(Long resourceId);

    List<UmsAdmin> findAllByUsername(String username);

    @Query("select u from UmsAdmin u where u.username like %:keyword% or u.nickName like %:keyword%")
    Page<UmsAdmin> findAllByKeyword(String keyword, Pageable pageable);
}
