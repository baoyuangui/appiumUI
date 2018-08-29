package gby.appium.page;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class MinePage extends BasePage{

	public MinePage(AndroidDriver<AndroidElement> driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	
	/*
	 * 删除帖子Action
	 * 
	 * */
	public void deletePost() {
				
		findElement("mineButton").click();
		
		while(findElement("myPostButton") == null) {
			swipeToUp(1000);
		}
		findElement("myPostButton").click();
		findElement("deleteButtons").click();
		findElement("deleteEnsureButton").click();		
	}
	
}
