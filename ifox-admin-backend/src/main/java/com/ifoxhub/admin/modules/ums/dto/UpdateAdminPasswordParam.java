package com.ifoxhub.admin.modules.ums.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

/**
 * @author zhangxl
 * @version v1.0
 * @date 2021/1/7
 */
@Getter
@Setter
public class UpdateAdminPasswordParam {
    @NotEmpty
    @Schema(description = "用户名", required = true)
    private String username;
    @NotEmpty
    @Schema(description = "旧密码", required = true)
    private String oldPassword;
    @NotEmpty
    @Schema(description = "新密码", required = true)
    private String newPassword;
}
