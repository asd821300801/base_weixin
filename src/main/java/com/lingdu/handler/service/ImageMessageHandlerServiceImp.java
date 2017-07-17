package com.lingdu.handler.service;

import org.springframework.stereotype.Service;

import com.lingdu.common.util.MessageUtil;
import com.lingdu.weixin.message.WeixinMessageUtil;
import com.lingdu.weixin.message.WeixinRequest;
import com.lingdu.weixin.message.WeixinTextResponse;

@Service
public class ImageMessageHandlerServiceImp implements MessageHandlerService {

	@Override
	public boolean match(WeixinRequest request) throws Throwable {
		// 判断消息类型是否为：text类型
		if (MessageUtil.REQ_MESSAGE_TYPE_IMAGE.equalsIgnoreCase(request.getMsgType())) {
			System.out.println("消息类型：" + request.getMsgType());
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String processMsg(WeixinRequest request) throws Throwable {
		System.out.println("图片URL：" + request.getPicUrl());
		WeixinTextResponse response = new WeixinTextResponse();
		response.setFromUserName(request.getToUserName());
		response.setToUserName(request.getFromUserName());
		response.setContent("图片消息");
		return WeixinMessageUtil.toWeixinResponseXml(response);
	}

}
