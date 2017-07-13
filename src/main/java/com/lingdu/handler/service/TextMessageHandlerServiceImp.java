package com.lingdu.handler.service;

import org.springframework.stereotype.Service;

import com.lingdu.common.util.MessageUtil;
import com.lingdu.weixin.message.WeixinMessageUtil;
import com.lingdu.weixin.message.WeixinRequest;
import com.lingdu.weixin.message.WeixinTextResponse;

/**
 * 处理文本消息
 * @author LingDu
 */
@Service
public class TextMessageHandlerServiceImp implements MessageHandlerService {

	@Override
	public boolean match(WeixinRequest request) throws Throwable {
		//判断消息类型是否为：text类型
		if(MessageUtil.REQ_MESSAGE_TYPE_TEXT.equalsIgnoreCase(request.getMsgType())){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public String processMsg(WeixinRequest request) throws Throwable {
		WeixinTextResponse response = new WeixinTextResponse();
		response.setFromUserName(request.getToUserName());
		response.setToUserName(request.getFromUserName());
		response.setContent("欢迎关注公众号！");
		return WeixinMessageUtil.toWeixinResponseXml(response);
	}

}
