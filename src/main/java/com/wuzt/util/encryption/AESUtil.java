package com.wuzt.util.encryption;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * AES加解密，java&php通用
 * 参考url：https://blog.csdn.net/clh604/article/details/20228159
 * js的参考url：https://blog.csdn.net/wd4871/article/details/51461048#
 * @author wuzt
 * @date: 2018年6月26日 上午11:05:06
 */

public class AESUtil {

	/**
	 * AES加密
	 * @param input
	 * @param key
	 * @return
	 */
	public static String encrypt(String input, String key){  
		byte[] crypted = null;  
		try{  
			SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "AES");  
	        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");  
	        cipher.init(Cipher.ENCRYPT_MODE, skey);  
	        crypted = cipher.doFinal(input.getBytes());  
		}catch(Exception e){  
	        System.out.println(e.toString());  
	    }  
	    return new String(Base64.encodeBase64(crypted));  
	}
	
	/**
	 * AES解密
	 * @param input
	 * @param key
	 * @return
	 */
	public static String decrypt(String input, String key){  
	    byte[] output = null;  
	    try{  
	        SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "AES");  
	        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");  
	        cipher.init(Cipher.DECRYPT_MODE, skey);  
	        output = cipher.doFinal(Base64.decodeBase64(input));  
	    }catch(Exception e){  
	        System.out.println(e.toString());  
	    }  
	    return new String(output);  
	}
	   
	/*public static void main(String[] args) {  
	    String key = "1234567891234567";  
	    String data = "example";  
	    System.out.println(AESUtil.encrypt(data, key));  
	    System.out.println(AESUtil.decrypt(AESUtil.encrypt(data, key), key));  
	}*/
      
}
