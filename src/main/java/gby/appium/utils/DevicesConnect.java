package gby.appium.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.SequenceInputStream;
import java.lang.reflect.Field;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.spi.ThreadContextMap;

import gby.appium.ui.DevicesManager;
import gby.appium.ui.Device;

public class DevicesConnect extends DevicesManager {

	public Device device;
	private Process prs ;
	public ReentrantLock serverLock = new ReentrantLock();
 

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
			} catch (NullPointerException | InterruptedException e) {
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

	public void startServer() {
		
		serverLock.lock();
		ThreadContext.put("ThreadName", Thread.currentThread().getName());
		
			try {
				prs = cmd.runCmdToProcess("appium -a 127.0.0.1 -p " + device.getApmsrv_port() + " -U "
						+device.getIp() + ":5555" + " -bp " + device.getApmsrv_bp());
				InputStream inputStream = prs.getInputStream();
				InputStream errorStream = prs.getErrorStream();
				SequenceInputStream sis = new SequenceInputStream
		        		(inputStream, errorStream);
				BufferedReader reader = new BufferedReader(new InputStreamReader(sis,"gbk"));
				String line;
				while ((line = reader.readLine()) != null) {
					LoggerUtil.debug(line);
					if(line.contains("listener started")) {
						LoggerUtil.info("appiumServer已启动");
						serverLock.unlock();
					}
					if(line.contains("Could not start")) {
						LoggerUtil.error("appiumServer无法启动，可能是已存在");
						serverLock.unlock();
					}
				}
				prs.waitFor();
				Thread.sleep(2000);
				LoggerUtil.debug("Stop appium server");
				inputStream.close();
				reader.close();
				prs.destroy();
			} catch (IOException | InterruptedException e) {
				// TODO Auto-generated catch block
				LoggerUtil.error("appiumServer运行出错",e);
			}

}

	public void stopServer() {

		if (prs != null) {
			System.out.println(prs);
			prs.destroy();

/*			long pid = -1;
			Field field = null;
			try {
				field = prs.getClass().getDeclaredField("handle");
				field.setAccessible(true);
				pid = Kernel32.INSTANCE.GetProcessId((Long) field.get(prs));
				cmd.runCommandThruProcess("taskkill /F /PID "+pid);
				LoggerUtil.debug(pid+"");
			} catch (Exception ex) {
				ex.printStackTrace();
			}*/
			cmd.runCommandThruProcess("taskkill /F /IM node.exe");

			
		}
	}

//public void stopServer(String port) {
//    Process process = processHashMap.get(port);
//    stopServer(process);
//    processHashMap.remove(port);
//    LoggerUtil.debug("停止appiumServer");
//}

	
	public void setDownAllCommand() {
//		cmd.runCommandThruProcess("taskkill /F /IM node.exe");
		stopServer();	
	}

}
