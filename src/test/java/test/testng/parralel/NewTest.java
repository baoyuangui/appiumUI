package test.testng.parralel;

import org.apache.logging.log4j.ThreadContext;
import org.checkerframework.framework.qual.FromByteCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import lombok.experimental.var;

public class NewTest {
	Logger logger = LoggerFactory.getLogger(NewTest.class);
  @Test
  public void f() {
	  
	  logger.info("newtest");
	  logger.debug("newtest_debug");
	  logger.error("newtest_error");
	  
  }
  
}
