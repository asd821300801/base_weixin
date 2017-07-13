package com.lingdu.weather.domain;

/**
 * 城市天气信息
 * @author LingDu
 */
public class CityWeather {
	//城市
		private String city;
		//时间段
		private String date;
		//温度
		private String temperature;
		//空气质量
		private String aqi;
		//风力状况
		private String txt;
		//风向
		private String dir;
		public String getCity() {
			return city;
		}
		public void setCity(String city) {
			this.city = city;
		}
		public String getDate() {
			return date;
		}
		public void setDate(String date) {
			this.date = date;
		}
		public String getTemperature() {
			return temperature;
		}
		public void setTemperature(String temperature) {
			this.temperature = temperature;
		}
		public String getAqi() {
			return aqi;
		}
		public void setAqi(String aqi) {
			this.aqi = aqi;
		}
		public String getTxt() {
			return txt;
		}
		public void setTxt(String txt) {
			this.txt = txt;
		}
		public String getDir() {
			return dir;
		}
		public void setDir(String dir) {
			this.dir = dir;
		}
		@Override
		public String toString() {
			return "/:,@-D \n你查询的地区是：" + city + "\n当前时间：" + date + "\n当前气温：" + temperature + "\n空气质量：" + aqi
					+ "\n天气状况：" + txt + "\n风向：" + dir ;
		}
		public CityWeather(String city, String date, String temperature, String aqi, String txt, String dir) {
			super();
			this.city = city;
			this.date = date;
			this.temperature = temperature;
			this.aqi = aqi;
			this.txt = txt;
			this.dir = dir;
		}
		public CityWeather() {
			super();
		}
		
		
		
}
