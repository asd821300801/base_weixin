package com.lingdu.handler.service;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lingdu.common.util.ConfigUtil;
import com.lingdu.common.util.MessageUtil;
import com.lingdu.weather.domain.CityWeather;
import com.lingdu.weixin.message.WeixinMessageUtil;
import com.lingdu.weixin.message.WeixinRequest;
import com.lingdu.weixin.message.WeixinTextResponse;

/**
 * 处理语音消息
 * 
 * @author LingDu
 */
@Service
public class VoiceMessageHandlerServiceImp implements MessageHandlerService {

	@Override
	public boolean match(WeixinRequest request) throws Throwable {
		System.out.println(request.getMsgType());
		// 判断消息类型是否为：voice类型 (音频类型)
		if (MessageUtil.REQ_MESSAGE_TYPE_VOICE.equalsIgnoreCase(request.getMsgType())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String processMsg(WeixinRequest request) throws Throwable {
		WeixinTextResponse response = new WeixinTextResponse();
		if (request.getRecognition() != null) {
			response.setFromUserName(request.getToUserName());
			response.setToUserName(request.getFromUserName());
			String msg = filterString(request.getRecognition()).trim();
			System.out.println(msg);
			String tianqi = ConfigUtil.get("tianqi");
			URL url = new URL(String.format(tianqi, msg.replace("。", "")));
			URLConnection conn = url.openConnection();
			InputStream in = conn.getInputStream();
			java.util.Scanner scanner = new java.util.Scanner(in);
			String str = "";
			while (scanner.hasNext()) {
				str = scanner.nextLine();
			}
			scanner.close();
			if (str.contains("HeWeather5") && str.contains("basic")) {
				response.setContent(message(str, msg));
			} else {
				response.setContent("没有查询到：'" + msg + "' 地区相关的信息！/:,@o");
			}

			System.out.println(str);
		}

		return WeixinMessageUtil.toWeixinResponseXml(response);
	}

	/**
	 * 清除所有的特殊字符
	 * 
	 * @param str
	 *            传入一个字符串
	 * @return 清除之后的结果
	 * @throws PatternSyntaxException
	 */
	public static String filterString(String str) throws PatternSyntaxException {
		// 清除掉所有特殊字符
		String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？_-]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}

	public static String message(String jsonContent, String msg) {
		CityWeather cityWeather = null;

		// 找到根路径
		JSONObject root = ((JSONObject) JSON.parse(jsonContent)).getJSONArray("HeWeather5").getJSONObject(0);
		if (!root.toString().contains("unknown city")) {
			try {

				cityWeather = new CityWeather();
				JSONObject basic = (JSONObject) root.get("basic");
				cityWeather.setCity(basic.getString("city"));
				System.out.println("当前城市：" + basic.getString("city"));

				JSONObject date = (JSONObject) root.getJSONArray("daily_forecast").get(0);
				// JSONObject date =
				// root.getJSONArray("hourly_forecast").getJSONObject(0);

				cityWeather.setDate(date.getString("date"));
				System.out.println("当前时间段：" + date.getString("date"));

				JSONObject tmp = root.getJSONArray("daily_forecast").getJSONObject(0).getJSONObject("tmp");
				cityWeather.setTemperature(tmp.getString("min") + "° ~ " + tmp.getString("max") + "°");
				System.out.println("当天气温：" + tmp.getString("min") + "° ~ " + tmp.getString("max") + "°");

				JSONObject city = (JSONObject) root.getJSONObject("aqi").get("city");
				cityWeather.setAqi(city.getString("aqi") + city.getString("qlty"));
				System.out.println("空气质量：" + city.getString("aqi") + city.getString("qlty"));

				JSONObject cond = root.getJSONObject("now").getJSONObject("cond");
				cityWeather.setTxt(cond.getString("txt"));
				System.out.println("风力状况：" + cond.getString("txt"));

				JSONObject wind = root.getJSONObject("now").getJSONObject("wind");
				cityWeather.setDir(wind.getString("dir"));
				System.out.println("风向：" + wind.getString("dir"));
			} catch (NullPointerException e) {
				cityWeather = new CityWeather(msg, "", "", "", "", "");
			}
		} else {
			cityWeather = new CityWeather(msg, "", "", "", "", "");
		}

		return cityWeather.toString();

	}
}
