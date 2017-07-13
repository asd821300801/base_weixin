package com.lingdu.weixin.api;

public class AccessToken {
	private String access_token;
	private int expires_in;
	private long createTime=System.currentTimeMillis();
	/**
	 * 
	 * @return true表示已经过期
	 */
	public boolean isExpired(){
		long curexpiresin=(System.currentTimeMillis()-createTime)/1000;
		if(curexpiresin>(expires_in-900)){
			return true;
		}else{
			return false;
		}
	}
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public int getExpires_in() {
		return expires_in;
	}
	public void setExpires_in(int expires_in) {
		this.expires_in = expires_in;
	}
}
