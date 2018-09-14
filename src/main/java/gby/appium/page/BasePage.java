package gby.appium.page;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.alibaba.fastjson.JSONObject;

import gby.appium.deviceM.Device;
import gby.appium.utils.LoggerUtil;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

public class BasePage {

	public AndroidDriver<AndroidElement> driver;
	public Map<String, String> elesMap = new HashMap<String, String>();
	public static Device device;

	public BasePage(AndroidDriver<AndroidElement> driver) {
		// TODO Auto-generated constructor stub
		this.driver = driver;
		elesMap = this.getElesMap();
	}

	
	/**
	 * 
	 * 根据外部的类的调用，获取对应类名的json文件，生成元素信息map
	 * 
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> getElesMap() {
		String filePath = System.getProperty("user.dir") + "/elementInfo/" + getClass().getSimpleName() + ".json";
		BufferedReader bufferedReader = null;
		try {
			bufferedReader = new BufferedReader(new FileReader(new File(filePath)));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		String jsonContent = null;
		try {
			jsonContent = IOUtils.toString(bufferedReader);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return (Map<String, String>) JSONObject.parse(jsonContent);
	}

	/**
	 * 
	 * 显示等待15s，根据元素map key值获取定位信息， 返回元素对象
	 * 
	 */
	public AndroidElement findElement(String name) {
//		final String id = this.elesMap.get(name);
		AndroidElement el = null;
		WebDriverWait wait = new WebDriverWait(driver, 15);
		try {
			el = wait.until(new ExpectedCondition<AndroidElement>() {
				@Override
				public AndroidElement apply(WebDriver d) {
					return findEleBy(name);
				}
			});
//			LoggerUtil.debug("元素已找到：" + name+": " +id);
		} catch (Exception e) {
			// TODO: handle exception
			LoggerUtil.error("【" + name + "】识别失败：识别超时"+ e.getMessage());
//			Assert.fail("【"+ name +"】元素识别失败，该测试方法自动结束失败");
		}
		return el;
	}

	/**
	 * 
	 * 关键字驱动，兼容多种定位方式
	 * @pramater name 元素名称key
	 * @return AndroidElement
	 * 
	 */
	private AndroidElement findEleBy(String name) {
		String id = this.elesMap.get(name);
		
		//如果id为null,表示name不在map的key里，直接使用text识别，一般用于仅text定位，方便使用，不用再在json文件中添加对应text的元素信息
		if(id == null) {
			return driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + name + "\")");
		}
		
		AndroidElement element = null;
		String byTpye = id.split("@")[0];
		String eleLoctorInfo = id.split("@")[1];
		try {
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
				element = driver.findElementByAndroidUIAutomator("new UiSelector().textStartsWith(\"" + eleLoctorInfo + "\")");
				break;
			case "class":
				element = driver.findElementByClassName(eleLoctorInfo);
				break;
			case "name":// name和text都是用于定位text，但是text用于定位7.0以上的系统
				element = driver.findElementByName(eleLoctorInfo);
				break;
			default:
				LoggerUtil.error("暂不支持的by类型：" + byTpye);
				break;
			}
			LoggerUtil.info("【" + name + "】识别成功");
		} catch (Exception e) {
			LoggerUtil.info("【" + name + "】暂未找到，轮询中...");
		}
		return element;
	}

	/**
	 * 
	 * 返回具有相同定位元素信息的list
	 * 
	 */
	ArrayList<AndroidElement> findElesBy(String name) {
		String id = this.elesMap.get(name);
		ArrayList<AndroidElement> elements = new ArrayList<AndroidElement>();
		String byTpye = id.split("@")[0];
		String eleLoctorInfo = id.split("@")[1];

			switch (byTpye) {
			case "id":
				elements = (ArrayList<AndroidElement>) driver.findElementsById(eleLoctorInfo);
				break;
			case "xpath":
				elements = (ArrayList<AndroidElement>) driver.findElementsByXPath(eleLoctorInfo);
				break;
			case "class":
				elements = (ArrayList<AndroidElement>) driver.findElementsByClassName(eleLoctorInfo);
				break;
			default:
				LoggerUtil.error("暂不支持的by类型：" + byTpye);
				break;
			}
			if(elements.size() != 0) {
				LoggerUtil.info("【" + name + "】识别成功");
			}else {
				LoggerUtil.info("【" + name + "】未找到，识别失败,元素list长度为0");
			}
			

		return elements;
	}

	/**
	 * 
	 * 	逐一点击元素列表中的元素，一般用于多选 num 代表多选几个，无差别选择
	 * 
	 */
	public void clickAllEles(ArrayList<AndroidElement> als, int num) {

		for (int x = 0; x < num; x++) {
			if (x > als.size() - 1)
				break;
			als.get(x).click();
		}
	}

	public void swipe(int x, int y, int toX, int toY, int millis) {
		Duration duration = Duration.ofMillis(millis);
		TouchAction action = new TouchAction(driver);

		action.press(PointOption.point(x, y)).waitAction(WaitOptions.waitOptions(duration))
				.moveTo(PointOption.point(toX, toY)).release();

		action.perform();
	}

	public void swipe(int x, int y, int toX, int toY) {
		swipe(x, y, toX, toY, 1000);
	}

	public void swipeToUp(int millis) {
		int width = driver.manage().window().getSize().width;
		int height = driver.manage().window().getSize().height;

		swipe(width / 2, height * 3 / 4, width / 2, height / 4, millis);
		LoggerUtil.debug("往上滑动：" + width / 2 + "_" + height * 3 / 4 + " to " + width / 2 + "_" + height / 4);
	}

	public void swipeToUp() {
		swipeToUp(1500);
	}

	public void quickSwipeToUp() {
		swipeToUp(150);
	}

	/**
	 * 
	 * toast的识别需要使用uiautomator2 仅使用了xpath的方式来识别
	 * 
	 * @param name 表示需要识别的toast里消息的部分文字
	 * @return boolean 找到返回true，未找到或内容不匹配返回false
	 */
	public boolean findToast(String name) {
		boolean isFind = false;
//		String id = this.elesMap.get(name);
//		String eleLoctorInfo = id.substring(6);
		WebDriverWait wait = new WebDriverWait(driver, 15);
		try {
			WebElement ae = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class='android.widget.Toast']")));
			if(ae.getText().contains(name)) {
				isFind = true;
			}else {
				isFind = false;
				LoggerUtil.error("toast 内容不匹配");
			}	
		} catch (Exception e) {
			// TODO: handle exception
			LoggerUtil.error("【	"+ name + " toast】未找到");
		}
		return isFind;

	}

	
	/**
	 * 	利用keyevent事件返回至app最外层主页
	 * @author gby
	 *
	 */
	public void backToHome() {
		int size = 0;
		while(size == 0){
			KeyEvent kEvent = new KeyEvent(AndroidKey.BACK);
			driver.pressKey(kEvent);
			size = findElesBy("homePageButton").size();
			LoggerUtil.info("正在返回首页...");
		}
		LoggerUtil.info("已返回首页");
	}

}
