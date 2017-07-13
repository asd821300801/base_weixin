package com.lingdu.weixin.api;

public class ImageCustomMessage extends CustomMessage {
	public ImageCustomMessage() {
		this.setMsgtype(TYPE_IMAGE);
	}

	private Image image = new Image();

	public void setMediaId(String mediaId) {
		this.image.setMedia_id(mediaId);
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

}

class Image {
	private String media_id;

	public String getMedia_id() {
		return media_id;
	}

	public void setMedia_id(String media_id) {
		this.media_id = media_id;
	}

}
