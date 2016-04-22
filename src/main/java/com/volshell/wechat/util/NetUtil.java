package com.volshell.wechat.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author volshell
 * @version 1.0
 * 
 *          实现get/post方式发送http带参请求
 */
public class NetUtil {

	/**
	 * @param url
	 *            请求地址
	 * @param param
	 *            请求参数
	 * @return
	 */
	public static String sendGet(String url, Map<String, String> params) {
		StringBuilder requestParams = new StringBuilder();
		Set<String> keys = params.keySet();
		Iterator<String> iterator = keys.iterator();

		// 合成参数
		String param;
		// 接受返回结果
		String result = "";
		while (iterator.hasNext()) {
			String key = iterator.next();
			requestParams.append(key).append("=").append(params.get(key)).append("&");
		}
		param = requestParams == null ? "" : "?" + requestParams.toString().substring(0, requestParams.length()-1);

		BufferedReader in = null;
		try {
			String urlNameString = url + param;
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
			for (String key : map.keySet()) {
				System.out.println(key + "--->" + map.get(key));
			}
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * post方式发起带参数请求
	 * 
	 * @param url
	 *            post请求地址
	 * @param param
	 *            post请求参数
	 * @return 返回
	 */
	public static String sendPost(String url, Map<String, String> params) {
		StringBuilder requestParams = new StringBuilder();
		Set<String> keys = params.keySet();
		Iterator<String> iterator = keys.iterator();

		// 合成参数
		String param;
		// 接受返回结果
		String result = "";
		while (iterator.hasNext()) {
			String key = iterator.next();
			requestParams.append(key).append("=").append(params.get(key));
		}
		param = requestParams == null ? "" : "?" + requestParams.toString();

		PrintWriter out = null;
		BufferedReader in = null;
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	public static void main(String[] args) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("grant_type", "client_credential");
		params.put("appid", "wxbf346157c5763fd5");
		params.put("secret", "3bc403058c7dfb734166fbbfb20bcdeb");
		String url = "https://api.weixin.qq.com/cgi-bin/token";
		String result = sendGet(url, params);

		System.out.println(result);
		
	}
}
