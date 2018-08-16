package gby.appium.ui;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.print.DocFlavor.STRING;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import gby.appium.utils.LoggerUtil;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidTouchAction;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

public class AppiumStudy {

	static AndroidDriver<AndroidElement> driver;
	static Map<String, String> elmsMap = new HashMap<String, String>();
	static {
		elmsMap.put("homeSearch", "com.loulifang.house:id/rl_home_search_hint");
		elmsMap.put("searchInput", "com.loulifang.house:id/searchEdit");
	}

	public static void main(String[] args) throws MalformedURLException {
		// TODO Auto-generated method stub

		/*
		 * AppiumServiceBuilder builder = new
		 * AppiumServiceBuilder().withArgument(GeneralServerFlag.SESSION_OVERRIDE)
		 * .withIPAddress("127.0.0.1") .usingPort(8000); AppiumDriverLocalService
		 * service = AppiumDriverLocalService.buildService(builder); service.start();
		 */

		DesiredCapabilities cap = new DesiredCapabilities();

		// 设置启动参数
		cap.setCapability("automationName", "uiautomator2");
		cap.setCapability("platformName", "Android");
		cap.setCapability("platformVersion", "7.1.2");
		cap.setCapability("deviceName", "vivox9");
		cap.setCapability("udid", "6b00d640");// 6b00d640 //MFQ8FMJZJVPNRGZH
		cap.setCapability("appPackage", "com.loulifang.house");
		cap.setCapability("appActivity", "com.loulifang.house.activities.TMainActivity");
		cap.setCapability("unicodeKeyboard", "True");
		cap.setCapability("resetKeyboard", "True");

		// 初始化AndroidDriver
		String url = "http://127.0.0.1:4723/wd/hub";
		driver = new AndroidDriver<AndroidElement>(new URL(url), cap);
		LoggerUtil.info("AndroidDriver初始化完成，打开app成功");

		findElement("homeSearch").click();
		findElement("searchInput").sendKeys("地铁1号线");;
//		driver.findElementById("com.loulifang.house:id/ivTabHome").click();

		/* service.stop(); */

	}

	static WebElement findElement(String name) {
		String id = elmsMap.get(name);
		AndroidElement el = null;
		WebDriverWait wait = new WebDriverWait(driver, 20);
		try {
			el = wait.until(new ExpectedCondition<AndroidElement>() {
				@Override
				public AndroidElement apply(WebDriver d) {
					return driver.findElementById(id);
				}
			});
			LoggerUtil.debug("元素已找到：" + id);
		} catch (Exception e) {
			// TODO: handle exception
			LoggerUtil.error("未找到元素：" + id);
		}
		return el;
	}
	
	static void swipe() {
		TouchAction<AndroidTouchAction> touch = new AndroidTouchAction(driver);
		
	}
}
