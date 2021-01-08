package com.ifox.admin.modules.ums.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author zhangxl
 * @version v1.0
 * @date 2021/1/7 1:16 下午
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description="后台角色资源关系表")
@Entity
public class UmsRoleResourceRelation implements Serializable {

    private static final long serialVersionUID=1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "角色ID")
    private Long roleId;

    @Schema(description = "资源ID")
    private Long resourceId;


}
