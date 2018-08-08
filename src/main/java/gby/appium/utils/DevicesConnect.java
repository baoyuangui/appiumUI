package gby.appium.utils;

import java.util.List;

import gby.appium.ui.AndroidManager;
import gby.appium.ui.Device;

public class DevicesConnect extends AndroidManager{

	public Device device ;
	
	public DevicesConnect() {
		super();
	}
	
	public DevicesConnect(String path) {
		super(path);
	}
	
	public Device adbConnect(String deviceName) {
		
		if (fromJsonfileGetDevices().get(deviceName) == null) {
			List<String> adbUdids = cmd.adbDevices();
			for (String udid : adbUdids) {
				getDeviceInfo(udid);
			}
			saveDeviceInfo(adbDevices);
		}
		
		try {
			device = fromJsonfileGetDevices().get(deviceName);
			cmd.runCommandThruProcess("adb -s " + device.getUdid() + " tcpip 5555");
			Thread.sleep(1000);
			cmd.runCommandThruProcess("adb connect " + device.getIp() + ":5555");
			return device;
		} catch (NullPointerException e) {
			LoggerUtil.error("设备名称有误，devicesJson中无法找到该设备名称："+ deviceName+ ";请将设备开启调试模式，并与电脑连接");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return device;
	}
	
	public void setUpAppiumServer() {

		cmd.runCommandThruProcess("appium -a 127.0.0.1 -p "
				+ device.getApmsrv_port() + " -U " +device.getIp()+":5555"				
				+ " -bp " + device.getApmsrv_bp());
	}
	
	public void setDownAllCommand() {
		cmd.runCommandThruProcess("taskkill /F /IM adb.exe");
		cmd.runCommandThruProcess("taskkill /F /IM node.exe");
	}
}
