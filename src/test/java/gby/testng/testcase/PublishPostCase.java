package gby.testng.testcase;


import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


import gby.appium.page.FindRoommateBasePage;
import gby.appium.page.LoginPage;
import gby.appium.page.MinePage;
import gby.appium.utils.AssertUtils;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class PublishPostCase {
	AndroidDriver<AndroidElement> driver;
	
	FindRoommateBasePage findbg;
	MinePage minepg;
	LoginPage loginpg;
	
  @BeforeClass
  public void setup() {
	  	driver = InitDriverCase.driver;
		findbg = new FindRoommateBasePage(driver);
//		loginpg = new LoginPage(driver);
//		loginpg.login("15575993304", "180205");
  }
  
  
  @Test
  public void  intoPublisPostPage() {
	  findbg.clickIntoRoommatePage();
	  findbg.openPublishPage();
	  AssertUtils.checkElementNotNull("检查是否进入发帖页",findbg, "titleInput");
	  
}
  
  @Test(dependsOnMethods = {"intoPublisPostPage"})
  public void publishRoom() {
//	  findbg.clickIntoRoommatePage();
//	  findbg.openPublishPage();
	  findbg.inputText("标题", "其他内容");
	  findbg.selectLocation("jingan_jingansi","line7_yuntaiLoad");
	  findbg.selectPrice();
	  findbg.selectImages();
	  findbg.clickPublish();

	  boolean isFind = findbg.findToast("发帖成功");
	  Assert.assertEquals(isFind, true, "发布失败");
  }
  
    
  @AfterClass
  //发帖数据清除
  public void deletePost() {
	  minepg = new MinePage(driver);
	  minepg.clickIntoMinepg();
	  AssertUtils.checkElementNotNull("检查是否进入我的页面",minepg, "loginButton");
	  
	  minepg.deletePost();
	  Assert.assertEquals(minepg.findToast("删除成功"), true, "删除失败，没有找到删除成功的toast");
  }
   
}
