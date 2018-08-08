package test.testng.parralel;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gby.appium.utils.CommandPromptUtil;

public class RunCommand {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
//		String comRst  = "10: wlan0: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc mq"
//				+ " state UP qlen 1000	inet 192.168.20.138/24 brd 192.168.20.255 scope"
//				+ " global wlan0	valid_lft forever preferred_lft forever";
//		Pattern pattern = Pattern.compile("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}");
//		Matcher matcher = pattern.matcher(comRst);
//		matcher.find();
//		String ip = matcher.group(0);
//		System.out.println("ip: " + ip);
//		String device = "ABC123DC3def      device";
//		System.out.println(Pattern.compile("\\w+").matcher(device).find());
		CommandPromptUtil cmd = new CommandPromptUtil();
		cmd.runCommandThruProcess("adb devices");
	}

}
