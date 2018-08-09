package gby.appium.ui;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import gby.appium.ui.AppiumInit;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class InitAppStart {

	Logger logger;
	AndroidDriver<AndroidElement> driver;

public AndroidDriver<AndroidElement> initDriver(Device device) throws MalformedURLException {
		logger = LoggerFactory.getLogger(AppiumInit.class);


		// TODO Auto-generated method stub

		DesiredCapabilities cap = new DesiredCapabilities();

		// 设置启动参数
		cap.setCapability("automationName", "Appium");
		cap.setCapability("platformName", device.getOs());
		cap.setCapability("platform Version", device.getOs_ver());
		cap.setCapability("deviceName", device.getName());
		cap.setCapability("udid", device.getIp());
		cap.setCapability("appPackage", "com.loulifang.house");
		cap.setCapability("appActivity", "com.loulifang.house.activities.TMainActivity");

		// 初始化AndroidDriver
//		driver = new AndroidDriver<AndroidElement>(new URL("http://127.0.0.1 "/wd/hub"), cap);
		logger.info("AndroidDriver初始化完成，打开app成功");
		return driver;
	}

}
