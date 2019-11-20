package com.gwm.one.backend.service.impl;

import java.util.*;

import com.gwm.one.backend.service.MenuService;
import com.gwm.one.common.constants.SysConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.gwm.one.backend.dao.MenuDao;
import com.gwm.one.backend.dao.RoleMenuDao;
import com.gwm.one.backend.model.Menu;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MenuServiceImpl implements MenuService {

	@Autowired
	private MenuDao menuDao;
	@Autowired
	private RoleMenuDao roleMenuDao;

	@Transactional
	@Override
	public void save(Menu menu) {
		menu.setCreateTime(new Date());
		menu.setUpdateTime(menu.getCreateTime());
		menuDao.save(menu);
		log.info("新增菜单：{}", menu);
	}

	@Transactional
	@Override
	public void update(Menu menu) {
		menu.setUpdateTime(new Date());

		menuDao.update(menu);
		log.info("修改菜单：{}", menu);
	}

	@Transactional
	@Override
	public void delete(Long id) {
		Menu menu = menuDao.findById(id);

		menuDao.deleteByParentId(id);
		menuDao.delete(id);
		roleMenuDao.delete(null, id);

		log.info("删除菜单：{}", menu);
	}

	/**
	 * 给角色设置菜单<br>
	 * 我们这里采用先删除后插入，这样更简单
	 *
	 * @param roleId
	 * @param menuIds
	 */
	@Transactional
	@Override
	public void setMenuToRole(Long roleId, Set<Long> menuIds) {
		if(roleId != null){
			roleMenuDao.delete(roleId, null);
		}

		if (!CollectionUtils.isEmpty(menuIds)) {
			menuIds.forEach(menuId -> roleMenuDao.save(roleId, menuId));
		}
	}

	@Override
	public List<Menu> findByRoles(Set<Long> roleIds) {
		if (roleIds.contains(SysConstants.ADMIN_ROLE_ID)){
			return menuDao.findAll();
		}
		return roleMenuDao.findMenusByRoleIds(roleIds);
	}

	@Override
	public List<Menu> findAll() {
		return menuDao.findAll();
	}

	@Override
	public Menu findById(Long id) {
		return menuDao.findById(id);
	}

	@Override
	public Set<Long> findMenuIdsByRoleId(Long roleId) {
		return roleMenuDao.findMenuIdsByRoleId(roleId);
	}

	@Override
	public List<Menu> findMenusByRoleId(Long roleId) {
		if (SysConstants.ADMIN_ROLE_ID.equals(roleId)){//超级管理员,拥有所有菜单
			return menuDao.findAll();
		}
		Set<Long> roleIds = new HashSet<>();
		roleIds.add(roleId);
		if (roleIds.size() == 0){
			return Collections.emptyList();
		}
		return roleMenuDao.findMenusByRoleIds(roleIds);
	}

}
