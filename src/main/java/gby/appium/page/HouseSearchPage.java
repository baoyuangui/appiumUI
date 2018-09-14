/**
 * 
 */
package gby.appium.page;

import java.util.ArrayList;
import java.util.List;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

/**
 * @author gby
 *
 */
public class HouseSearchPage extends MinePage{
	
	public List<String> prices = new ArrayList<String>();//价格
	public List<String> roomTypes = new ArrayList<String>();;//房型：整租or合租
	public List<String> proportion = new ArrayList<String>();;//面积
	public List<String> distance = new ArrayList<String>();;//距离
	

	/**
	 * @param driver
	 */
	public HouseSearchPage(AndroidDriver<AndroidElement> driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	public void selectArea(String... areaName) {
		findElement("areaSelector").click();
		if(areaName.length > 0)
			findElement(areaName[0]).click();
		if(areaName.length > 1)
			findElement(areaName[1]).click();
		if(areaName.length > 2)
			findElement(areaName[2]).click();
	}
	
	public void selectMoney(String money ) {
		findElement("moneySelector").click();
		findElement(money).click();
	}
	
	public void selectSorting(String sorting) {
		findElement("sortSelector").click();
		findElement(sorting).click();				
	}
	
	public void selectMoreInfo(List<String> more) {
		findElement("moreSelector").click();
		more.forEach((name) -> findElement(name).click());
		findElement("moreEnsureButotn").click();
	}
	
	/**
	 * 	将当前页面房源的信息：价格、房源类型、描述、距离抓取
	 *  
	 * */
	public void setList() {
//		findElesBy("prices").forEach((AndroidElement ae) -> prices.add(ae.getText()));
//		findElesBy("roomTypes").forEach((AndroidElement ae) -> prices.add(ae.getText()));
//		findElesBy("roomDescriptions").forEach((AndroidElement ae) -> prices.add(ae.getText()));
//		findElesBy("distances").forEach((AndroidElement ae) -> prices.add(ae.getText()));
		prices.addAll(getTexts(findElesBy("prices")));
		roomTypes.addAll(getTexts(findElesBy("roomTypes")));	
	}
	
	public List<String> getTexts(List<AndroidElement> eleLists){
		List<String> ls = new ArrayList<String>();
		for(AndroidElement ae : eleLists) {
			ls.add(ae.getText());
		}
		return ls;
		
	}
	public void setList(int n) {
		int i = 0;
		while(i < n) {
			setList();
			swipeToUp();
			i++;
		};
	}

	/**
	 * 
	 * Administrator
	 *
	 */
	public void clickIntoHomepg() {
		// TODO Auto-generated method stub
		findElement("homePageButton").click();
	}
}
