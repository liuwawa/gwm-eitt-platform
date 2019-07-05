package com.cloud.backend.controller;

import java.util.*;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSONArray;
import com.cloud.common.constants.SysConstants;
import com.cloud.common.vo.Response;
import com.cloud.common.vo.ResultVo;
import org.apache.ibatis.annotations.Param;
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

@RestController
@RequestMapping("/menus")
public class MenuController {

	@Autowired
	private MenuService menuService;

	/**
	 * 当前登录用户的菜单
	 * 
	 * @return
	 */
	@GetMapping("/me")
	public List<Menu> findMyMenu() {
		LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
		Set<SysRole> roles = loginAppUser.getSysRoles();
		if (CollectionUtils.isEmpty(roles)) {
			return Collections.emptyList();
		}

		List<Menu> menus = menuService
				.findByRoles(roles.parallelStream().map(SysRole::getId).collect(Collectors.toSet()));

		List<Menu> firstLevelMenus = menus.stream().filter(m -> m.getParentId().equals(0L))
				.collect(Collectors.toList());
		firstLevelMenus.forEach(m -> {
			setChild(m, menus);
		});

		return firstLevelMenus;
	}
	/**
	 * 当前登录用户的菜单(element ui)
	 *
	 * @return
	 */
	@PostMapping("/me2")
	public Map findMyMenu2() {
		LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
		assert loginAppUser != null;
		if (loginAppUser.getId().equals(SysConstants.ADMIN_USER_ID)){ //当前用户是超级管理员，拥有所有菜单权限
			List<Menu> menus = menuService.findAll();
			return buildLevelMenus(menus);
		}
		Set<SysRole> roles = loginAppUser.getSysRoles();
		if (CollectionUtils.isEmpty(roles)) {
			return null;
		}

		List<Menu> menus = menuService
				.findByRoles(roles.parallelStream().map(SysRole::getId).collect(Collectors.toSet()));
		return buildLevelMenus(menus);
	}

	/**
	 * 构造菜单树，封装响应结果
	 * @param menus
	 * @return
	 */
	private Map buildLevelMenus(List<Menu> menus){
		List<Menu> firstLevelMenus;
		HashMap<Object, Object> reslut = new HashMap<>();
		firstLevelMenus = menus.stream().filter(m -> m.getParentId().equals(0L))
				.collect(Collectors.toList());
		firstLevelMenus.forEach(m -> setChildren(m, menus));
		reslut.put("code",200);
		reslut.put("msg",null);
		reslut.put("data",firstLevelMenus);
		return reslut;
	}

	/**
	 * lay ui 数据
	 * @param menu
	 * @param menus
	 */
	private void setChild(Menu menu, List<Menu> menus) {
		List<Menu> child = menus.stream().filter(m -> m.getParentId().equals(menu.getId()))
				.collect(Collectors.toList());
		if (!CollectionUtils.isEmpty(child)) {
			menu.setChild(child);
//			menu.setChildren(child);
			//递归设置子元素，多级菜单支持
			child.parallelStream().forEach(c -> {
				setChild(c, menus);
			});
		}
	}

	/**
	 * element  ui  数据
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
	 * 给角色分配菜单
	 *
	 * @param roleId  角色id
	 * @param menuIds 菜单ids
	 */
	@LogAnnotation(module = LogModule.SET_MENU_ROLE)
	@PreAuthorize("hasAuthority('back:menu:set2role')")
	@PostMapping("/toRole")
	public void setMenuToRole(Long roleId, @RequestBody Set<Long> menuIds) {
		menuService.setMenuToRole(roleId, menuIds);
	}

	/**
	 * 给角色分配菜单(element ui)
	 *
	 */
	@LogAnnotation(module = LogModule.SET_MENU_ROLE)
	@PreAuthorize("hasAuthority('back:menu:set2role')")
	@PostMapping("/setMenusToRole")
	public void setMenusToRole(@RequestBody Map<String,Object> params) {
		Long roleId = Long.valueOf(params.get("roleId").toString());
        HashSet<Long> menuIds = new HashSet<>(JSONArray.parseArray(params.get("menuIds").toString(), Long.class));
		menuService.setMenuToRole(roleId, menuIds);
	}

