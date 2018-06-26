package com.wuzt.util.email;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.wuzt.util.configfile.ConfigFileUtil;

/**
 * 邮件工具类
 * @author wuzt
 * @date: 2018年6月26日 下午1:26:24
 */
public class EmailUtil {
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map<String, Object> getJsonMap(boolean success, String message,
			Object entity) {
		HashMap json = new HashMap(4);
		json.put("success", Boolean.valueOf(success));
		json.put("message", message);
		json.put("entity", entity);
		return json;
	}
	
	/**
	 * 邮件发送
	 * @param senderEmail  发送方email
	 * @param authCode  发送方email第三方授权码
	 * @param receiveEmail  接收方email 
	 * @param subject  邮件标题
	 * @param content  邮件内容（可写html格式字符串，显示样式）
	 * @return
	 */
	public Map<String,Object> sendEmail(final String senderEmail,final String authCode,String receiveEmail,String subject,String content){
		try{
			//根据email发送地址获取主机
			String smtpHost = "";
			if(senderEmail!=null){
				String[] split1 = senderEmail.split("@");
				String string1 = split1[1];
				String[] split2 = string1.split("\\.");
				String string2 = split2[0];
				smtpHost = "smtp."+string2+".com";
			}
			// 1.创建一个程序与邮件服务器会话对象 Session
			Properties props = new Properties();
			props.setProperty("mail.transport.protocol", "SMTP");
			props.setProperty("mail.smtp.host", smtpHost);
			props.setProperty("mail.smtp.port", "25");
			// 指定验证为true
			props.setProperty("mail.smtp.auth", "true");
			props.setProperty("mail.smtp.timeout","1000");
			// 验证账号及密码，密码需要是第三方授权码
			Authenticator auth = new Authenticator() {
				public PasswordAuthentication getPasswordAuthentication(){
					return new PasswordAuthentication(senderEmail, authCode);
			    }
			};
			Session session = Session.getInstance(props, auth);

			// 2.创建一个Message，它相当于是邮件内容
			Message message = new MimeMessage(session);
			// 设置发送者
			message.setFrom(new InternetAddress(senderEmail));
			// 设置发送方式与接收者
			message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveEmail));
			// 设置主题
			message.setSubject(subject);
			// 设置内容
			message.setContent(content, "text/html;charset=utf-8");

			// 3.创建 Transport用于将邮件发送
			Transport.send(message);
			return this.getJsonMap(true, "发送成功", null);
		}catch(Exception e){
			e.printStackTrace();
			return this.getJsonMap(false, "发送失败", e);
		}
	}
	
	/**
	 * util按照本地配置文件发送邮件
	 * @param receiveEmail  接收方email
	 * @param subject  邮件标题
	 * @param content  邮件内容（可写html格式字符串，显示有样式）
	 * @return
	 */
	public Map<String,Object> localSendEmail(String receiveEmail,String subject,String content){
		try{
			//获取配置文件的邮件配置
			ConfigFileUtil config = new ConfigFileUtil();
			final String sender = config.getPropertyValByKey("src\\main\\resources\\config.properties", "email");
			final String authCode = config.getPropertyValByKey("src\\main\\resources\\config.properties", "authCode");
			final String smtpHost = config.getPropertyValByKey("src\\main\\resources\\config.properties", "smtpHost");
			Properties props = new Properties();
			props.setProperty("mail.transport.protocol", "SMTP");
			props.setProperty("mail.smtp.host", smtpHost);
			props.setProperty("mail.smtp.port", "25");
			// 指定验证为true
			props.setProperty("mail.smtp.auth", "true");
			props.setProperty("mail.smtp.timeout","1000");
			// 验证账号及密码，密码需要是第三方授权码
			Authenticator auth = new Authenticator() {
				public PasswordAuthentication getPasswordAuthentication(){
					return new PasswordAuthentication(sender, authCode);
			    }
			};
			Session session = Session.getInstance(props, auth);

			// 2.创建一个Message，它相当于是邮件内容
			Message message = new MimeMessage(session);
			// 设置发送者
			message.setFrom(new InternetAddress(sender));
			// 设置发送方式与接收者
			message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveEmail));
			// 设置主题
			message.setSubject(subject);
			// 设置内容
			message.setContent(content, "text/html;charset=utf-8");

			// 3.创建 Transport用于将邮件发送
			Transport.send(message);
			return this.getJsonMap(true, "发送成功", null);
		}catch(Exception e){
			e.printStackTrace();
			return this.getJsonMap(false, "发送失败", e);
		}
	}
	
}
