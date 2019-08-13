package com.cloud.common.db.sync.provider.sync;

import com.cloud.common.db.sync.provider.entity.JobInfo;

import java.sql.Connection;
import java.sql.SQLException;




/**
 * 同步数据库的抽象接口
 * @author wangpan
 *
 */
public interface DBSync {
	/**
	 * 
	 * @param paramString:同步参数
	 * @param paramConnection：数据库连接
	 * @param paramJobInfo：同步任务
	 * @return
	 * @throws SQLException
	 */
	String assembleSQL(String paramString, Connection paramConnection, JobInfo paramJobInfo) throws SQLException;
	/**
	 * 
	 * @param sql：要执行的SQL语句
	 * @param conn：数据库连接
	 * @throws SQLException
	 */
	void executeSQL(String sql, Connection conn) throws SQLException;
	

}
