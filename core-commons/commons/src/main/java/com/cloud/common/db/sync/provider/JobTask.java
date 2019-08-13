package com.cloud.common.db.sync.provider;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;

import com.cloud.common.db.sync.provider.entity.DBInfo;
import com.cloud.common.db.sync.provider.entity.JobInfo;
import com.cloud.common.db.sync.provider.factory.DBSyncFactory;
import com.cloud.common.db.sync.provider.sync.DBSync;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;


/**
 * 同步数据库任务的具体实现
 * @author wangpan
 *
 */
@Slf4j
public class JobTask implements Job {

	/**
	 * 执行同步数据库任务
	 *
	 */
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		log.info("开始任务调度: " + new Date());
		Connection inConn = null;
		Connection outConn = null;
		JobDataMap data = context.getJobDetail().getJobDataMap();
		DBInfo srcDb = (DBInfo) data.get("srcDb");
		DBInfo destDb = (DBInfo) data.get("destDb");
		JobInfo jobInfo = (JobInfo) data.get("jobInfo");
		String logTitle = (String) data.get("logTitle");
		try {
			inConn = createConnection(srcDb);
			outConn = createConnection(destDb);
			if (inConn == null) {
				log.info("请检查源数据连接!");
				return;
			} else if (outConn == null) {
				log.info("请检查目标数据连接!");
				return;
			}

			DBSync dbHelper = DBSyncFactory.create(destDb.getDbtype());
			long start = new Date().getTime();
			String sql = dbHelper.assembleSQL(jobInfo.getSrcSql(), inConn, jobInfo);
			log.info("组装SQL耗时: " + (new Date().getTime() - start) + "ms");
			if (sql != null) {
				log.debug(sql);
				long eStart = new Date().getTime();
				dbHelper.executeSQL(sql, outConn);
				log.info("执行SQL语句耗时: " + (new Date().getTime() - eStart) + "ms");
			}
		} catch (SQLException e) {
			log.error(logTitle + e.getMessage());
			log.error(logTitle + " SQL执行出错，请检查是否存在语法错误");
		} finally {
			log.error("关闭源数据库连接");
			destoryConnection(inConn);
			log.error("关闭目标数据库连接");
			destoryConnection(outConn);
		}
	}

	/**
	 * 创建数据库连接
	 * @param db
	 * @return
	 */
	private Connection createConnection(DBInfo db) {
		try {
			Class.forName(db.getDriver());
			Connection conn = DriverManager.getConnection(db.getUrl(), db.getUsername(), db.getPassword());
			conn.setAutoCommit(false);
			return conn;
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	/**
	 * 关闭并销毁数据库连接
	 * @param conn
	 */
	private void destoryConnection(Connection conn) {
		try {
			if (conn != null) {
				conn.close();
				conn = null;
				log.error("数据库连接关闭");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
