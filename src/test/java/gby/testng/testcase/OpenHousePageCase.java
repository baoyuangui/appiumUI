package gby.testng.testcase;


import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import gby.testng.testcase.InitDriverCase;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class OpenHousePageCase {
	 AndroidDriver<AndroidElement> driver;
	Logger logger =LoggerFactory.getLogger(OpenHousePageCase.class);
  @Test
  public void f() {
	  try {
		Thread.sleep(3000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  //获取InitDriverCase中已初始化的driver
	  driver = InitDriverCase.driver; 
//	  int width = driver.manage().window().getSize().width;//获取当前屏幕的宽度
//	  int height = driver.manage().window().getSize().height;//获取当前屏幕的高度
//	  
//	  driver.swipe();
//	  
	  driver.findElementById("com.loulifang.house:id/llItem").click();
	  
	  logger.info("点击房源列表，进入房源详情ok");
  }
}
