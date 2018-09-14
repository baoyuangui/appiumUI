package gby.testng.testcase;

import org.testng.annotations.Test;

import gby.appium.page.HomePage;
import gby.appium.page.HouseSearchPage;
import gby.appium.page.MinePage;
import gby.appium.utils.AssertUtils;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.testng.annotations.BeforeClass;

public class HouseSearchCase {
	AndroidDriver<AndroidElement> driver;
	HouseSearchPage housespg;
	HomePage homepg;
	
  @Test
  public void searchHouse() {
	  homepg.clickIntoHomepg();
//	  housespg.clickIntoHomepg();
	  housespg.selectArea("附近", "3千米");
	  housespg.selectMoney("1500-2500");
	  housespg.selectSorting("价格从低到高");
	  
	  List<String> more = Arrays.asList("合租", "二室", "三室", "小区住宅");
	  housespg.selectMoreInfo(more);
	  housespg.setList(4);		//	滑动四页，获取更多房源信息
	  
	  AssertUtils.notInterruptAssert(Integer.parseInt(Collections.max(housespg.prices)) <= 2500, true, "检查价格上限是否正确");
	  AssertUtils.notInterruptAssert(Integer.parseInt(Collections.min(housespg.prices)) >= 1500, true, "检查价格下限是否正确");
	  
	  List<String> cpPrices = new ArrayList<>();
	  cpPrices.addAll(housespg.prices);
	  Collections.sort(cpPrices);
	  AssertUtils.notInterruptAssert(cpPrices, housespg.prices, "检查价格排序是否正确");
	  
  }
  @BeforeClass
  public void beforeClass() {
	  driver = InitDriverCase.driver;
	  housespg = new HouseSearchPage(driver);
	  homepg =  new HomePage(driver);
  }

}
