package gby.appium.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class CommandPromptUtil {
	
    String OSType =null;
    public CommandPromptUtil()
    {
        OSType = System.getProperty("os.name");
    }

    public String runCommandThruProcess(String command)
             {
        BufferedReader br = null;
		try {
			br = getBufferedReader(command);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        String line;
        String allLine = "";
        try {
			while ((line = br.readLine()) != null) {
			    allLine = allLine + "" + line + "\n";
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        LoggerUtil.debug("命令结果：" + allLine );
        return allLine;
    }

    public List<String> runCommand(String command)
            {
        BufferedReader br = null;
		try {
			br = getBufferedReader(command);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        String line;
        List<String> allLine = new ArrayList<>();
        try {
			while ((line = br.readLine()) != null) {
//        		allLine.add(line.replaceAll("[\\[\\](){}]","_").split("_")[1]);
				allLine.add(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        LoggerUtil.debug("命令结果：" + allLine );
        return allLine;
    }

    private BufferedReader getBufferedReader(String command) throws IOException {
        List<String> commands = new ArrayList<>();
        if(OSType.contains("Windows"))
        {
            commands.add("cmd");
            commands.add("/c");
        }
        else
        {
            commands.add("/bin/sh");
            commands.add("-c");
        }
        commands.add(command);
        ProcessBuilder builder = new ProcessBuilder(commands);
        final Process process = builder.start();
        InputStream is = process.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        LoggerUtil.debug("执行命令：" + commands.toString());
        return new BufferedReader(isr);
    }

    public Process execForProcessToExecute(String cmd) throws IOException {
        Process pr = null;
        List<String> commands = new ArrayList<>();

        if(OSType.contains("Windows"))
        {
            commands.add("cmd");
            commands.add("/c");
        }
        else {
            commands.add("/bin/sh");
            commands.add("-c");
        }
        commands.add(cmd);
        ProcessBuilder builder = new ProcessBuilder(commands);
        pr = builder.start();
        return pr;
    }

//    public String runProcessCommandToGetDeviceID(String command)
//            throws InterruptedException, IOException {
//        BufferedReader br = getBufferedReader(command);
//        String line;
//        String allLine = "";
//        while ((line = br.readLine()) != null) {
//            allLine = allLine.trim() + "" + line.trim() + "\n";
//        }
//        return allLine.trim();
//    }
    
    public List<String> adbDevices() {
    	
        List<String> devicesList = new ArrayList<String>();
    	List<String> devicesCom = new ArrayList<String>();
		devicesCom = runCommand("adb devices");
    	for (String str : devicesCom) {
    		if (Pattern.compile("\\w+").matcher(str).find()) {
        		if (str.contains("List")) {
        			continue;	
        		} else if (str.contains("device")) {
        			devicesList.add(str.split("device")[0].replaceAll("\\s", ""));
        		} else {
        			System.out.println("设备未确认秘钥连接或其他错误：" + str);
        		}
    		}
    		}
    	System.out.println(devicesList.toString());
		return devicesList;
    }

// 杀掉adb进程，adb进程性能有限，放在多个进程出现意外问题。
//	cmd.runCommandThruProcess("taskkill /F /IM adb.exe");

//	for (String str : devicesList) {
//
//		// 去除空白字符，去除device字符，获取udid
////		if(Pattern.compile("\\\\d{1,3}\\\\.\\\\d{1,3}\\\\.\\\\d{1,3}\\\\.\\\\d{1,3}").matcher(str).find()) {
////			continue;
////		}
//		String udid = str.replaceAll("\\s*", "").split("device")[0];
//		System.out.println("udid: "+ udid);
//		String strtcp = runCommandThruProcess("adb -s " + udid + " tcpip 5555");
//		System.out.println("strtcp: " +strtcp);
//		Thread.sleep(2000);
//
//		String result = runCommand("adb connect " + getDeviceIP(udid) + ":5555").toString();
//		System.out.println(result);
//	}

}
