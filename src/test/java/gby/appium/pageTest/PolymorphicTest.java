/**
 * 
 */
package gby.appium.pageTest;

import bsh.This;
import gby.appium.page.BasePage;
import gby.appium.page.HomePage;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

/**
 * @author Administrator
 *
 */
public class PolymorphicTest {

	/**
	 * 
	 */
	public PolymorphicTest() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 * Administrator
	 *
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PolymorphicTest pp = new PolymorphicTest();
//		Father father = pp.new Father();
		Son son = pp.new Son();
		
//		father = son;
//		father.fmethod();
//		System.out.println(((Son) father).fString);
//		son = (Son) father;
//		son.sonly();
		
//		System.out.println(((Son) father).fString);
		
//		father = pp.new Son();
//		father.fmethod();
//		System.out.println(father.fString);;
//		
	}
	
	 class Father{
		/**
		 * 
		 */
			String fString = "f";
			int a = 1 ;
			boolean frag;
		 
		 
		 
		public Father() {
			// TODO Auto-generated constructor stub
			fmethod();
			System.out.println("father constructor"+this.getClass().getName());
		}

		 void fmethod() {
			System.out.println("father fmethod");	
			System.out.println(getClass().getSimpleName());
		}
		 private void fonlymethod() {
			System.out.println(this.toString());	
			System.out.println(getClass().getSimpleName());
		}
	}

	 class Son extends Father{
		String sString = "s";
		int sa = 1 ;
		public Son() {
			super.fonlymethod();
			fmethod();
		}
		void fmethod() {
			System.out.println(sString);
		}
		void sonly() {
			System.out.println(fString);
		}
	}
}
