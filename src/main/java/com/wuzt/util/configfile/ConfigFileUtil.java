package com.wuzt.util.configfile;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;

/**
 * 读取配置文件
 * @author wuzt
 * @date: 2018年6月26日 下午1:55:56
 */
public class ConfigFileUtil {
	/**
	 * 获取.properties中(key=value)key对应的value值
	 * @param fileName
	 * @param key
	 * @return
	 */
	public String getPropertyValByKey(String fileName, String key){
		String property = "";
		try{
			Properties prop = new Properties();     
			//读取属性文件a.properties
			InputStream in = new BufferedInputStream (new FileInputStream(fileName));
			prop.load(in);
			//获取key对应的value值
			property = prop.getProperty(key);
			/*
			//加载属性列表
			Iterator<String> it=prop.stringPropertyNames().iterator();
			while(it.hasNext()){
				String keyDto=it.next();
				System.out.println(keyDto+":"+prop.getProperty(key));
			}
			*/
			in.close();
			return property;
		}catch(Exception e){
			e.printStackTrace();
		}
		return key;
	}
	
}
