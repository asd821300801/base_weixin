package com.lingdu.weixin.api;

import java.io.IOException;

import com.alibaba.fastjson.JSONObject;
import com.lingdu.common.util.ConfigUtil;
import com.lingdu.common.util.HttpUtil;

/**
 * 封装微信基本的功能 1、获取access_token 2、获取用户信息 3、创建菜单 4、上传媒体 5、获取二维码 6、发送客服消息
 * 
 * @author LingDu
 */
public class WeixinApi {
	private static final Object LOCK = new Object();
	private static final String POST = "POST";
	private static final String GET = "GET";
	private static final String APPID = ConfigUtil.get("appID");
	private static final String SECRET = ConfigUtil.get("appsecret");
	private static final String CHARSET = ConfigUtil.get("charset");
	private static final String GET_TOKEN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";
	private static AccessToken token;

	public static AccessToken getAccessToken() throws IOException {
		if (token != null && !token.isExpired()) {
			return token;
		}
		synchronized (LOCK) {
			if (token != null && !token.isExpired()) {
				return token;
			}
			String jsonResult = HttpUtil.sendHttpRequest(String.format(GET_TOKEN, APPID, SECRET), GET, null, CHARSET);
			System.out.println(jsonResult);
			token = JSONObject.parseObject(jsonResult, AccessToken.class);
			System.out.println(token.getAccess_token());
			return token;

		}
	}

	private static final String GET_USERINFO = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=%s&openid=%s&lang=zh_CN";

	public static UserInfo getUserInfo(String openid) throws IOException {
		AccessToken token = getAccessToken();
		String jsonResult = HttpUtil.sendHttpRequest(String.format(GET_USERINFO, token.getAccess_token(), openid), GET,
				null, CHARSET);
		System.out.println(jsonResult);
		UserInfo user = JSONObject.parseObject(jsonResult, UserInfo.class);
		System.out.println(user.getHeadimgurl());
		return user;
	}

	private static final String POST_MENU = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=%s";

	public static Result createMenu(Menu menu) throws IOException {
		AccessToken token = getAccessToken();
		String jsonRequet = JSONObject.toJSONString(menu);
		System.out.println(jsonRequet);
		String jsonResult = HttpUtil.sendHttpRequest(String.format(POST_MENU, token.getAccess_token()), POST,
				jsonRequet, CHARSET);
		System.out.println(jsonResult);
		return JSONObject.parseObject(jsonResult, Result.class);
	}

	private static final String POST_UPLOAD = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=%s&type=%s";

	public static Media upload(String filepath, String type) throws Exception {
		AccessToken token = getAccessToken();
		long start = System.currentTimeMillis();
		String jsonResult = HttpUtil.upload(String.format(POST_UPLOAD, token.getAccess_token(), type), filepath,
				CHARSET);
		System.out.println(jsonResult);
		System.out.println("上传耗时：" + (System.currentTimeMillis() - start) / 1000 + "秒");
		return JSONObject.parseObject(jsonResult, Media.class);
	}

	// 创建二维码
	private static final String POST_QRTICKET = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=%s";
	// 拿到二维码
	private static final String CREATE_QR = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=%s";

	public static QRTicket getQrTicket(QRTicketRequest request) throws Exception {
		AccessToken token = getAccessToken();
		String jsonRequet = JSONObject.toJSONString(request);
		System.out.println(jsonRequet);
		String jsonResult = HttpUtil.sendHttpRequest(String.format(POST_QRTICKET, token.getAccess_token()), POST,
				jsonRequet, CHARSET);
		System.out.println(jsonResult);
		return JSONObject.parseObject(jsonResult, QRTicket.class);

	}

	private static final String POST_CUSTOMMSG = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=%s";

	public static void sendCustomMessage(CustomMessage message) throws Exception {
		AccessToken token = getAccessToken();
		String jsonRequet = JSONObject.toJSONString(message);
		System.out.println(jsonRequet);
		String jsonResult = HttpUtil.sendHttpRequest(String.format(POST_CUSTOMMSG, token.getAccess_token()), POST,
				jsonRequet, CHARSET);
		System.out.println(jsonResult);
	}

	public static void main(String[] args) {
		try {
			
			  //向指定用户发送一段消息
			
			
			  TextCustomMessage message=new TextCustomMessage();
			  message.setContent("/::");
			  message.setTouser("obKXtwJtkhES-Xyi6mxyb4FT92MU");
			  sendCustomMessage(message);
			 

			
			 // 向指定用户发送一张图片
			  
			
			/*
			  ImageCustomMessage message = new ImageCustomMessage();
			  message.setTouser("obKXtwDX4kxu3U5gnSom65AVYqrA"); 
			  Media md =upload("E:\\Eclipse\\练习\\base_weixin\\src\\main\\resources\\image\\pikaqiu.png",Media.TYPE_IMAGE); 
			  message.setMediaId(md.getMedia_id());
			  sendCustomMessage(message);
			 */

			
			  //创建公众号二维码
			  
			/*
			  ImageCustomMessage message = new ImageCustomMessage();
			  message.setTouser("obKXtwF6GfvxQLMMQAcPpK4Ie5Xs");
			  QRTicketRequest request = new QRTicketRequest();
			  request.addSceneId("1"); 
			  QRTicket qr = getQrTicket(request);
			  System.out.println(qr.toString());
			  System.out.println(CREATE_QR + qr.getTicket());
			 */

			// WeixinApi.getAccessToken(); //获取access_tonken

			// 获取用户信息
			// UserInfo info = getUserInfo("obKXtwDX4kxu3U5gnSom65AVYqrA");
			// System.out.println(info.toString());

			// 创建菜单
			// createMenu(Menu.getDefalutMenu());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
