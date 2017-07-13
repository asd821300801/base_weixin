package com.lingdu.weixin.api;

public class QRTicketRequest {
	// 二维码的有效时间
	private long expire_seconds = 2592000;
	private String action_name = "QR_SCENE";
	private ActionInfo action_info = new ActionInfo();

	public void addSceneId(String sceneid) {
		this.action_info.addSceneId(sceneid);
	}

	public long getExpire_seconds() {
		return expire_seconds;
	}

	public void setExpire_seconds(long expire_seconds) {
		this.expire_seconds = expire_seconds;
	}

	public String getAction_name() {
		return action_name;
	}

	public void setAction_name(String action_name) {
		this.action_name = action_name;
	}

	public ActionInfo getAction_info() {
		return action_info;
	}

	public void setAction_info(ActionInfo action_info) {
		this.action_info = action_info;
	}

}

class ActionInfo {
	private Scene scene = new Scene();

	public void addSceneId(String sceneid) {
		this.scene.setScene_id(sceneid);
	}

	public Scene getScene() {
		return scene;
	}

	public void setScene(Scene scene) {
		this.scene = scene;
	}
}

class Scene {
	private String scene_id;

	public String getScene_id() {
		return scene_id;
	}

	public void setScene_id(String scene_id) {
		this.scene_id = scene_id;
	}
}
