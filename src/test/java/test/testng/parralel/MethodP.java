package test.testng.parralel;

import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;

import java.util.Map;

import org.apache.logging.log4j.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.LoggingEvent;
import org.testng.Assert;
import org.testng.annotations.DataProvider;

public class MethodP {
	Logger logger = LoggerFactory.getLogger(MethodP.class);
	Map<String, String> map = null;
	@Test
	public void a() {
		String threadName = Thread.currentThread().getId() + "";
		Thread.currentThread().setName(threadName);
		 ThreadContext.put("ThreadName", Thread.currentThread().getName());
		 
		 try {
			 int s = 10/0;
			 map.put("aaa", "bbb");
		} catch (Exception e) {
			e.getStackTrace();// TODO: handle exception
		}
		 
		 
		 assertNotNull(map);
	}
	
  @Test(dataProvider = "dp",dependsOnMethods="a")
  public void f(Integer n, String s) {
//	  long id = Thread.currentThread().getId();
//      System.out.println("After test-class. Thread id is: " + id+" "+s);
	  logger.info("n: "+ n + "s: "+ s);
	  logger.info("map: "+ map.toString());
  }

  @DataProvider
  public Object[][] dp() {
    return new Object[][] {
      new Object[] { 1, "a" },
      new Object[] { 2, "b" },
    };
  }
}
