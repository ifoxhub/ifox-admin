package com.ifoxhub.admin.modules.ums.service.impl;

import com.google.common.collect.Lists;
import com.ifoxhub.admin.modules.ums.dto.UmsMenuNode;
import com.ifoxhub.admin.modules.ums.model.UmsMenu;
import com.ifoxhub.admin.modules.ums.repository.UmsMenuRepository;
import com.ifoxhub.admin.modules.ums.service.UmsMenuService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhangxl
 * @version v1.0
 * @date 2021/1/7
 */
@Service
public class UmsMenuServiceImpl implements UmsMenuService {

    @Resource
    private UmsMenuRepository menuRepository;

    @Override
    public boolean create(UmsMenu umsMenu) {
        umsMenu.setCreateTime(new Date());
        updateLevel(umsMenu);
        menuRepository.save(umsMenu);
        return true;
    }

    /**
     * 修改菜单层级
     */
    private void updateLevel(UmsMenu umsMenu) {
        if (umsMenu.getParentId() == 0) {
            //没有父菜单时为一级菜单
            umsMenu.setLevel(0);
        } else {
            //有父菜单时选择根据父菜单level设置
            UmsMenu parentMenu = menuRepository.findById(umsMenu.getParentId()).orElse(null);
            if (parentMenu != null) {
                umsMenu.setLevel(parentMenu.getLevel() + 1);
            } else {
                umsMenu.setLevel(0);
            }
        }
    }

    @Override
    public boolean update(Long id, UmsMenu umsMenu) {
        umsMenu.setId(id);
        updateLevel(umsMenu);
        menuRepository.save(umsMenu);
        return true;
    }

    @Override
    public Page<UmsMenu> list(Long parentId, Integer pageSize, Integer pageNum) {
        Pageable pageable = PageRequest.of(--pageNum, pageSize, Sort.Direction.DESC, "sort");
        return menuRepository.findAllByParentId(parentId, pageable);
    }

    @Override
    public List<UmsMenuNode> treeList() {
        List<UmsMenu> menuList = Lists.newArrayList(menuRepository.findAll());
        List<UmsMenuNode> result = menuList.stream()
                .filter(menu -> menu.getParentId().equals(0L))
                .map(menu -> covertMenuNode(menu, menuList)).collect(Collectors.toList());
        return result;
    }

    @Override
    public boolean updateHidden(Long id, Integer hidden) {
        UmsMenu umsMenu = new UmsMenu();
        umsMenu.setId(id);
        umsMenu.setHidden(hidden);
        menuRepository.save(umsMenu);
        return true;
    }

    @Override
    public UmsMenu getById(Long id) {
        return menuRepository.findById(id).orElse(null);
    }

    @Override
    public boolean removeById(Long id) {
        menuRepository.deleteById(id);
        return true;
    }

    /**
     * 将UmsMenu转化为UmsMenuNode并设置children属性
     */
    private UmsMenuNode covertMenuNode(UmsMenu menu, List<UmsMenu> menuList) {
        UmsMenuNode node = new UmsMenuNode();
        BeanUtils.copyProperties(menu, node);
        List<UmsMenuNode> children = menuList.stream()
                .filter(subMenu -> subMenu.getParentId().equals(menu.getId()))
                .map(subMenu -> covertMenuNode(subMenu, menuList)).collect(Collectors.toList());
        node.setChildren(children);
        return node;
    }
}
