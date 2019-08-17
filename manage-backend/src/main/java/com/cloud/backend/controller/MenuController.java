package com.cloud.backend.controller;

import java.util.*;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSONArray;
import com.cloud.common.constants.SysConstants;
import com.cloud.common.vo.ResultVo;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import com.cloud.backend.model.Menu;
import com.cloud.backend.service.MenuService;
import com.cloud.common.utils.AppUserUtil;
import com.cloud.model.log.LogAnnotation;
import com.cloud.model.log.constants.LogModule;
import com.cloud.model.user.LoginAppUser;
import com.cloud.model.user.SysRole;
@Slf4j
@RestController
@RequestMapping("/menus")
@Api(value = "后台侧边栏菜单", tags = {"后台侧边栏菜单接口 MenuController"})
public class MenuController {

    @Autowired
    private MenuService menuService;

    /**
     * 当前登录用户的菜单(element ui)
     *
     * @return
     */
    @PostMapping("/me2")
    @ApiOperation(value = "当前登录用户的菜单")
    public Map findMyMenu2() {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        assert loginAppUser != null;
        Set<SysRole> roles = loginAppUser.getSysRoles();
        if (CollectionUtils.isEmpty(roles)) {
            return null;
        }

        List<Menu> menus = menuService
                .findByRoles(roles.parallelStream().map(SysRole::getId).collect(Collectors.toSet()));
        return buildLevelMenus(addParentName(menus.parallelStream().distinct().collect(Collectors.toList())));
    }

    /**
     * 构造菜单树，封装响应结果
     *
     * @param menus
     * @return
     */
    private Map buildLevelMenus(List<Menu> menus) {
        List<Menu> firstLevelMenus;
        HashMap<Object, Object> reslut = new HashMap<>();
        firstLevelMenus = menus.stream().filter(m -> m.getParentId().equals(0L))
                .collect(Collectors.toList());
        firstLevelMenus.forEach(m -> setChildren(m, menus));
        reslut.put("code", 200);
        reslut.put("msg", null);
        reslut.put("data", firstLevelMenus);
        return reslut;
    }

    /**
     * 设置 menu 的parentName属性
     * @param menus
     * @return
     */
    private List<Menu> addParentName(List<Menu> menus) {
        for (Menu m : menus) {
            if (m.getParentId() == 0) {
                m.setParentName("顶级菜单");
            }
            if (m.getParentId() > 0) {
                m.setParentName(menuService.findById(m.getParentId()).getName());
            }
        }
        return menus;
    }


