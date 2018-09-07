package gby.appium.utils;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;


public class WebShot {

//	public File codeImageFile;// 例如"d：/codeImage.png"

	public  static void screenShot(WebElement codeElement) {
		// //截取验证码区域图片
		int x = codeElement.getLocation().x;
		int y = codeElement.getLocation().y;
		int width = codeElement.getSize().getWidth();
		int height = codeElement.getSize().getHeight();

		// 获取验证码区域
		Rectangle screenRectangle = new Rectangle(x, y, width, height);
		Robot robot = null;
		try {
			robot = new Robot();
		} catch (AWTException e) {
			LoggerUtil.error("robot实例化失败："+e.toString());
		}
		// 缓存区域图片
		BufferedImage codeBuffImage = robot.createScreenCapture(screenRectangle);
		
		// 保存
		File codeImageFile = new File("screenshots/test.png");
		try {
			ImageIO.write(codeBuffImage, "png", codeImageFile);
			
			LoggerUtil.info("验证码图片截图保存成功：screenshots");
		} catch (IOException e) {
			LoggerUtil.error("验证码图片保存失败："+e.toString());
		}
	}
	



}
