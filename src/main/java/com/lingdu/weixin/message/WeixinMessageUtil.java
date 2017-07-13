package com.lingdu.weixin.message;

import java.io.InputStream;
import java.io.InputStreamReader;

import com.lingdu.common.util.ConfigUtil;

import com.thoughtworks.xstream.XStream;

/**
 * 微信消息转换工具类
 * @author LingDu
 */
public class WeixinMessageUtil {
	public static WeixinRequest toWeixinRequest(InputStream input) throws Throwable {
		XStream xs = new XStream();
		xs.alias("xml", WeixinRequest.class);
		WeixinRequest request = (WeixinRequest) xs.fromXML(new InputStreamReader(input, ConfigUtil.get("charset")));
		return request;
	}

	public static String toWeixinResponseXml(WeixinTextResponse response) {
		XStream xs = new XStream();
		xs.alias("xml", WeixinTextResponse.class);
		return xs.toXML(response);
	}

	public static String toWeixinResponseXml(WeixinImageResponse response) {
		XStream xs = new XStream();
		xs.alias("xml", WeixinImageResponse.class);
		return xs.toXML(response);
	}
}
