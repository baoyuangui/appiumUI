package gby.appium.ui;

public class Threadx {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		Thread thread1  = new MyThread(10,20);
		Thread thread2  = new MyThread(10,22);
		thread1.setName("udid1");
		thread2.setName("udid2");
		thread1.start();
		thread2.start();

	}

}
