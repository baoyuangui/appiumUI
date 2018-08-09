package gby.testng.testcase;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.logging.log4j.ThreadContext;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import gby.appium.ui.AppiumInit;
import gby.appium.ui.InitAppStart;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class InitDriverCase {

	Logger logger = LoggerFactory.getLogger(AppiumInit.class);
	InitAppStart sClass;
	public static AndroidDriver<AndroidElement> driver;

	@Parameters({ "deviceName" })
	@Test
	public void f(String deviceName) throws MalformedURLException {
		Thread.currentThread().setName(deviceName);
		ThreadContext.put("ThreadName", Thread.currentThread().getName());
		logger.debug("deviceName: " + deviceName);
		sClass = new InitAppStart(deviceName);
		driver = sClass.driver;

	}
//	@AfterSuite
//	public void setDown() {
//		sClass.dc.setDownAllCommand();
//	}
//	

}
