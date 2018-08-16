package gby.testng.testcase;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.logging.log4j.ThreadContext;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import gby.appium.deviceM.SetUpDriver;
import gby.appium.utils.LoggerUtil;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;


public class InitDriverCase {

	SetUpDriver sClass;
	public static AndroidDriver<AndroidElement> driver;


	
	@Parameters({ "deviceName" })
	@Test
	public void f(String deviceName) throws MalformedURLException {
		Thread.currentThread().setName(deviceName);
		ThreadContext.put("ThreadName", Thread.currentThread().getName());
		LoggerUtil.debug("deviceName: " + deviceName);
		sClass = new SetUpDriver(deviceName);
		driver = sClass.getDriver();


/*		if(driver==null) {
			Thread.currentThread().destroy();
			LoggerUtil.error("driver初始化失败");
		}*/
	}
	

	@AfterSuite(alwaysRun=true)
	public void setDown() {
		
		sClass.dc.setDownAllCommand();
	}
	

}
