package gby.appium.deviceM;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import gby.appium.utils.CommandPromptUtil;
import gby.appium.utils.JsonParser;
import gby.appium.utils.LoggerUtil;


import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DevicesManager {

	public CommandPromptUtil cmd;

//	public String path;
	public JsonParser jParser;
	
//    private static Map<String, Process> processUDIDs = new HashMap<>();
//    private ArrayList<Process> prcsList = null;
//    Process process;
	public DevicesManager() {
		cmd = new CommandPromptUtil();
//		adbDevices = new JSONObject();
		jParser = new JsonParser("/src/main/resources/devices.json");
	}

	public DevicesManager(String jsonfilePath) {
		cmd = new CommandPromptUtil();
//		adbDevices = new JSONObject();
		jParser = new JsonParser(jsonfilePath);
	}

	/**
	 * This method start adb server
	 */
	public void startADB() throws Exception {
		String output = cmd.runCommandThruProcess("adb start-server");
		String[] lines = output.split("\n");
		if (lines[0].contains("internal or external command")) {
			System.out.println("Please set ANDROID_HOME in your system variables");
		}
	}

	public JSONObject getDeviceInfo(String udid) {
		
		JSONObject adbDevice = new JSONObject();
		String model = cmd.runCommandThruProcess("adb -s " + udid + " shell getprop ro.product.model")
				.replaceAll("\\s+", "");
		String brand = cmd.runCommandThruProcess("adb -s " + udid + " shell getprop ro.product.brand")
				.replaceAll("\\s+", "");
		String osVersion = cmd.runCommandThruProcess("adb -s " + udid + " shell getprop ro.build.version.release")
				.replaceAll("\\s+", "");

		String deviceName = model.contains(brand) ? model : brand + "_" + model;
		
		//根据设备名称字符值计算对应appium server的端口
		int apmsrv_port = 4000; 
		for(char c : deviceName.toCharArray()) {
			apmsrv_port = apmsrv_port+ c;
		}
		int apmsrv_bp = apmsrv_port + 1 ; 

		String getScreenResolution = cmd.runCommandThruProcess("adb -s " + udid + " shell wm size").split(":")[1]
				.replace("\n", "").replace("x", "_");

		String ip = this.getDeviceIP(udid);

		adbDevice.put("name", deviceName);
		adbDevice.put("os_ver", osVersion);
		adbDevice.put("udid", udid);
		adbDevice.put("screenSize", getScreenResolution);
		adbDevice.put("os", "android");
		adbDevice.put("ip", ip);
		adbDevice.put("udid2", ip+":5555");
		adbDevice.put("apmsrv_port", apmsrv_port);
		adbDevice.put("apmsrv_bp", apmsrv_bp);

		return adbDevice;
	}

	public Map<String, Device> fromJsonfileGetDevices() {
		Map<String, Device> devices = new HashMap<>();
//
//		JsonParser jsonParser = new JsonParser(path);
		JSONObject devicesJson = jParser.getObjectFromJSON();

		Set<String> keySet = devicesJson.keySet();
		for (String name : keySet) {
			Device device = JSON.toJavaObject(devicesJson.getJSONObject(name), Device.class);
			devices.put(name, device);
		}
		return devices;

	}

	
	/*
	 * 
	 * 根据adbDevices()返回的设备list
	 * 更新设备Json文件
	 * 
	 * 同样由于远程jenkins的使用，该方法暂时注释掉
	 * */
	public  void flashDeviceInfo() {
		
		List<String> adbUdids = adbDevices();
		JSONObject adbDevices = new JSONObject();
		JSONObject adbDevice = null;
		
		//根据udid列表循环获取设备信息json，插入devicesjson中。
		if(adbUdids.size() != 0) {
			for (String udid : adbUdids) {
				adbDevice = getDeviceInfo(udid);
				adbDevices.put(adbDevice.getString("name"), adbDevice);
				LoggerUtil.debug("更新设备信息："+adbDevice.getString("name"));
			}
			
			//将新json信息覆盖至老的设备信息json中,并保存至json文件中
			JSONObject oldJson = jParser.getObjectFromJSON();	
			oldJson.putAll(adbDevices);
			String devStr = oldJson.toString();		
			jParser.writeFile(devStr);
		}else {
			LoggerUtil.error("没有插入电脑的设备");
		}

	}

//    public void stopAllProcess() {
//    	
//    }

	public String getDeviceIP(String udid) {
		String ip = "";
		String comRst = "";
		comRst = cmd.runCommandThruProcess("adb -s " + udid + " shell ip addr show wlan0");

		// ip正则搜索
		Pattern pattern = Pattern.compile("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}");
		Matcher matcher = pattern.matcher(comRst);

		// 开始寻找第一个匹配，第一个ip就是设备ip
		matcher.find();
		ip = matcher.group();
		System.out.println("ip: " + ip);

		return ip;

	}
	
	
	/*
	 * 
	 * 过滤掉以ip形式连接的设备
	 * 获取usb连接的设备id List
	 * 
	 * 由于远程jenkis服务器原因，该方法已没有用
	 * */
    public List<String> adbDevices() {
    	
        List<String> devicesList = new ArrayList<String>();
    	List<String> devicesCom = new ArrayList<String>();
		devicesCom = cmd.runCommand("adb devices");
    	for (String str : devicesCom) {
    		if (!Pattern.compile("\\.").matcher(str).find()) {
        		if (str.contains("List")|| str.equals("")) {
        			continue;	
        		} else if (str.contains("device")) {
        			devicesList.add(str.split("device")[0].replaceAll("\\s", ""));
        		} else {
        			LoggerUtil.error("设备未确认秘钥连接或其他错误：" + str);
        		}
    		}
    		}
    	LoggerUtil.debug(devicesList.toString());
		return devicesList;
    }


}
