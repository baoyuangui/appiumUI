package gby.appium.ui;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.print.DocFlavor.STRING;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.w3c.dom.css.ElementCSSInlineStyle;

import gby.appium.utils.LoggerUtil;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

public class AppiumStudy {

	static AndroidDriver<AndroidElement> driver;
	static Map<String, String> elmsMap = new HashMap<String, String>();
	static {
		elmsMap.put("homeSearch", "id@com.loulifang.house:id/rl_home_search_hint");
		elmsMap.put("searchInput", "id@com.loulifang.house:id/searchEdit");
		elmsMap.put("mine", "text@我的");
		elmsMap.put("searchResultArea", "id@com.loulifang.house:id/recycleView");
		elmsMap.put("H5EntryPageTop", "id@com.loulifang.house:id/topLayout");
		elmsMap.put("H5EntryPageBack", "id@com.loulifang.house:id/back");
		elmsMap.put("hizhuH5Page", "class@android.webkit.WebView");
		elmsMap.put("selectorAera", "id@area");

	}

	public static void main(String[] args) throws MalformedURLException, InterruptedException {
		// TODO Auto-generated method stub

		AppiumServiceBuilder builder = new AppiumServiceBuilder()
				.withArgument(GeneralServerFlag.SESSION_OVERRIDE)
				.withIPAddress("127.0.0.1").usingPort(8000);
		AppiumDriverLocalService service = AppiumDriverLocalService.buildService(builder);
		service.start();

		DesiredCapabilities cap = new DesiredCapabilities();

		// 设置启动参数
//		cap.setCapability("automationName", "uiautomator2");
		cap.setCapability("platformName", "Android");
		cap.setCapability("platformVersion", "7.1.2");
		cap.setCapability("deviceName", "vivox9");
		cap.setCapability("udid", "7POFC6HI5HPF5L6T");// 6b00d640 //MFQ8FMJZJVPNRGZH
		cap.setCapability("appPackage", "com.loulifang.house");
		cap.setCapability("appActivity", "com.loulifang.house.activities.TMainActivity");// TMainActivity
		cap.setCapability("unicodeKeyboard", "True");
//		cap.setCapability("resetKeyboard", "True");
		cap.setCapability("systemPort", 8209);
//		cap.setCapability("adbPort", 5039);

		// 初始化AndroidDriver
		String url = "http://127.0.0.1:8000/wd/hub";
		driver = new AndroidDriver<AndroidElement>(new URL(url), cap);
		LoggerUtil.info("AndroidDriver初始化完成，打开app成功");

//		findElement("mine").click();
//		findElement("searchInput").sendKeys("地铁1号线");
		int width = driver.manage().window().getSize().width;
		int height = driver.manage().window().getSize().height;

//		findElement("homeSearch").click();

		Thread.sleep(4000);
		tap(width / 2, height / 4);

//		findElement("searchInput").sendKeys("1号线");
		if (findElement("H5EntryPageTop").isDisplayed()) {
			swipeToUp();
			Thread.sleep(3000);
//			findElement("H5EntryPageBack").click();
			tap(498, 1866); // 点击底部嗨住租房，进入h5
		}
//		Thread.sleep(6000);
//		findElement("selectorAera").click();
//		
//		Thread.sleep(6000);
//		findElement("H5EntryPageBack").click();
//		Thread.sleep(2000);
//		swipeToUp();

//		driver.findElementById("com.loulifang.house:id/ivTabHome").click();

		service.stop();
	}

	static AndroidElement findElement(String name) {
		String id = elmsMap.get(name);
		AndroidElement el = null;
		WebDriverWait wait = new WebDriverWait(driver, 60);
		try {
			el = wait.until(new ExpectedCondition<AndroidElement>() {
				@Override
				public AndroidElement apply(WebDriver d) {
					return findEleBys(id);
				}
			});
			LoggerUtil.debug("元素已找到：" + name+": " +id);
		} catch (Exception e) {
			// TODO: handle exception
			LoggerUtil.error("未找到元素：" + name+": " +id);
		}
		return el;
	}

	static AndroidElement findEleBys(String id) {
		AndroidElement element = null;
		String byTpye = id.split("@")[0];
		String eleLoctorInfo = id.split("@")[1];
		switch (byTpye) {
		case "id":
			element = driver.findElementById(eleLoctorInfo);
			break;
		case "xpath":
			element = driver.findElementByXPath(eleLoctorInfo);
			break;
		case "desc":
			element = driver.findElementByAccessibilityId(eleLoctorInfo);
			break;
		case "text":
			element = driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + eleLoctorInfo + "\")");
			break;
		case "class":
			element = driver.findElementByClassName(eleLoctorInfo);
			break;
		default:
			LoggerUtil.error("暂不支持的by类型：" + byTpye);
			break;
		}
		return element;

	}
//	static void swipe() {
//		TouchAction<AndroidTouchAction> touch = new AndroidTouchAction(driver);
//		touch.
//	}

	static void swipeToUp() {
		Duration duration = Duration.ofMillis(150);// ((long) 1);
		int width = driver.manage().window().getSize().width;
		int height = driver.manage().window().getSize().height;
		TouchAction action1 = new TouchAction(driver);
		action1.press(PointOption.point(width / 2, height * 3 / 4)).waitAction(WaitOptions.waitOptions(duration))
				.moveTo(PointOption.point(width / 2, height  / 4)).release();

//		action1.press(PointOption.point(540, 1440)).waitAction(WaitOptions.waitOptions(duration))
//				.moveTo(PointOption.point(540, 400)).release().perform();

//		action1.perform();
		LoggerUtil.debug("往上滑动：" + width / 2 + "_" + height * 3 / 4 + " to " + width / 2 + "_" + height / 4);
	}

	static void swipeToDown() {
		Duration duration = Duration.ofSeconds(1);
		int width = driver.manage().window().getSize().width;
		int height = driver.manage().window().getSize().height;
		TouchAction action2 = new TouchAction(driver).press(PointOption.point(width / 2, height / 4))
				.waitAction(WaitOptions.waitOptions(duration)).moveTo(PointOption.point(width / 2, height * 3 / 4))
				.release();
		action2.perform();
	}

	static void swipeToLeft() {
		Duration duration = Duration.ofSeconds((long) 1);
		int width = driver.manage().window().getSize().width;
		int height = driver.manage().window().getSize().height;
		TouchAction action3 = new TouchAction(driver).press(PointOption.point(width * 3 / 4, height / 2))
				.waitAction(WaitOptions.waitOptions(duration)).moveTo(PointOption.point(width / 4, height / 2))
				.release();
		action3.perform();
	}

	static void swipeToRight() {
		Duration duration = Duration.ofSeconds((long) 1);
		int width = driver.manage().window().getSize().width;
		int height = driver.manage().window().getSize().height;
		TouchAction action4 = new TouchAction(driver).press(PointOption.point(width / 4, height / 2))
				.waitAction(WaitOptions.waitOptions(duration)).moveTo(PointOption.point(width * 3 / 4, height / 2))
				.release();
		action4.perform();
	}

	static void tap(int x, int y) {
		Duration duration = Duration.ofSeconds((long) 1);
		TouchAction action = new TouchAction(driver).press(PointOption.point(x, y)).release().perform();
		LoggerUtil.debug("点击point:" + x + "_" + y);
	}

}
