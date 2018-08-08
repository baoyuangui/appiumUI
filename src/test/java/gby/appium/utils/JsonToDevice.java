package gby.appium.utils;

import java.util.HashMap;
import java.util.Map;

import gby.appium.ui.Device;

public class JsonToDevice {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		Device deviceOb = new Device();
//		deviceOb.getDevices("\\src\\main\\resources\\devices.json");
//		System.out.println(deviceOb.getDevices("\\src\\main\\resources\\devices.json").get("vivo").getBp());
		Map<String, String> json = new HashMap<>();
		json.put("a", "a1");
		json.put("b", "b1");
		System.out.println(json.get("b"));
	}

}
