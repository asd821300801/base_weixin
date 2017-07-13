package com.lingdu.weixin.message;

public class WeixinImageResponse extends WeixinResponse{
	public WeixinImageResponse(){
		this.setMsgType("image");
	}
	private Image Image=new Image();
	
	public void addMediaId(String mediaId){
		this.Image.setMediaId(mediaId);
	}
	public Image getImage() {
		return Image;
	}
	public void setImage(Image image) {
		Image = image;
	}
	
	
}
class Image {
	private String MediaId;

	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}
}
