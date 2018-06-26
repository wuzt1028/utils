package com.wuzt.util.uuid;

import java.util.UUID;


/**
 * UUID生成
 * @author wuzt
 * @date: 2018年6月25日 下午1:55:06
 */
public class UUIDGenerator {
	
	/**
	 * 获取uuid（带'-'分隔符）
	 * @return
	 */
	public static String getUUIDBar(){
		String uuidStr = UUID.randomUUID().toString();
		return uuidStr;
	}
	
	/**
	 * 获取uuid（不带有'-'分隔符）
	 * @return
	 */
	public static String getUUIDStr(){
		String uuidStr = getUUIDBar();
		return uuidStr.substring(0, 8) + uuidStr.substring(9, 13) + uuidStr.substring(14, 18) 
				+ uuidStr.substring(19, 23) + uuidStr.substring(24);
	}
	
}
