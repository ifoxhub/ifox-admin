package com.ifox.admin.modules.ums.repository;

import com.ifox.admin.modules.ums.model.UmsAdminLoginLog;
import org.springframework.data.repository.CrudRepository;

/**
 * @author zhangxl
 * @version v1.0
 * @date 2021/1/5 5:19 下午
 */
public interface UmsAdminLoginLogRepository extends CrudRepository<UmsAdminLoginLog, Long> {
}
