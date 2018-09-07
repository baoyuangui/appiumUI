package gby.testng.testcase;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.logging.log4j.ThreadContext;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import gby.appium.deviceM.Device;
import gby.appium.deviceM.InitDrvier;
import gby.appium.page.BasePage;
import gby.appium.utils.AssertUtils;
import gby.appium.utils.LoggerUtil;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;


public class InitDriverCase {

	InitDrvier sClass;
	public static AndroidDriver<AndroidElement> driver;

	
	@Parameters({ "deviceName" })
	@Test
	public void f(String deviceName) throws MalformedURLException {
		Thread.currentThread().setName(deviceName);
		ThreadContext.put("ThreadName", Thread.currentThread().getName());
		LoggerUtil.debug("deviceName: " + deviceName);
		sClass = new InitDrvier(deviceName);
		driver = InitDrvier.driver;
		BasePage.device = sClass.dc.device;
		
		Assert.assertNotEquals(driver, null, "初始化driver失败");

/*		if(driver==null) {
			Thread.currentThread().destroy();
			LoggerUtil.error("driver初始化失败");
		}*/
	}
	

//	@AfterSuite(alwaysRun=true)
//	public void setDown() {
//		
//		sClass.dc.setDownAllCommand();
//	}

}
