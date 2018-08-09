package gby.appium.utils;

import java.io.IOException;
import java.util.List;

import gby.appium.ui.DevicesManager;
import net.bytebuddy.implementation.bind.annotation.Super;
import gby.appium.ui.Device;

public class DevicesConnect extends DevicesManager {

	public Device device;

	public DevicesConnect(String deviceName) {
		super();
		device = fromJsonfileGetDevices().get(deviceName);
		
	}

	public DevicesConnect(String path, String deviceName) {
		super(path);
		device = fromJsonfileGetDevices().get(deviceName);
	}

	public void adbConnect() {

		// 先判断json文件中是否存在该设备，不存在则从电脑中刷新设备信息
		if (device == null) {
			flashDeviceInfo();

			try {
				cmd.runCommandThruProcess("adb -s " + device.getUdid() + " tcpip 5555");
				Thread.sleep(1000);
				cmd.runCommandThruProcess("adb connect " + device.getIp() + ":5555");
			} catch (NullPointerException  | InterruptedException e) {
				// TODO: handle exception
				LoggerUtil.error("无法连接设备，确认设备名称无误，尝试usb连接电脑再运行一次：" + e.getMessage());
			}

		} else {

			// json中存在设备，尝试连接
			String ipConStr = cmd.runCommandThruProcess("adb connect " + device.getIp() + ":5555");

			// 连接不成功,可能ip改变，刷新设备信息，再次连接
			if (ipConStr.contains("cannot")) {
				flashDeviceInfo();
				try {
					device = fromJsonfileGetDevices().get(device.getName());
					cmd.runCommandThruProcess("adb -s " + device.getUdid() + " tcpip 5555");

					Thread.sleep(1000);
				} catch (NullPointerException | InterruptedException e) {
					// TODO Auto-generated catch block
					LoggerUtil.error("无法连接设备，请尝试usb连接电脑再运行一次：" + e.getMessage());
				}

				String str = cmd.runCommandThruProcess("adb connect " + device.getIp() + ":5555");

				// 刷新ip后依旧无法连接，提示报错
				if (str.contains("cannot"))
					LoggerUtil.error("无法连接设备：" + device.getName() + ",首次使用或设备重启后，请开启调试模式并连接电脑再试");
			}
		}
	}

	public void setUpAppiumServer() {

		try {
			cmd.runCmdServer("appium -a 127.0.0.1 -p " + device.getApmsrv_port() + " -U " + device.getIp()
					+ ":5555" + " -bp " + device.getApmsrv_bp());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setDownAllCommand() {
		cmd.runCommandThruProcess("taskkill /F /IM adb.exe");
		cmd.runCommandThruProcess("taskkill /F /IM node.exe");
	}

}
