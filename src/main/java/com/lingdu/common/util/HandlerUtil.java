package com.lingdu.common.util;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lingdu.handler.service.MessageHandlerService;

@Component
public class HandlerUtil {
	@Autowired
	private List<MessageHandlerService> handlerList;

	public List<MessageHandlerService> getHandlerList() {
		return handlerList;
	}

	public void setHandlerList(List<MessageHandlerService> handlerList) {
		this.handlerList = handlerList;
	}
}
