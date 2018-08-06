package gby.testng.testcase;


import org.openqa.selenium.By;
import org.testng.annotations.Test;
import gby.testng.testcase.InitDriverCase;

public class LoginCase {
  @Test
  public void f() {
	  InitDriverCase.driver.findElement(By.id("com.loulifang.house:id/mineLyt")).click();
  }
}
