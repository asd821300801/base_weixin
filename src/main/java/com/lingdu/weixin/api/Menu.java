package com.lingdu.weixin.api;

import java.util.ArrayList;
import java.util.List;

public class Menu {
	private List<Button> button = new ArrayList<Button>();

	public void addButton(Button btn) {
		this.button.add(btn);
	}

	public List<Button> getButton() {
		return button;
	}

	public void setButton(List<Button> button) {
		this.button = button;
	}

	public static Menu getDefalutMenu() {
		Menu menu = new Menu();
		Button btn1 = new Button();
		btn1.setType(Button.TYPE_CLICK);
		btn1.setName("锐哥");
		btn1.setKey("POSTER");

		Button btn2 = new Button();
		btn2.setName("菜单");

		Button btn2Sub1 = new Button();
		btn2Sub1.setName("搜索");
		btn2Sub1.setType(Button.TYPE_VIEW);
		btn2Sub1.setUrl("http://m.baidu.com");

		Button btn2Sub2 = new Button();
		btn2Sub2.setName("视频");
		btn2Sub2.setType(Button.TYPE_VIEW);
		btn2Sub2.setUrl("http://v.qq.com");

		btn2.addSubButton(btn2Sub1);
		btn2.addSubButton(btn2Sub2);

		menu.addButton(btn1);
		menu.addButton(btn2);

		return menu;

	}
}
