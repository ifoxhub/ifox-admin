package com.ifox.admin.modules.ums.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;

/**
 * @author zhangxl
 * @version v1.0
 * @date 2021/1/7
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UmsAdminLoginParam {
    @NotEmpty
    @Schema(description = "用户名",required = true)
    private String username;
    @NotEmpty
    @Schema(description = "密码",required = true)
    private String password;
}
