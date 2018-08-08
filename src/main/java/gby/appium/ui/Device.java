package gby.appium.ui;


import com.alibaba.fastjson.JSONObject;

public class Device {

	private String name;
	private String os;
	private String os_ver;
	private String udid;
	private String ip;
	private String screenSize;
	private String apmsrv_port;
	private String apmsrv_bp;

	public Device(JSONObject deviceJson) {

		this.name = deviceJson.getString("name");
		this.os = deviceJson.getString("os");
		this.os_ver = deviceJson.getString("os_ver");
		this.udid = deviceJson.getString("udid");
		this.ip = deviceJson.getString("ip");
		this.screenSize = deviceJson.getString("screenSize");

	}

	public Device() {
	}

	/*
	 * 
	 * 
	 * */

	public String getName() {
		return name;
	}

	public String getOs() {
		return os;
	}

	public String getUdid() {
		return udid;
	}

	public String getScreenSize() {
		return screenSize;
	}

	public String getOs_ver() {
		return os_ver;
	}

	public void setOs_ver(String os_ver) {
		this.os_ver = os_ver;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public void setUdid(String udid) {
		this.udid = udid;
	}

	public void setScreenSize(String screenSize) {
		this.screenSize = screenSize;
	}

	public String getApmsrv_port() {
		return apmsrv_port;
	}

	public void setApmsrv_port(String apmsrv_port) {
		this.apmsrv_port = apmsrv_port;
	}

	public String getApmsrv_bp() {
		return apmsrv_bp;
	}

	public void setApmsrv_bp(String apmsrv_bp) {
		this.apmsrv_bp = apmsrv_bp;
	}

}
