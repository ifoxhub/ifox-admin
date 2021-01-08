package com.ifox.admin.modules.ums.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

/**
 * @author zhangxl
 * @version v1.0
 * @date 2021/1/7
 */
@Getter
@Setter
public class UmsAdminParam {
    @NotEmpty
    @Schema(description = "用户名", required = true)
    private String username;
    @NotEmpty
    @Schema(description = "密码", required = true)
    private String password;
    @Schema(description = "用户头像")
    private String icon;
    @Email
    @Schema(description = "邮箱")
    private String email;
    @Schema(description = "用户昵称")
    private String nickName;
    @Schema(description = "备注")
    private String note;
}
