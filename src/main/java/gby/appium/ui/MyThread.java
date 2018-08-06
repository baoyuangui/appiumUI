package gby.appium.ui;

import org.apache.logging.log4j.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyThread extends Thread{
	int s;
	private int a,b;
//	private String ThreadName=null;
	Logger logger = LoggerFactory.getLogger(getClass().getName());
	MyThread(int a, int b){
		this.a = a;
		this.b = b;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
//		super.run();
		sum(this.a, this.b);
		System.out.println("s,="+ s);
		ThreadContext.put("ThreadName", Thread.currentThread().getName());
		logger.info("s="+s);
	}
	
	int sum(int a, int b){
		s = a+b;	
		return s;
	}
	

}
