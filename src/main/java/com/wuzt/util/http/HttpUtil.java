package com.wuzt.util.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.util.URIUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * HttpClient请求
 * @author wuzt
 * @date: 2018年6月25日 下午15:05:06
 */
public class HttpUtil {
	private static final Logger logger = LoggerFactory
			.getLogger(HttpUtil.class);
	
	/**
	 * httpClient的Get请求
	 * @param url
	 * @param queryString
	 * @return
	 */
	public static String doGet(String url, String queryString) {
		String response = null;
		HttpClient client = new HttpClient();
		GetMethod method = new GetMethod(url);
		method.getParams().setParameter("http.protocol.content-charset",
				"UTF-8");

		try {
			if (StringUtils.isNotBlank(queryString)) {
				method.setQueryString(URIUtil.encodeQuery(queryString));
			}

			client.executeMethod(method);
			if (method.getStatusCode() == 200) {
				response = method.getResponseBodyAsString();
			}
		} catch (URIException arg9) {
			;
		} catch (IOException arg10) {
			;
		} finally {
			method.releaseConnection();
		}

		return response;
	}
	
	/**
	 * httpClient的Post请求
	 * @param url
	 * @param params
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String doPost(String url, Map<String, String> params) {
		logger.debug("Post Url:" + url + ",Params:" + params);
		StringBuffer result = new StringBuffer();
		HttpClient client = new HttpClient();
		PostMethod method = new PostMethod(url);
		method.getParams().setParameter("http.protocol.content-charset",
				"UTF-8");
		if (params != null && params.size() > 0) {
			NameValuePair[] e = new NameValuePair[params.size()];
			int str = 0;

			Entry entry;
			for (Iterator i$ = params.entrySet().iterator(); i$.hasNext(); e[str++] = new NameValuePair(
					(String) entry.getKey(), (String) entry.getValue())) {
				entry = (Entry) i$.next();
			}

			method.setRequestBody(e);
		}

		try {
			client.executeMethod(method);
			if (method.getStatusCode() == 200) {
				BufferedReader arg13 = new BufferedReader(
						new InputStreamReader(method.getResponseBodyAsStream(),
								"UTF-8"));
				String arg14 = null;

				while ((arg14 = arg13.readLine()) != null) {
					result.append(arg14);
				}
			}
		} catch (IOException arg11) {
			;
		} finally {
			method.releaseConnection();
		}

		logger.debug("Post Return:" + result.toString());
		return result.toString();
	}
	
	/**
	 * 启用线程去请求（适用于不等待不考虑结果的响应请求）
	 * @param url
	 * @param params
	 */
	public void doThreadPost(String url, Map<String, String> params) {
		Thread thread = new Thread(new ThreadHttp(url,params));
		thread.start();
	}
}