package gby.appium.pageTest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log4jFilterTest {
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Logger logger = LoggerFactory.getLogger(Log4jFilterTest.class);
		logger.info("udidxxx");
		logger.info("udidzzz");
		logger.debug("udidzzz");
		logger.debug("udidxxx");
		logger.error("udidzzz");
		logger.error("udidxxx");
	}

}
