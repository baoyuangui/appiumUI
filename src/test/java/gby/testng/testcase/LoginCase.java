package gby.testng.testcase;

import org.testng.annotations.Test;

import gby.appium.page.LoginPage;
import gby.appium.page.MinePage;
import gby.appium.utils.AssertUtils;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterTest;

public class LoginCase {
	AndroidDriver<AndroidElement> driver;
	MinePage minepg;
	LoginPage loginpg;
	
  @Test
  public void login() {
	  minepg.clickIntoMinepg();
	  minepg.clickIntoLoginpg();
	  loginpg.login("15575993304", "180205");
	  AssertUtils.checkMethodSucess("检查登录是否成功", minepg, "phoneNumber", "15575993304");
  }
  @BeforeClass
  public void beforeClass() {
	  driver = InitDriverCase.driver;
	  minepg = new MinePage(driver);
	  loginpg = new LoginPage(driver);
  }

  @AfterTest(dependsOnMethods = "login")
  public void logout() {
	  minepg.backToHome();
	  minepg.clickIntoMinepg();
	  minepg.clickIntoSettingpg();
	  minepg.logout();
	  AssertUtils.checkMethodSucess("检查注销是否成功", minepg, "nickname", "立即登录");
  }

}
