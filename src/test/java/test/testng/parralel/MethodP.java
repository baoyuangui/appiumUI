package test.testng.parralel;

import org.testng.annotations.Test;
import org.apache.logging.log4j.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.LoggingEvent;
import org.testng.annotations.DataProvider;

public class MethodP {
	Logger logger = LoggerFactory.getLogger(MethodP.class);
	
	@Test
	public void a() {
		String threadName = Thread.currentThread().getId() + "";
		Thread.currentThread().setName(threadName);
		 ThreadContext.put("ThreadName", Thread.currentThread().getName());
	}
	
  @Test(dataProvider = "dp")
  public void f(Integer n, String s) {
//	  long id = Thread.currentThread().getId();
//      System.out.println("After test-class. Thread id is: " + id+" "+s);
	  logger.info("n: "+ n + "s: "+ s);
  }

  @DataProvider
  public Object[][] dp() {
    return new Object[][] {
      new Object[] { 1, "a" },
      new Object[] { 2, "b" },
    };
  }
}
