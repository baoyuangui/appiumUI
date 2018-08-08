package gby.appium.utils;

import java.util.List;

import gby.appium.ui.AndroidManager;
import gby.appium.ui.Device;

public class AndroidManagerTest {

	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		AndroidManager am = new AndroidManager();
//		if(am.fromJsonfileGetDevices().get("LenovoK32c36")== null) {
//			List<String> adbUdids = am.cmd.adbDevices();
//			for(String udid :adbUdids ) {
//				am.getDeviceInfo(udid);
//			}
//			am.saveDeviceInfo(am.adbDevices);
//		}
//		System.out.println(am.fromJsonfileGetDevices().get("LenovoK32c36")
//				.getIp());
//		
		DevicesConnect dConnect = new DevicesConnect();
		if(dConnect.adbConnect("vivoX9")== null) {
			LoggerUtil.error("设备信息无法获取");
		}else{
			dConnect.setUpAppiumServer();
		}
		
}}
