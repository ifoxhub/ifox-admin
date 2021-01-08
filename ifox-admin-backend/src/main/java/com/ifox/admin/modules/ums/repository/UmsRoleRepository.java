package com.ifox.admin.modules.ums.repository;

import com.ifox.admin.modules.ums.model.UmsRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * @author zhangxl
 * @version v1.0
 * @date 2021/1/5 5:23 下午
 */
public interface UmsRoleRepository extends PagingAndSortingRepository<UmsRole, Long> {

    @Query("select r \n" +
            "        from UmsAdminRoleRelation ar left join UmsRole r on ar.roleId = r.id\n" +
            "        where ar.adminId = :adminId")
    List<UmsRole> getAllByAdminId(Long adminId);

    void deleteAllByIdIn(List<Long> ids);

    Page<UmsRole> findAllByNameContains(String keyword, Pageable pageable);
}
