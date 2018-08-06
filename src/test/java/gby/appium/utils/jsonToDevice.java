package gby.appium.utils;

import gby.appium.ui.Device;

public class jsonToDevice {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Device deviceOb = new Device();
		deviceOb.getDevices("\\src\\main\\resources\\devices.json");
		System.out.println(deviceOb.getDevices("\\src\\main\\resources\\devices.json").get("vivo").getBp());
	}

}
