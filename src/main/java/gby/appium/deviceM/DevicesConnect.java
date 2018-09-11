package gby.appium.deviceM;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.SequenceInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

import org.apache.logging.log4j.ThreadContext;

import gby.appium.utils.LoggerUtil;

public class DevicesConnect extends DevicesManager {

	public Device device;
	private Process prs;
	public ReentrantLock serverLock = new ReentrantLock();

	public DevicesConnect(String deviceName) {
		super();
		List<String> devs = new ArrayList<String>();
		for(String dev : fromJsonfileGetDevices().keySet()) {
			if(dev.toUpperCase().contains(deviceName.toUpperCase())) {
				devs.add(dev);
			}
		}
		if(devs.size() == 0) {
			device = null;	
		}else{
			if(devs.size() > 1)
				LoggerUtil.debug("输入的设备名称匹配有多个，仅使用第一个：" + devs.get(0));
			device = fromJsonfileGetDevices().get(devs.get(0));
		}
		

	}

	public DevicesConnect(String path, String deviceName) {
		super(path);
		device = fromJsonfileGetDevices().get(deviceName);
	}


	/*
	 * 
	 * 根据device对象是否为空，从而通过电脑刷新设备信息
	 * 并开启wifi调试模式，连接设备
	 * 
	 * 
	 * */
	public void adbConnect() {

		// 先判断json文件中是否存在该设备，不存在则从电脑中刷新设备信息
		if (device == null) {
			flashDeviceInfo();

			try {
				cmd.runCommandThruProcess("adb -s " + device.getUdid() + " tcpip 5555");
				Thread.sleep(1000);
				cmd.runCommandThruProcess("adb connect " + device.getUdid2());
			} catch (NullPointerException | InterruptedException e) {
				// TODO: handle exception
				LoggerUtil.error("无法连接设备，确认设备名称无误，尝试usb连接电脑再运行一次：" + e.getMessage());
//				Thread.currentThread().destroy();
			}

		} else {

			// json中存在设备，尝试连接
			String ipConStr = cmd.runCommandThruProcess("adb connect " + device.getUdid2());

			// 连接不成功,可能ip改变，刷新设备信息，再次连接
			if (ipConStr.contains("cannot") &&
					adbDevices().contains(device.getUdid())) {

				flashDeviceInfo();
				try {
					device = fromJsonfileGetDevices().get(device.getName());
					cmd.runCommandThruProcess("adb -s " + device.getUdid() + " tcpip 5555");

					Thread.sleep(1000);
				} catch (NullPointerException | InterruptedException e) {
					// TODO Auto-generated catch block
					LoggerUtil.error("无法连接设备，请尝试usb连接电脑再运行一次：" + e.getMessage());
//					Thread.currentThread().destroy();
				}

				String str = cmd.runCommandThruProcess("adb connect " + device.getUdid2());
				
				// 刷新ip后依旧无法连接，提示报错
				if (str.contains("cannot"))
					LoggerUtil.error("无法连接设备：" + device.getName() + ",首次使用或设备重启后，请开启调试模式并连接电脑再试");
//					Thread.currentThread().destroy();
			}
		}
	}
	public void startServer() {

		serverLock.lock();
		ThreadContext.put("ThreadName", Thread.currentThread().getName());

		try {
			prs = cmd.runCmdToProcess("appium -a 127.0.0.1 -p " + device.getApmsrv_port() + " -U " + device.getIp()
					+ ":5555" + " -bp " + device.getApmsrv_bp() );//
			InputStream inputStream = prs.getInputStream();
			InputStream errorStream = prs.getErrorStream();
			SequenceInputStream sis = new SequenceInputStream(inputStream, errorStream);
			BufferedReader reader = new BufferedReader(new InputStreamReader(sis, "UTF-8"));
			String line;
			while ((line = reader.readLine()) != null) {
				LoggerUtil.debug(line);
				if (line.contains("listener started")) {
					LoggerUtil.info("appiumServer已启动");
					serverLock.unlock();
				}
				if (line.contains("Could not start")) {
					LoggerUtil.error("appiumServer无法启动，可能是已存在");
					serverLock.unlock();
				}
			}
			prs.waitFor();
////				Thread.sleep(2000);
////				LoggerUtil.debug("Stop appium server");
//			inputStream.close();
//			reader.close();
//			prs.destroy();
//			LoggerUtil.debug(prs.toString()+" destroied");
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			LoggerUtil.error("appiumServer运行出错", e);
		}

	}

	public void stopServer() {

//		if (prs != null) {
//			System.out.println(prs);
//			prs.destroy();

		/*
		 * long pid = -1; Field field = null; try { field =
		 * prs.getClass().getDeclaredField("handle"); field.setAccessible(true); pid =
		 * Kernel32.INSTANCE.GetProcessId((Long) field.get(prs));
		 * cmd.runCommandThruProcess("taskkill /F /PID "+pid); LoggerUtil.debug(pid+"");
		 * } catch (Exception ex) { ex.printStackTrace(); }
		 */
		cmd.runCommandThruProcess("taskkill /F /IM node.exe");

//		}
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
