package gby.appium.ui;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class AppiumInit {

	public static void main(String[] args) throws MalformedURLException {
		 Logger  logger = LoggerFactory.getLogger(AppiumInit.class);
		
		// TODO Auto-generated method stub
		AndroidDriver<AndroidElement> driver;
		DesiredCapabilities cap = new DesiredCapabilities();

		// 设置启动参数
		cap.setCapability("automationName", "Appium");
		cap.setCapability("platformName", "Android");
		cap.setCapability("platformName", "5.1");
		cap.setCapability("deviceName", "oppoR9");
		cap.setCapability("udid", "192.168.20.60:5555");
		cap.setCapability("appPackage", "com.loulifang.house");
		cap.setCapability("appActivity", "com.loulifang.house.activities.TMainActivity");

		//初始化AndroidDriver
		driver = new AndroidDriver<AndroidElement>(new URL("http://127.0.0.1:4492/wd/hub"),cap);
		logger.debug("AndroidDriver初始化完成，打开app成功");
		driver.findElement(By.id("com.loulifang.house:id/llItem")).click();
		logger.debug("llItem列表点击成功");
	}

}
