package com.lingdu.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class ConfigUtil {
	private static Properties pro;
	
	static{
		pro=new Properties();
		//默认从类的所在包目录开始查找资源文件
		//如果要classpath的根目录开始找，必须加上/
		/*InputStream input=PropertiesUtil.class.
				getResourceAsStream("/config.properties");*/
		//默认从classspath的根目录开始查找资源文件
		InputStream input=ConfigUtil.class
				.getClassLoader()
				.getResourceAsStream("config.properties");

		
		try {
			pro.load(new InputStreamReader(input,
					"UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(input!=null){
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	public static String get(String key){
		//return pro.getProperty(key);
		return pro.getProperty(key);
	}
	
	public static int getInt(String key){
		return Integer.parseInt(pro.getProperty(key));
	}
	
	public static void main(String[] args) {
		System.out.println(ConfigUtil.get("token"));

	}
}
