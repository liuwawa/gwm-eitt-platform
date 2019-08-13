package com.cloud.common.db.sync;

import com.cloud.common.db.sync.build.DBSyncBuilder;


/**
 * 程序入口
 * @author wangpan
 *
 */
public class Main {

	public static void main(String[] args) {
		DBSyncBuilder.builder().init().start();
	}
}
