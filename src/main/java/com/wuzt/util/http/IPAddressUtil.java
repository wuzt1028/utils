package com.wuzt.util.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

/**
 * @author wuzt
 * @date: 2018年7月12日 下午1:45:04
 */
public class IPAddressUtil {
	
	public static String getIPAddressUrl = "http://ip.taobao.com/service/getIpInfo.php?ip=";
	private static Logger logger = LoggerFactory.getLogger(IPAddressUtil.class);
	
	
	/**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, Map<String,Object> param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setConnectTimeout(1500);//设置超时时间，防止请求链接阻塞
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
        return result;
    }
    
    public static String convertStreamToString(InputStream is) throws IOException {
        if(is != null) {
           StringBuilder sb = new StringBuilder();

           try {
              BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));

              String line;
              while((line = reader.readLine()) != null) {
                 sb.append(line).append("\n");
              }
           } finally {
              is.close();
           }

           return sb.toString();
        } else {
           return "";
        }
     }
    
    public static String visitWeb(String urlStr) {
        URL url = null;
        HttpURLConnection httpConn = null;
        InputStream in = null;

        try {
           url = new URL(urlStr);
           httpConn = (HttpURLConnection)url.openConnection();
           HttpURLConnection.setFollowRedirects(true);
           httpConn.setRequestMethod("GET");
           httpConn.setRequestProperty("User-Agent", "Mozilla/4.0(compatible;MSIE 6.0;Windows 2000)");
           in = httpConn.getInputStream();
           String ex = convertStreamToString(in);
           return ex;
        } catch (MalformedURLException arg15) {
           arg15.printStackTrace();
        } catch (IOException arg16) {
           arg16.printStackTrace();
        } finally {
           try {
              in.close();
              httpConn.disconnect();
           } catch (Exception e) {
              e.printStackTrace();
           }

        }

        return null;
     }
    
    public static String getAddressByIP(String ip) {
    	String address = "未知";
    	try {
           String str = visitWeb(getIPAddressUrl + ip);
           logger.info("ip::::::"+ip);
           logger.info("result::::::"+str);
           //备用：https://freeapi.ipip.net/ip,不过返回数据格式不一样
           if(str!=null && !"".equals(str)){
        	   JSONObject jsonObject = JSONObject.parseObject(str);
        	   Object code = jsonObject.get("code");
        	   if("0".equals(code.toString())){//请求成功
            	   Map dataMap = (Map) jsonObject.get("data");
            	   String province = dataMap.get("region").toString();
            	   String city = dataMap.get("city").toString();
            	   if(province!=null && !"".equals(province)){//判断省是否存在
            		   if(city!=null){
            			   if("".equals(city) || "XX".equals(city) || province.equals(city)){//判断市是否为空或者为XX或者是直辖市，是就直接输出省
            				   address = province;
	            		   }else{//按照"省-市"的方法拼接地址串，直辖市直接显示
	            			   address = province + "-" + city;
	            		   }
            		   }
            	   }
        	   }
           }
           return address;
        } catch (Exception e) {
           e.printStackTrace();
           return address;
        }
     }
}
