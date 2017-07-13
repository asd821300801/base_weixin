package com.lingdu.handler.service;

import com.lingdu.weixin.message.WeixinRequest;

/**
 * 消息处理器
 * @author LingDu
 *
 */
public interface MessageHandlerService {
	/**
	 * 判断消息是否该处理器进行处理
	 * @param request
	 * @return
	 * @throws Throwable
	 */
	public boolean match(WeixinRequest request) throws Throwable; 
	
	/**
	 * 进行消息处理
	 * @param request
	 * @return
	 * @throws Throwable
	 */
	public String processMsg(WeixinRequest request) throws Throwable; 
	
	
	
}
