package gby.appium.parrallelLog;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;



public class parallelLogTest {

    public static void main(String[] args) {
        new Thread(new Runnable() {			
			@Override
			public void run() {
			Logger log = LogManager.getLogger(parallelLogTest.class);
			ThreadContext.put("ThreadName", Thread.currentThread().getName());
			    log.info("info");
			    log.debug("debug");
			    log.error("error");
			}
		}).start();
        new Thread(new Runnable() {			
			@Override
			public void run() {
				Logger log1 = LogManager.getLogger(parallelLogTest.class);
			ThreadContext.put("ThreadName", Thread.currentThread().getName());
			    log1.info("info");
			    log1.debug("debug");
			    log1.error("error");
			}
		}).start();
    }
}
