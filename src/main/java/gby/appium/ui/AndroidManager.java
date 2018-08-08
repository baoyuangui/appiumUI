package gby.appium.ui;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import gby.appium.utils.CommandPromptUtil;
import gby.appium.utils.JsonParser;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AndroidManager {

	public CommandPromptUtil cmd;
	public JSONObject adbDevices;
//	public String path;
	public JsonParser jParser;

//    private static Map<String, Process> processUDIDs = new HashMap<>();
//    private ArrayList<Process> prcsList = null;
//    Process process;
	public AndroidManager() {
		cmd = new CommandPromptUtil();
		adbDevices = new JSONObject();
		jParser = new JsonParser("/src/main/resources/devices.json");
	}

	public AndroidManager(String jsonfilePath) {
		cmd = new CommandPromptUtil();
		adbDevices = new JSONObject();
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
		
		JSONObject adbDevice =  jParser.getObjectFromJSON();
		String model = cmd.runCommandThruProcess("adb -s " + udid + " shell getprop ro.product.model")
				.replaceAll("\\s+", "");
		String brand = cmd.runCommandThruProcess("adb -s " + udid + " shell getprop ro.product.brand")
				.replaceAll("\\s+", "");
		String osVersion = cmd.runCommandThruProcess("adb -s " + udid + " shell getprop ro.build.version.release")
				.replaceAll("\\s+", "");

		String deviceName = model.contains(brand) ? model : brand + "_" + model;
		
		int apmsrv_port =4000; 
		for(char c : deviceName.toCharArray()) {
			apmsrv_port = apmsrv_port+ c;
		}
		int apmsrv_bp = apmsrv_port + 1 ; 
		/*
		 * String apiLevel = cmd.runCommandThruProcess("adb -s " + deviceID +
		 * " shell getprop ro.build.version.sdk") .replaceAll("\n", "");
		 */
		String getScreenResolution = cmd.runCommandThruProcess("adb -s " + udid + " shell wm size").split(":")[1]
				.replace("\n", "").replace("x", "_");

		String ip = this.getDeviceIP(udid);

		adbDevice.put("name", deviceName);
		adbDevice.put("os_ver", osVersion);
		adbDevice.put("udid", udid);
		adbDevice.put("screenSize", getScreenResolution);
		adbDevice.put("os", "android");
		adbDevice.put("ip", ip);
		adbDevice.put("apmsrv_port", apmsrv_port);
		adbDevice.put("bstPort", apmsrv_bp);
		
		this.adbDevices.put(deviceName, adbDevice);
		return adbDevices;
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

	public  void saveDeviceInfo(JSONObject devices) {
//		JsonParser jParser = new JsonParser(path);
		JSONObject oldMap = jParser.getObjectFromJSON();
		devices.putAll(oldMap);

		String devStr = devices.toString();
		jParser.writeFile(devStr);

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

}
