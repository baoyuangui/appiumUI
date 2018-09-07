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
        } catch (AssertionError e) {
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
    
    
    public static void checkIsNextPage(BasePage bg, String excepted) {
    	 try {
    		 Assert.assertNotNull(bg.findElement(excepted));
         } catch (AssertionError e) {
             Assert.fail("检查点检查出错误");
         }
    		

    	
    	
    	
    	
    }
    	
}