    /**
     * element  ui  数据
     *
     * @param menu
     * @param menus
     */
    private void setChildren(Menu menu, List<Menu> menus) {
        List<Menu> child = menus.stream().filter(m -> m.getParentId().equals(menu.getId()))
                .collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(child)) {
            menu.setChildren(child);
            //递归设置子元素，多级菜单支持
            child.parallelStream().forEach(c -> {
                setChildren(c, menus);
            });
        }
    }


    /**
     * 给角色分配菜单(element ui)
     */
    @LogAnnotation(module = LogModule.SET_MENU_ROLE)
    @PreAuthorize("hasAuthority('back:menu:set2role')")
    @PostMapping("/setMenusToRole")
    @ApiOperation(value = "给角色分配菜单", notes = "参数：roleId，menuIds（数组）")
    @ApiResponses({@ApiResponse(code = 200, message = "响应成功"), @ApiResponse(code = 500, message = "操作错误")})
    public ResultVo setMenusToRole(@RequestBody Map<String, Object> params) {
        try {
            Long roleId = Long.valueOf(params.get("roleId").toString());
            HashSet<Long> menuIds = new HashSet<>(JSONArray.parseArray(params.get("menuIds").toString(), Long.class));
            menuService.setMenuToRole(roleId, menuIds);
            return ResultVo.builder().code(200).build();
        } catch (Exception e) {
            log.info(e + "");
            return ResultVo.builder().code(5000).msg("请联系管理员!").build();
        }
    }



    /**
     * 菜单树ztree (element ui)
     */
    @PreAuthorize("hasAnyAuthority('back:menu:set2role','back:menu:query')")
    @PostMapping("/findMenuTree2")
    @ApiOperation(value = "菜单树ztree")
    public Map findMenuTree2() {
        List<Menu> all = menuService.findAll();
        List<Menu> list = new ArrayList<>();
        setMenuTree2(0L, all, list);
        return buildLevelMenus(addParentName(list));
    }

    /**
     * 菜单树 （element ui）
     *
     * @param parentId
     * @param all
     * @param list
     */
    private void setMenuTree2(Long parentId, List<Menu> all, List<Menu> list) {
        all.forEach(menu -> {
            if (parentId.equals(menu.getParentId())) {
                list.add(menu);

                List<Menu> child = new ArrayList<>();
                menu.setChildren(child);
                setMenuTree2(menu.getId(), all, child);
            }
        });
    }

    /**
     * 获取角色的菜单
     *
     * @param roleId
     */
    @PreAuthorize("hasAnyAuthority('back:menu:set2role','menu:byroleid')")
    @GetMapping(params = "roleId")
    @ApiOperation(value = "获取角色的菜单")
    public Set<Long> findMenuIdsByRoleId(Long roleId) {
        return menuService.findMenuIdsByRoleId(roleId);
    }

    /**
     * 获取角色的菜单(element ui)
     *
     * @param roleId
     */
    @PreAuthorize("hasAnyAuthority('back:menu:set2role','menu:byroleid')")
    @PostMapping("findMenusByRoleId")
    @ApiOperation(value = "获取角色的菜单")
    public List<Menu> findMenusByRoleId(@ApiParam(value = "roleId", required = true) @RequestParam("roleId") Long roleId) {
        List<Menu> menusByRoleId = menuService.findMenusByRoleId(roleId);
        return menusByRoleId.parallelStream().distinct().collect(Collectors.toList());
    }

    /**
     * 添加/修改菜单 (element ui 添加菜单)
     *
     * @param menu
     */
    @LogAnnotation(module = LogModule.ADD_MENU)
    @PreAuthorize("hasAuthority('back:menu:save')")
    @PostMapping("/saveOrUpdate")
    @ApiOperation(value = "添加/修改菜单")
    @ApiResponses({@ApiResponse(code = 200, message = "响应成功"), @ApiResponse(code = 500, message = "操作错误")})
    public ResultVo saveOrUpdate(@RequestBody Menu menu) {
        if (menu.getId() != null && menu.getId() != 0) {
            menuService.update(menu);
            return ResultVo.builder().code(200).msg("更新成功!").data(menu).build();
        }
        menuService.save(menu);
        //给超级管理员角色添加权限
        HashSet<Long> menuIds = new HashSet<>();
        List<Menu> menusByRoleId = menuService.findMenusByRoleId(SysConstants.ADMIN_ROLE_ID);
        for (Menu menu1 : menusByRoleId) {
            menuIds.add(menu1.getId());
        }
        menuIds.add(menu.getId());
        menuService.setMenuToRole(SysConstants.ADMIN_ROLE_ID, menuIds);
        return ResultVo.builder().code(200).msg("添加成功!").data(menu).build();
    }


    /**
     * 修改菜单
     *
     * @param menu
     */
    @LogAnnotation(module = LogModule.UPDATE_MENU)
    @PreAuthorize("hasAuthority('back:menu:update')")
    @PutMapping
    @ApiOperation(value = "修改菜单")
    public Menu update(@RequestBody Menu menu) {
        menuService.update(menu);

        return menu;
    }

    /**
     * 删除菜单
     *
     * @param id
     */
    @LogAnnotation(module = LogModule.DELETE_MENU)
    @PreAuthorize("hasAuthority('back:menu:delete')")
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除菜单")
    public void delete(@PathVariable Long id) {
        menuService.delete(id);
    }

    /**
     * 删除菜单 (element ui)
     *
     * @param ids
     */
    @LogAnnotation(module = LogModule.DELETE_MENU)
    @PreAuthorize("hasAuthority('back:menu:delete')")
    @PostMapping("/delete2")
    @ApiOperation(value = "删除菜单")
    public void delete2(@RequestBody List<Map<String, Long>> ids) {
        for (Map<String, Long> id : ids) {
            menuService.delete(id.get("id"));
        }
    }

    /**
     * 查询所有菜单
     */
    @PreAuthorize("hasAuthority('back:menu:query')")
    @GetMapping("/all")
    @ApiOperation(value = "查询所有菜单")
    public List<Menu> findAll() {
        List<Menu> all = menuService.findAll();
        List<Menu> list = new ArrayList<>();
        setSortTable(0L, all, list);

        return list;
    }

    /**
     * 菜单table
     *
     * @param parentId
     * @param all
     * @param list
     */
    private void setSortTable(Long parentId, List<Menu> all, List<Menu> list) {
        all.forEach(a -> {
            if (a.getParentId().equals(parentId)) {
                list.add(a);
                setSortTable(a.getId(), all, list);
            }
        });
    }

    @PreAuthorize("hasAuthority('back:menu:query')")
    @GetMapping("/{id}")
    public Menu findById(@PathVariable Long id) {
        return menuService.findById(id);
    }

}
