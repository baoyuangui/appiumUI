package gby.appium.listern;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import gby.appium.deviceM.InitDrvier;
import gby.appium.page.BasePage;
import gby.appium.utils.LoggerUtil;


public class TestNGListener extends TestListenerAdapter {

	// testng初始化
	@Override
	public void onStart(ITestContext testContext) {
		LoggerUtil.info("测试初始化");
		super.onStart(testContext);
	}

	// case开始
	@Override
	public void onTestStart(ITestResult tr) {
		LoggerUtil.info("【" + tr.getName() + "】测试用例开始执行");
		super.onTestStart(tr);
	}

	// case执行成功
	@Override
	public void onTestSuccess(ITestResult tr) {
		LoggerUtil.info("【" + tr.getName() + "】测试用例执行成功");
		super.onTestSuccess(tr);
	}

	// case执行失败
	@Override
	public void onTestFailure(ITestResult tr) {
		super.onTestFailure(tr);
		LoggerUtil.error("【" + tr.getName() + "】测试用例执行失败");
		captureScreenShot(tr);
		new BasePage(InitDrvier.driver).backToHome();
//		takeScreenShot(tr);
	}

	// case被跳过
	@Override
	public void onTestSkipped(ITestResult tr) {
		super.onTestSkipped(tr);
		LoggerUtil.error("【" + tr.getName() + "】测试用例被跳过");
		new BasePage(InitDrvier.driver).backToHome();
//        takeScreenShot(tr);
	}

	@Override
	public void onFinish(ITestContext testContext) {
		LoggerUtil.info("测试执行完毕");

		Iterator<ITestResult> listOfFailedTests = testContext.getFailedTests().getAllResults().iterator();
		Iterator<ITestResult> listOfSkippedTests = testContext.getSkippedTests().getAllResults().iterator();

		while (listOfFailedTests.hasNext()) {
			// 失败的测试
			ITestResult failedTest = listOfFailedTests.next();
			ITestNGMethod failedMethod = failedTest.getMethod();
			
			LoggerUtil.debug(failedTest.getName()+ ":"+ failedTest.toString());
			
			//失败的测试如果因重跑次数累计多次，则去重
			if (testContext.getFailedTests().getResults(failedMethod).size() > 1) {
				listOfFailedTests.remove();
			} else {
				//失败的测试如果因重跑成功了，则从失败结果中剔除该测试，只看成功的
				if (testContext.getPassedTests().getResults(failedMethod).size() > 0) {
					listOfFailedTests.remove();
				}
			}
		}
		while (listOfSkippedTests.hasNext()) {
			//	跳过的用例
			ITestResult skippedTest = listOfSkippedTests.next();
			ITestNGMethod skippedMethod = skippedTest.getMethod();
			
			LoggerUtil.debug(skippedTest.getName()+ ":"+ skippedTest.toString());

			
//			 	//如果失败的方法在跳过方法中，直接把跳过方法从结果中移除
//			System.out.println("跳过：" + skippedTest.getName() + ", 失败：" + failedTest.getName());
//        	
//        	if(skippedTest.getName().equals(failedTest.getName())) {
//        		listOfSkippedTests.remove();
//        	}      

			//凡是跳过的测试在失败或成功的测试结果中，则从跳过的结果中剔除。都是为了修改后期报告中的统计
			if (testContext.getFailedTests().getResults(skippedMethod).size() > 0) {
				listOfSkippedTests.remove();
			}
			if (testContext.getPassedTests().getResults(skippedMethod).size() > 0) {
				listOfSkippedTests.remove();
			}
		}

	}
	
	

	/**
	 *	 监听里的截图方法
	 * @author gby
	 *
	 */
	public void captureScreenShot(ITestResult result){  
		
        File srcFile = InitDrvier.driver.getScreenshotAs(OutputType.FILE);
        
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
        String threadName = Thread.currentThread().getName();
        File location = new File(threadName + "_screenshots");
        
        String dest = result.getMethod().getRealClass().getSimpleName()  
        				+ "." + result.getMethod().getMethodName();
       
        File targetFile = new File(location.getAbsolutePath() + File.separator 
        				+ dest + "_" + dateFormat.format(new Date())+".png");
//        System.out.println("----------------- file is " + targetFile.getPath());

        try {
            FileUtils.copyFile(srcFile, targetFile);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        LoggerUtil.screenShotLog("失败截图：" + targetFile, targetFile);
    }
	

}
