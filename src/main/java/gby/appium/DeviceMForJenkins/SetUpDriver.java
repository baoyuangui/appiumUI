package gby.appium.DeviceMForJenkins;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import gby.appium.utils.LoggerUtil;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

public class SetUpDriver {

//	public AndroidDriver<AndroidElement> driver;
	public DevicesConnect dc;
	
	public SetUpDriver(String deviceName) {
		dc = new DevicesConnect(deviceName);
	}
	public AndroidDriver<AndroidElement> getDriver() throws MalformedURLException {
	
		dc.adbConnect();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				dc.startServer();//方法内部获取了锁
			}
		}, dc.device.getName() + "_appiumServer").start();
		
		/*确保newTread先获取锁，主线程先等待两秒。主线程想获取到锁，必须要等startServer内部先释放锁，
			释放锁的前提就是appiumServer（）方法服务已经启动。
		*/
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//锁的目的是为了形成竞争，确保一方能优先，防止主线程在appiumserver启动前执行后面的代码
        dc.serverLock.lock();
/*        LoggerUtil.info("启动appiumServer");*/
        dc.serverLock.unlock();
		// TODO Auto-generated method stub

		DesiredCapabilities cap = new DesiredCapabilities();

		// 设置启动参数
		cap.setCapability("automationName", "uiautomator2");
		cap.setCapability("platformName", dc.device.getOs());
		cap.setCapability("platformVersion", dc.device.getOs_ver());
		cap.setCapability("deviceName", dc.device.getName());
		cap.setCapability("udid", dc.device.getIp() + ":5555");
		cap.setCapability("app", System.getProperty("user.dir")+"/src/main/resources/hizhu.apk");
		cap.setCapability("appPackage", "com.loulifang.house");
		cap.setCapability("appActivity", "com.loulifang.house.activities.TMainActivity");
		cap.setCapability("unicodeKeyboard", true);
		cap.setCapability("resetKeyboard", true);
//		cap.setCapability("systemPort", Integer.parseInt(dc.device.getApmsrv_bp()+1));
		/*据说多设备平行运行时，如果不配置不同的systemPort，会出错。不过目前我没遇到*/
		
		// 初始化AndroidDriver
		String url = "http://127.0.0.1:" + dc.device.getApmsrv_port() + "/wd/hub";
//		try {
			AndroidDriver<AndroidElement> driver = new AndroidDriver<AndroidElement>(new URL(url), cap);
			LoggerUtil.info("AndroidDriver初始化完成，打开app成功");
//			return driver;
//		} catch (Exception e) {
//			// TODO: handle exception
//			LoggerUtil.error("初始化driver失败");
//		}

		return driver;
	}

}
