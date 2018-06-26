package com.wuzt.util.email;

import java.util.Map;

/**
 * 邮件服务测试
 * @author wuzt
 * @date: 2018年6月26日 下午1:23:15
 */
public class EmailTest {
	
	public static void main(String[] args){
		EmailUtil eu = new EmailUtil();
		/*Map<String, Object> sendEmail = eu.localSendEmail("******@163.com", "邮件测试标题", "邮件测试内容");
		System.out.print(sendEmail);*/
		Map<String, Object> sendEmail = eu.sendEmail("******@qq.com", "******", "***@163.com", "邮件格式测试标题", "<h1>邮件格式测试</h1><p style='color:red;font-size:16px'>邮件格式测试内容</p><img src='***.png'>");
		System.out.print(sendEmail);
	}
	
}
