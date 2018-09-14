/**
 * 
 */
package gby.appium.page;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

/**
 * @author gby
 *
 */
public class HomePage extends BasePage{

	/**
	 * @param driver
	 */
	public HomePage(AndroidDriver<AndroidElement> driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	public void clickIntoHomepg() {
		findElement("homePageButton").click();
	}

}
