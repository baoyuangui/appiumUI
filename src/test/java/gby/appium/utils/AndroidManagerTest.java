package gby.appium.utils;

import java.util.List;

import bsh.Capabilities;
import bsh.commands.dir;
import gby.appium.ui.DevicesManager;
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
//		DevicesConnect dConnect = new DevicesConnect();
//		if(dConnect.adbConnect("vivoX9")== null) {
//			LoggerUtil.error("设备信息无法获取");
//		}else{
//			dConnect.setUpAppiumServer();
//		}
//		CommandPromptUtil cmd = new CommandPromptUtil();
//		String cmdStr = cmd.runCommandThruProcess("appium -U 192.168.20.200:5555"
//						+ " -a 127.0.0.1 -p 4725 -bp 4726");
//		String cmdStr2 = cmd.runCommandThruProcess("adb connect 192.168.20.218:5555");
////		System.out.println(cmdStr2);
////		
		DevicesConnect dc = new DevicesConnect("LenovoK32c36");
		dc.adbConnect();

////		dc.setUpAppiumServer();
		
}}
