package gby.appium.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;


public class AppiumServer {
	private HashMap<String, Process> processHashMap = new HashMap<>();

	
    private static AppiumServer appiumServerController = new AppiumServer();

    private AppiumServer() {
    }

    public static AppiumServer getInstance() {
        return appiumServerController;
    }
    

	public void startServer(ReentrantLock serverLock,DevicesConnect dc) {
			Process prs;
			serverLock.lock();
				try {
					prs = dc.cmd.runCmdToProcess("appium -a 127.0.0.1 -p " + dc.device.getApmsrv_port() + " -U "
							+ dc.device.getIp() + ":5555" + " -bp " + dc.device.getApmsrv_bp());
					InputStream inputStream = prs.getInputStream();
					BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
					String line;
					while ((line = reader.readLine()) != null) {
						LoggerUtil.debug(line);
						if(line.contains("listener started")) {
							serverLock.unlock();
						}
					}
					prs.waitFor();
//					LoggerUtil.debug("Stop appium server");
//					inputStream.close();
//					reader.close();
//					prs.destroy();
				} catch (IOException | InterruptedException e) {
					// TODO Auto-generated catch block
					LoggerUtil.error("appiumServer运行出错",e);
				}

	}

    public void stopServer(Process process) {

        if (process != null) {
            System.out.println(process);
            process.destroy();
        }
    }

    public void stopServer(String port) {
        Process process = processHashMap.get(port);
        stopServer(process);
        processHashMap.remove(port);
        LoggerUtil.debug("停止appiumServer");
    }
    
}
