package gby.appium.utils;

import org.testng.Assert;

import gby.appium.page.BasePage;
import io.appium.java_client.android.AndroidElement;

public class AssertUtils extends Assert{
	
	
	public static void  checkBoolen(boolean actual,boolean expected, String... comment){
        String comm;
        if (comment.length == 0) {
            comm = "检查boolean型数据是否一样";
        } else {
            comm = comment[0];
        }      
        try {
            Assert.assertEquals(actual, expected);
            LoggerUtil.assertReportFormat(comm, actual + "", expected + "", "PASS");
        }
        catch (AssertionError e) {
        	LoggerUtil.assertReportFormat(comm, actual + "", expected + "", "FAIL");
            Assert.fail(comm+":失败");
        }
	}
	
    public void checkIntNum(int actual, int expected, String... comment) {
        String comm;
        if (comment.length == 0) {
            comm = "检查int型数字是否一样";
        } else {
            comm = comment[0];
        }
        try {
            Assert.assertEquals(actual, expected);
            LoggerUtil.assertReportFormat(comm, actual + "", expected + "", "PASS");
        } catch (AssertionError e) {
        	LoggerUtil.assertReportFormat(comm, actual + "", expected + "", "FAIL");
            Assert.fail("检查点检查出错误");
        }
    }
    
    public void checkDoubleNum(double actual, double expected, String... comment) {
        String comm;
        if (comment.length == 0) {
            comm = "检查double型数字是否一样";
        } else {
            comm = comment[0];
        }
        try {
            Assert.assertEquals(actual, expected);
            LoggerUtil.assertReportFormat(comm, actual + "", expected + "", "PASS");
        } catch (AssertionError e) {
        	LoggerUtil.assertReportFormat(comm, actual + "", expected + "", "FAIL");
            Assert.fail("检查点检查出错误");
        }
    }
    
    public void checkString(String actual, String expected, String... comment) {
        String comm;
        if (comment.length == 0) {
            comm = "检查字符串是否一样";
        } else {
            comm = comment[0];
        }
        try {
            Assert.assertEquals(actual, expected);
            LoggerUtil.assertReportFormat(comm, actual + "", expected + "", "PASS");
        } catch (AssertionError e) {
        	LoggerUtil.assertReportFormat(comm, actual + "", expected + "", "FAIL");
            Assert.fail("检查点检查出错误");
        }
    }
    
    public void checkStringContains(String actual, String expected, String... comment) {
        actual = actual.replaceAll("\n", " ");

        String comm;
        if (comment.length == 0) {
            comm = "检查实际字符串中是否有指定字段";
        } else {
            comm = comment[0];
        }
        try {
            Assert.assertEquals(true, actual.contains(expected));
            LoggerUtil.assertReportFormat(comm, actual + "", expected + "", "PASS");
        } catch (AssertionError e) {
        	LoggerUtil.assertReportFormat(comm, actual + "", expected + "", "FAIL");
            Assert.fail("检查点检查出错误");
        }
    }
    
    public void checkStringNotContains(String actual, String expected, String... comment) {
        actual = actual.replaceAll("\n", " ");

        String comm;
        if (comment.length == 0) {
            comm = "检查实际字符串中是否存在指定字段，存在为错误，不存在为正确";
        } else {
            comm = comment[0];
        }
        try {
            Assert.assertEquals(false, actual.contains(expected));
            LoggerUtil.assertReportFormat(comm, actual + "", "不包含\"" + expected + "\"", "PASS");
        } catch (AssertionError e) {
        	LoggerUtil.assertReportFormat(comm, actual + "", expected + "", "FAIL");
            Assert.fail("检查点检查出错误");
        }
    }
    
    public void checkNULL(Object actual, String... comment) {
        String comm;
        if (comment.length == 0) {
            comm = "检查对象是否为空，对象为空正确，对象非空错误";
        } else {
            comm = comment[0];
        }
        try {
            Assert.assertNull(actual);
            LoggerUtil.assertReportFormat(comm, "传入的对象 ：NULL" , "NULL" , "PASS");
        } catch (AssertionError e) {
        	LoggerUtil.assertReportFormat(comm,  "传入的对象 ：Not NULL" , "NULL" , "FAIL");
            Assert.fail("检查点检查出错误");
        }
    }
    
    public void checkNotNULL(Object actual, String... comment) {
        String comm;
        if (comment.length == 0) {
            comm = "检查对象是否为空，对象非空正确，对象为空错误";
        } else {
            comm = comment[0];
        }
        try {
            Assert.assertNotNull(actual);
            LoggerUtil.assertReportFormat(comm, "传入的对象 ：Not NULL" , " Not NULL" , "PASS");
        } catch (AssertionError e) {
        	LoggerUtil.assertReportFormat(comm, "传入的对象 ：NULL" , "NULL" , "FAIL");
            Assert.fail("检查点检查出错误");
        }
    }
    
    
    
    /**
     * 检查方法是否执行成功，进入到某个页面，值是否符合预期
     *@param bg 检查元素的页面类
     *@param excepted 可变参数，传一个，代表只检查元素是否存在
     *				 		传两个，后一个代表元素需要校验的值
     * 
     */
    public static void checkElementNotNull(String comment, BasePage bg, String element) {
    	
    	 try {
    		 Assert.assertNotNull(bg.findElement(element));
    		 LoggerUtil.assertReportFormat(comment, element+" Not NULL" , "预期 Not NULL" , "PASS");
         } catch (AssertionError e) {
         	LoggerUtil.assertReportFormat(comment, element+" IS NULL" , "预期 Not NULL" , "FAIL");
            Assert.fail("检查点检查出错误");           
         }	   	   	
    }
    
    public static void checkMethodSucess(String comment, BasePage bg, String element, String value) {
    	
    	checkElementNotNull(comment, bg, element);
    	try {
    		Assert.assertEquals(bg.findElement(element).getText().replace(" ", ""), value);
    		LoggerUtil.assertReportFormat(comment, element+"的值为："+ bg.findElement(element).getText().replace(" ", "") , "预期的值为："+ value , "PASS");
    	}catch(AssertionError e){
    		LoggerUtil.assertReportFormat(comment, element+"的值为："+ bg.findElement(element).getText().replace(" ", "") , "预期的值为："+ value , "FAIL");
    		Assert.fail("检查点检查出错误"); 
    	} 
   }
    
    /**
     * 
     * 非中断性断言
     * 适用于一个方法执行后，需要校验多个点的情况
     *  
     * */
    public static void notInterruptAssert(Object actual, Object expected, String... comment) {
    	 String comm;
         if (comment.length == 0) {
             comm = "两个对象是否相同";
         } else {
             comm = comment[0];
         }
         try {
             Assert.assertEquals(actual, expected);
             LoggerUtil.assertReportFormat(comm, "传入的对象 ："+actual.toString() , "实际："+ expected.toString() , "PASS");
         } catch (AssertionError e) {
         	LoggerUtil.assertReportFormat(comm, "传入的对象 ："+actual.toString() , "实际："+ expected.toString(), "FAIL");
         }
    }
    	
}
