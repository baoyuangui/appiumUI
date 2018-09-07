package gby.appium.page;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class LoginPage extends BasePage{
	
	public LoginPage(AndroidDriver<AndroidElement> driver) {
		super(driver);
	}
	
	public void login(String pnumber, String smscode) {
		findElement("userName").sendKeys(pnumber);
		findElement("password").sendKeys(smscode);
		findElement("loginButton").click();
	}
	
	
	
}