	/**
	 * 菜单树ztree (lay ui)
	 */
	@PreAuthorize("hasAnyAuthority('back:menu:set2role','back:menu:query')")
	@GetMapping("/tree")
	public List<Menu> findMenuTree() {
		List<Menu> all = menuService.findAll();
		List<Menu> list = new ArrayList<>();
		setMenuTree(0L, all, list);
		return list;
	}

	/**
	 * 菜单树ztree (element ui)
	 */
	@PreAuthorize("hasAnyAuthority('back:menu:set2role','back:menu:query')")
	@GetMapping("/tree2")
	public List<Menu> findMenuTree2() {
		List<Menu> all = menuService.findAll();
		List<Menu> list = new ArrayList<>();
		setMenuTree2(0L, all, list);
		return list;
	}

	/**
	 * 菜单树 （lay ui）
	 * 
	 * @param parentId
	 * @param all
	 * @param list
	 */
	private void setMenuTree(Long parentId, List<Menu> all, List<Menu> list) {
		all.forEach(menu -> {
			if (parentId.equals(menu.getParentId())) {
				list.add(menu);

				List<Menu> child = new ArrayList<>();
				menu.setChild(child);
//				menu.setChildren(child);
				setMenuTree(menu.getId(), all, child);
			}
		});
	}

	/**
	 * 菜单树 （element ui）
	 * @param parentId
	 * @param all
	 * @param list
	 */
	private void setMenuTree2(Long parentId, List<Menu> all, List<Menu> list) {
		all.forEach(menu -> {
			if (parentId.equals(menu.getParentId())) {
				list.add(menu);

				List<Menu> child = new ArrayList<>();
//				menu.setChild(child);
				menu.setChildren(child);
				setMenuTree(menu.getId(), all, child);
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
	public List<Menu> findMenusByRoleId(@RequestParam("roleId")Long roleId) {
		return menuService.findMenusByRoleId(roleId);
	}

	/**
	 * 添加/修改菜单 (element ui 添加菜单)
	 * 
	 * @param menu
	 */
	@LogAnnotation(module = LogModule.ADD_MENU)
	@PreAuthorize("hasAuthority('back:menu:save')")
	@PostMapping("/saveOrUpdate")
	public ResultVo saveOrUpdate(@RequestBody Menu menu) {
		if (menu.getId()!= null && menu.getId() != 0){
			menuService.update(menu);
			return ResultVo.builder().code(200).msg("更新成功!").data(menu).build();
		}
		menuService.save(menu);
		return ResultVo.builder().code(200).msg("添加成功!").data(menu).build();
	}

	/**
	 * 添加菜单 （ lay ui 添加菜单）
	 *
	 * @param menu
	 */
	@LogAnnotation(module = LogModule.ADD_MENU)
	@PreAuthorize("hasAuthority('back:menu:save')")
	@PostMapping
	public Menu save(@RequestBody Menu menu) {
		menuService.save(menu);

		return menu;
	}

	/**
	 * 修改菜单
	 * 
	 * @param menu
	 */
	@LogAnnotation(module = LogModule.UPDATE_MENU)
	@PreAuthorize("hasAuthority('back:menu:update')")
	@PutMapping
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
	public void delete2(@RequestBody List<Map<String,Long>> ids) {
		for (Map<String, Long> id : ids) {
			menuService.delete(id.get("id"));
		}
//		for (Map id : ids) {
//			Long.valueOf(id.get("id").toString());
//
//		}
	}

	/**
	 * 查询所有菜单
	 */
	@PreAuthorize("hasAuthority('back:menu:query')")
	@GetMapping("/all")
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
