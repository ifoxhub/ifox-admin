package com.ifoxhub.admin.modules.ums.model;

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
@Schema(description="后台用户角色表")
@Entity
public class UmsRole implements Serializable {

    private static final long serialVersionUID=1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "后台用户数量")
    private Integer adminCount;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "启用状态：0->禁用；1->启用")
    private Integer status;

    private Integer sort;


}
