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
@Schema(description="后台菜单表")
@Entity
public class UmsMenu implements Serializable {

    private static final long serialVersionUID=1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "父级ID")
    private Long parentId;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "菜单名称")
    private String title;

    @Schema(description = "菜单级数")
    private Integer level;

    @Schema(description = "菜单排序")
    private Integer sort;

    @Schema(description = "前端名称")
    private String name;

    @Schema(description = "前端图标")
    private String icon;

    @Schema(description = "前端隐藏")
    private Integer hidden;


}
