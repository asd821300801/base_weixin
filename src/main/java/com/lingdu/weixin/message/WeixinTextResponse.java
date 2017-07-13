package com.lingdu.weixin.message;

public class WeixinTextResponse extends WeixinResponse{
	public WeixinTextResponse(){
		this.setMsgType("text");
	}
	private String Content;
	
	public String getContent() {
		return Content;
	}
	public void setContent(String content) {
		Content = content;
	}
	
	
}
