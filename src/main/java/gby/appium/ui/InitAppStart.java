package gby.appium.ui;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import gby.appium.ui.AppiumInit;
import gby.appium.utils.DevicesConnect;
import gby.appium.utils.LoggerUtil;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

public class InitAppStart {

	public AndroidDriver<AndroidElement> driver;
	public DevicesConnect dc;

public  InitAppStart(String deviceName) throws MalformedURLException {
		
		dc = new DevicesConnect(deviceName);
		dc.adbConnect();
		int port = Integer.parseInt(dc.device.getApmsrv_port());
        AppiumServiceBuilder builder =
                new AppiumServiceBuilder().withArgument(GeneralServerFlag.SESSION_OVERRIDE)
                        .withIPAddress("127.0.0.1")  
                        .usingPort(port);

        AppiumDriverLocalService service = AppiumDriverLocalService.buildService(builder);
        service.start();
		// TODO Auto-generated method stub

		DesiredCapabilities cap = new DesiredCapabilities();

		// 设置启动参数
		cap.setCapability("automationName", "Appium");
		cap.setCapability("platformName", dc.device.getOs());
		cap.setCapability("platform Version", dc.device.getOs_ver());
		cap.setCapability("deviceName", dc.device.getName());
		cap.setCapability("udid", dc.device.getIp()+":5555");
		cap.setCapability("appPackage", "com.loulifang.house");
		cap.setCapability("appActivity", "com.loulifang.house.activities.TMainActivity");
		cap.setCapability("unicodeKeyboard", "True");
		cap.setCapability("resetKeyboard", "True");	
		
		// 初始化AndroidDriver
		String url = "http://127.0.0.1:"+port+"/wd/hub";
		driver = new AndroidDriver<AndroidElement>(new URL(url), cap);
		LoggerUtil.info("AndroidDriver初始化完成，打开app成功");
	}

}
