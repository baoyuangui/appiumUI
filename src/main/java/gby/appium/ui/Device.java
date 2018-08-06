package gby.appium.ui;



import gby.appium.utils.JsonParser;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;


public class Device {

	private String name;
    private String os;
    private String os_ver;
    private String udid;
    private String ip;
    private String apmsrv_ip;
    private String apmsrv_port;
    private String bp;
    private String app_path;
    private String app_pack ;
    private String app_activ;
    private String screenSize;

    public Device(JSONObject deviceJson) {
        this.udid = deviceJson.getString("udid");
        this.name = deviceJson.getString("name");
        this.ip = deviceJson.getString("ip");
        this.os = deviceJson.getString("os");
        this.apmsrv_ip = deviceJson.getString("apmsrv_ip");
        this.os_ver = deviceJson.getString("os_ver");
        this.bp = deviceJson.getString("bp");
        this.screenSize = deviceJson.getString("screenSize");
        this.app_path = deviceJson.getString("app_path");
        this.app_pack = deviceJson.getString("app_pack");
        this.app_activ = deviceJson.getString("app_activ");

    }

    public Device() {
    }

    
/*
 * 
 * 
 * */
    public Map<String, Device> getDevices(String jsonPath){
    	Map<String, Device> devices = new HashMap<>();
    	JsonParser jsonParser = new JsonParser(jsonPath);
    	JSONObject devicesJson = jsonParser.getObjectFromJSON();
    	Set<String> keySet = devicesJson.keySet();
    	for(String name : keySet) {
    		Device device = JSON.toJavaObject(devicesJson.getJSONObject(name), Device.class);
    		devices.put(name, device);    		
    	}
		return devices;
    	
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


	public String getApmsrv_ip() {
		return apmsrv_ip;
	}


	public void setApmsrv_ip(String apmsrv_ip) {
		this.apmsrv_ip = apmsrv_ip;
	}


	public String getApmsrv_port() {
		return apmsrv_port;
	}


	public void setApmsrv_port(String apmsrv_port) {
		this.apmsrv_port = apmsrv_port;
	}


	public String getBp() {
		return bp;
	}


	public void setBp(String bp) {
		this.bp = bp;
	}


	public String getApp_path() {
		return app_path;
	}


	public void setApp_path(String app_path) {
		this.app_path = app_path;
	}


	public String getApp_pack() {
		return app_pack;
	}


	public void setApp_pack(String app_pack) {
		this.app_pack = app_pack;
	}


	public String getApp_activ() {
		return app_activ;
	}


	public void setApp_activ(String app_activ) {
		this.app_activ = app_activ;
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

}
