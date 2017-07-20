package com.lingdu.weixin.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lingdu.common.util.ConfigUtil;
import com.lingdu.common.util.HandlerUtil;
import com.lingdu.common.util.SignUtil;
import com.lingdu.common.util.SpringUtil;
import com.lingdu.handler.service.MessageHandlerService;
import com.lingdu.weixin.domain.Verify;
import com.lingdu.weixin.message.WeixinMessageUtil;
import com.lingdu.weixin.message.WeixinRequest;

@Controller
@RequestMapping("/weixin")
public class WeixinController {
	private Logger logger = LoggerFactory.getLogger(Logger.class);
	private List<String> msgIdList = new ArrayList<String>();
	// 拿到所有的实现类
	private List<MessageHandlerService> handlerList = SpringUtil.getBean(HandlerUtil.class).getHandlerList();

	/**
	 * 验证身份，成为开发者
	 * 
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/WeChatAPI", method = { RequestMethod.GET })
	public void verify(Verify verify, HttpServletResponse response) throws IOException {
		logger.info("verify() -----> 开始验证 \n");

		logger.info("接受参数：" + verify + "\n");

		if (verify.getSignature() != null) {
			if (SignUtil.validSign(verify.getSignature(), ConfigUtil.get("token"), verify.getTimestamp(),
					verify.getNonce())) {
				logger.info("验证成功：" + verify.getEchostr() + "\n");
				// return verify.getEchostr();
				response.getWriter().write(verify.getEchostr());
			} else {
				logger.info("验证失败 ！ \n");
				response.getWriter().write("验证失败 ！");
			}
		} else {
			logger.info("参数错误 ！ \n");
			response.getWriter().write("参数错误 ！");
		}

	}

	/**
	 * 从微信接受消息
	 * 
	 * @throws IOException
	 */
	@RequestMapping(value = "/WeChatAPI", method = RequestMethod.POST, produces = "application/xml;charset=UTF-8")
	public void ToReceiveMessagesFromWeChat(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		InputStream input = request.getInputStream();
		logger.info("接收到请求..... \n");
		String msgId = null;
		try {
			WeixinRequest weixinReq = WeixinMessageUtil.toWeixinRequest(input);
			// 消息去重处理
			if ("event".equals(weixinReq.getMsgType())) {
				msgId = weixinReq.getFromUserName() + weixinReq.getCreateTime();
			} else {
				msgId = weixinReq.getMsgId();
			}
			if (msgIdList.remove(msgId)) {// 消息重复
				logger.info("消息重复..... \n");
				response.getWriter().write("");
				return;
			}
			msgIdList.add(msgId);
			
			logger.info("请求的内容：" + weixinReq.getContent() + "\n");
			logger.info("eventkey：" + weixinReq.getEventKey() + "\n");
			
			for (MessageHandlerService msgHandler : handlerList) {
				if (msgHandler.match(weixinReq)) {
					String result = msgHandler.processMsg(weixinReq);
					logger.info("返回结果：\n" + result + "\n");
					response.getWriter().write(result);
					break;
				}
			}

		} catch (Throwable e) {
			e.printStackTrace();
			response.getWriter().write("");
		} finally {
			msgIdList.remove(msgId);
		}
	}

	@RequestMapping("/test")
	public String test() {
		for (MessageHandlerService msgHandler : handlerList) {
			System.out.println(msgHandler);
		}
		return "index";
	}
}
