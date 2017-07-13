package com.lingdu.weixin.api;

public class TextCustomMessage extends CustomMessage {
	public TextCustomMessage() {
		this.setMsgtype(TYPE_TEXT);
	}

	private Text text = new Text();

	public void setContent(String content) {
		this.text.setContent(content);
	}

	public Text getText() {
		return text;
	}

	public void setText(Text text) {
		this.text = text;
	}
}

class Text {
	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
