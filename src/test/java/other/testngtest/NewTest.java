package other.testngtest;

import org.testng.annotations.Test;

public class NewTest {
  @Test
  public void f() {
	  int a  = 10/0;
	  System.out.println(a);
	  
  }
  @Test(dependsOnMethods = "f")
  public void g() {
	  System.out.println("this is g method that denends on f method");
  }
}
