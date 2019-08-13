package com.cloud.common.db.sync.provider.factory;

import com.cloud.common.db.sync.provider.constants.Constants;
import com.cloud.common.db.sync.provider.sync.DBSync;
import com.cloud.common.db.sync.provider.sync.impl.MySQLSync;
import com.cloud.common.db.sync.provider.sync.impl.SQLServerSync;
import com.cloud.common.db.sync.provider.utils.StringUtils;

/**
 * 创建同步对象的工厂类
 * @author wangpan
 *
 */
public class DBSyncFactory {
	
	/**
	 * 根据数据库的类型创建不同的同步数据库数据的对象
	 * @param type:数据库类型
	 * @return：同步数据库数据的对象
	 */
	public static DBSync create(String type) {
		if (StringUtils.isEmpty(type)) return null;
		switch (type) {
		case Constants.TYPE_DB_MYSQL:
			return new MySQLSync();
		case Constants.TYPE_DB_SQLSERVER:
			return new SQLServerSync();
		default:
			return null;
		}
	}
}
