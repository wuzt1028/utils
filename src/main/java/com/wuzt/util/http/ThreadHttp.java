package com.wuzt.util.http;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 线程请求
 * @author wuzt
 * @date: 2018年6月25日 下午15:25:06
 */
public class ThreadHttp implements Runnable{
	
	private Logger logger = LoggerFactory.getLogger(ThreadHttp.class);
	
	//url
	private String postUrl;
	//参数
	private Map<String,String> params;
	
	public ThreadHttp(String postUrl,Map<String,String> params){
		this.postUrl = postUrl;
		this.params = params;
	}
	
	public void run(){
		try{
			Map<String,String> paramsDto = this.params;
			String postUrlDto = this.postUrl;
			String result = HttpUtil.doPost(postUrlDto, paramsDto);
			logger.info("=====url:"+postUrlDto+"---parameter:"+paramsDto+"=====");
			logger.info("=====result:"+result+"=====");
		}catch(Exception e){
			e.printStackTrace();
			logger.info("=====url:"+postUrl+"---parameter:"+params+"=====");
			logger.info("===== 请求失败 =====");
		}
		
	}
	
}
