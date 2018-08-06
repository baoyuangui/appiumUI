package gby.testng.testcase;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import gby.appium.ui.AppiumInit;
import gby.appium.ui.InitAppStart;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class InitDriverCase {
	
	Logger logger = LoggerFactory.getLogger(AppiumInit.class);
	InitAppStart sClass ;
	public static AndroidDriver<AndroidElement> driver;
	
	@Parameters({ "udid", "serverURL" })
	@Test
	public void f(String udid, String serverURL) throws MalformedURLException {
		Thread.currentThread().setName(udid);
		logger.debug("udid: " + udid);
		logger.debug("serverURL: " + serverURL);
		sClass = new InitAppStart();
		driver = sClass.initDriver(udid, serverURL);
		



		
//
//
//		// TODO Auto-generated method stub
//
//		DesiredCapabilities cap = new DesiredCapabilities();
//
//		// 设置启动参数
//		cap.setCapability("automationName", "Appium");
//		cap.setCapability("platformName", "Android");
//		cap.setCapability("platformName", "5.1");
//		cap.setCapability("deviceName", udid);
//		cap.setCapability("udid", udid);
//		cap.setCapability("appPackage", "com.loulifang.house");
//		cap.setCapability("appActivity", "com.loulifang.house.activities.TMainActivity");
//
//		// 初始化AndroidDriver
//		driver = new AndroidDriver<AndroidElement>(new URL("http://" + serverURL + "/wd/hub"), cap);
//		logger.info("AndroidDriver初始化完成，打开app成功");
	}

}
