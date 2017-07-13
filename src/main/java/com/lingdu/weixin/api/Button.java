package com.lingdu.weixin.api;

import java.util.ArrayList;
import java.util.List;

public class Button {
	public static final String TYPE_CLICK="click";
	public static final String TYPE_VIEW="view";
	
	private String type;
	private String name;
	private String key;
	private String url;
	private List<Button> sub_button=new ArrayList<Button>();
	
	public void addSubButton(Button btn){
		this.sub_button.add(btn);
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public List<Button> getSub_button() {
		return sub_button;
	}
	public void setSub_button(List<Button> sub_button) {
		this.sub_button = sub_button;
	}
}
