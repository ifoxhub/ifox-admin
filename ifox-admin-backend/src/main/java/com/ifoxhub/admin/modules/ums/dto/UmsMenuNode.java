package com.ifoxhub.admin.modules.ums.dto;

import com.ifoxhub.admin.modules.ums.model.UmsMenu;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author zhangxl
 * @version v1.0
 * @date 2021/1/7
 */
@Getter
@Setter
public class UmsMenuNode extends UmsMenu {
    @Schema(description = "子级菜单")
    private List<UmsMenuNode> children;
}
