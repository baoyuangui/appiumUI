package gby.appium.utils;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.thoughtworks.utils.CommandPromptUtil;

import gby.appium.ui.Device;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class AndroidManager  {

    private CommandPromptUtil cmd;
    private JSONObject adbDevices;
//    private static Map<String, Process> processUDIDs = new HashMap<>();
//    private ArrayList<Process> prcsList = null;
//    Process process;
    public AndroidManager() {
        cmd = new CommandPromptUtil();
        adbDevices = new JSONObject();
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

    public JSONObject getDeviceInfo(String udid) throws InterruptedException, IOException {
    	JSONObject adbDevice = new JSONObject() ;
    	String model =
                cmd.runCommandThruProcess("adb -s " + udid
                        + " shell getprop ro.product.model");
        String brand =
                cmd.runCommandThruProcess("adb -s " + udid
                        + " shell getprop ro.product.brand")
                        .replaceAll("\\s+", "");
        String osVersion = cmd.runCommandThruProcess(
                "adb -s " + udid + " shell getprop ro.build.version.release")
                .replaceAll("\\s+", "");
        	
        String deviceName =  model.contains(brand) ? model : brand + " " + model;
       
/*        String apiLevel =
                cmd.runCommandThruProcess("adb -s " + deviceID
                        + " shell getprop ro.build.version.sdk")
                        .replaceAll("\n", "");*/
        String getScreenResolution = cmd.runCommandThruProcess("adb -s " + udid
                + " shell wm size").split(":")[1].replace("\n", "").replace("x", "_");
        
        String ip = this.getDeviceIP(udid); 
        
        int size = this.fromJsonfileGetDevices("jsonPath").size();
        
        adbDevice.put("name", deviceName);
        adbDevice.put("os_ver", osVersion);
//        adbDevices.put("apiLevel", apiLevel);
        adbDevice.put("udid", udid);
        adbDevice.put("screenSize", getScreenResolution);
        adbDevice.put("os", "android");
        adbDevice.put("ip", ip);
        adbDevice.put("apmsrv_port", value)
//        adbDevice
        this.adbDevices.put("deviceName",adbDevice);
        return adbDevices;
    }

    public Map<String, Device> fromJsonfileGetDevices(String jsonPath){
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
    
    
    public void saveDeviceInfo(JSONObject devices, String path){
    	JsonParser jParser = new JsonParser(path);
    	JSONObject oldMap =  jParser.getObjectFromJSON();;
    	devices.putAll(oldMap);
    	
    	String devStr = devices.toString();
    	jParser.writeFile(devStr);
    	
    }

//    public void stopAllProcess() {
//    	
//    }
    
    public  String getDeviceIP(String udid) {
		String ip = "";
		String comRst = "";
//		CommandPromptUtil cmd = new CommandPromptUtil();
		try {
			comRst = cmd.runCommandThruProcess("adb -s " + udid + " shell ip addr show wlan0");

			// ip正则搜索
			Pattern pattern = Pattern.compile("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}");
			Matcher matcher = pattern.matcher(comRst);
			
			//开始寻找第一个匹配，第一个ip就是设备ip
			matcher.find();
			ip = matcher.group();
			System.out.println("ip: " + ip);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ip;

	}


}
