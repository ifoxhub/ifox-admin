package com.ifox.admin.modules.ums.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * @author zhangxl
 * @version v1.0
 * @date 2021/1/7 1:16 下午
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description="后台用户登录日志表")
@Entity
public class UmsAdminLoginLog implements Serializable {

    private static final long serialVersionUID=1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long adminId;

    private Date createTime;

    private String ip;

    private String address;

    @Schema(description = "浏览器登录类型")
    private String userAgent;


}
