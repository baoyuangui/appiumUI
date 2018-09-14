package gby.appium.page;

import org.testng.Assert;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.iOSBy;

public class MinePage extends BasePage{

	public MinePage(AndroidDriver<AndroidElement> driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	
	public void clickIntoMinepg() {
		findElement("mineButton").click();
	}
	
	public void clickIntoLoginpg() {
		findElement("loginButton").click();
	}
	
	public void clickIntoSettingpg() {
		findElement("settingButton").click();
	}
	
	public void logout() {
		findElement("logoutOption").click();
		findElement("logoutEnsureButton").click();
	}
	
	
	/*
	 * 删除帖子Action
	 * 
	 * */
	public void deletePost() {
		int i = 0;
		while(findElement("myPostButton") == null && i < 3) {
			swipeToUp();
			i++;
			if(findElement("myPostButton") == null && i == 3)
				Assert.fail("删帖失败，找不到收藏帖子入口");
		}
		findElement("myPostButton").click();
		findElement("deleteButtons").click();
		findElement("deleteEnsureButton").click();		
	}
	
}
