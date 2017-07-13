package com.lingdu.weixin.api;

public class CustomMessage {
	public static final String TYPE_TEXT="text";
	public static final String TYPE_IMAGE="image";
	
	private String touser;
	private String msgtype;
	public String getTouser() {
		return touser;
	}
	public void setTouser(String touser) {
		this.touser = touser;
	}
	public String getMsgtype() {
		return msgtype;
	}
	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}

}
