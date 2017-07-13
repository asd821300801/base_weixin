package com.lingdu.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtil {
	public static String sendHttpRequest(String urlStr, String method, String params, String charset)
			throws IOException {
		PrintWriter pw = null;
		BufferedReader rd = null;
		HttpURLConnection conn = null;
		try {
			// 创建URL对象
			URL url = new URL(urlStr);
			// 打开http连接
			conn = (HttpURLConnection) url.openConnection();
			// 设置请求方法
			conn.setRequestMethod(method);
			// 设置连接超时时间
			conn.setConnectTimeout(1000 * 60);
			// 读取数据超时时间
			conn.setReadTimeout(1000 * 60);
			if (params != null) {
				conn.setDoOutput(true);// 写数据到对方服务器

			}
			conn.setDoInput(true);// 从对方服务器读取数据
			// 不使用缓存
			conn.setUseCaches(false);
			// 写入数据到对方服务器
			if (params != null) {
				pw = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(), charset));
				pw.write(params);
				pw.flush();
			}
			// 从对方服务器读取数据
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), charset));
			StringBuilder strbuf = new StringBuilder();
			String line = null;
			while ((line = rd.readLine()) != null) {
				strbuf.append(line).append("\r\n");
			}
			return strbuf.toString();
		} finally {
			if (pw != null) {
				try {
					pw.close();
				} catch (Throwable e) {

				}
			}
			if (rd != null) {
				try {
					rd.close();
				} catch (Throwable e) {

				}
			}
			if (conn != null) {
				try {
					conn.disconnect();
				} catch (Throwable e) {

				}
			}
		}

	}

	/**
	 * 上传附件
	 * 
	 * @param urlStr
	 * @param filepath
	 * @param charset
	 * @return
	 */
	public static String upload(String urlStr, String filepath, String charset) throws IOException {
		PrintWriter pw = null;
		BufferedReader rd = null;
		HttpURLConnection conn = null;
		BufferedInputStream bufinput = null;
		try {
			// 创建URL对象
			URL url = new URL(urlStr);
			// 打开http连接
			conn = (HttpURLConnection) url.openConnection();
			// 设置请求方法
			conn.setRequestMethod("POST");
			// 设置连接超时时间
			conn.setConnectTimeout(1000 * 60);
			// 读取数据超时时间
			conn.setReadTimeout(1000 * 60);
			conn.setDoOutput(true);// 写数据到对方服务器
			conn.setDoInput(true);// 从对方服务器读取数据
			// 不使用缓存
			conn.setUseCaches(false);
			// 写入数据到对方服务器
			// 分隔符
			String boundary = String.valueOf(System.currentTimeMillis());

			// 设置Content-Type
			conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
			// 拼接头部信息
			StringBuilder header = new StringBuilder();
			header.append("--").append(boundary).append("\r\n");
			header.append("Content-Disposition: form-data; name=\"file\"; filename=\"");
			File file = new File(filepath);
			header.append(file.getName()).append("\"");
			header.append(";filelength=\"").append(file.length()).append("\"");
			header.append("\r\n");
			header.append("Content-Type: application/octet-stream\r\n\r\n");

			// 写入头部信息
			pw = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(), charset));
			pw.write(header.toString());
			pw.flush();

			// 写入附件
			BufferedOutputStream bufout = new BufferedOutputStream(conn.getOutputStream());
			bufinput = new BufferedInputStream(new FileInputStream(file));// 本地文件流
			byte[] bytebuf = new byte[1024];
			int length = 0;
			while ((length = bufinput.read(bytebuf)) != -1) {
				bufout.write(bytebuf, 0, length);
			}
			bufout.flush();

			// 写入结束符
			pw.write("\r\n--" + boundary + "--\r\n");
			pw.flush();

			// 从对方服务器读取数据
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), charset));
			StringBuilder strbuf = new StringBuilder();
			String line = null;
			while ((line = rd.readLine()) != null) {
				strbuf.append(line).append("\r\n");
			}
			return strbuf.toString();
		} finally {
			if (bufinput != null) {
				try {
					bufinput.close();
				} catch (Throwable e) {

				}
			}
			if (pw != null) {
				try {
					pw.close();
				} catch (Throwable e) {

				}
			}
			if (rd != null) {
				try {
					rd.close();
				} catch (Throwable e) {

				}
			}
			if (conn != null) {
				try {
					conn.disconnect();
				} catch (Throwable e) {

				}
			}
		}
	}

	public static void main(String[] args) {
		try {
			String result = HttpUtil.sendHttpRequest("http://127.0.0.1/ajax/RestUserList", "GET", null, "UTF-8");
			System.out.println(result);
			/*
			 * String result=HttpUtil.upload("http://127.0.0.1/servlet1/user1",
			 * "d:/1.jpg","UTF-8"); System.out.println(result);
			 */
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
