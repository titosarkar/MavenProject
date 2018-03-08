package ExyentReportScreenshot.ExyentReportScreenshot1;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class FreeCRMTest {

	public WebDriver cd;
	public ExtentReports extent;
	public ExtentTest extentTest;
	
	@BeforeTest 
	public void setExtend(){
		
		/*In extent constructor I'm passing 2 parameter appended by (+)(String buffer example).
		 *  Here I'm "true" key word to replace existing ExtentReport.html report with the new.
		 *  If I pass "false" it will not replace  
		*/
		extent=new ExtentReports(System.getProperty("user.dir")+"/test-output/ExtentReport.html",true );
		extent.addSystemInfo("Host name", "mhsnr");
		extent.addSystemInfo("User Name", "mhsnr");
		extent.addSystemInfo("Environment", "QA");
		}
		
	@AfterTest
	public void endReport(){
		extent.flush(); // disconnect the connection with extent report
		extent.close();
	}
	
	public static String getScreenShot(WebDriver cd, String sceenshotName) throws Exception{
		//To get screen shot with date 
		String dateName =new SimpleDateFormat("yyyymmddhhmmss").format(new Date());
		TakesScreenshot ts=(TakesScreenshot)cd;
		File source=ts.getScreenshotAs(OutputType.FILE);
		//After execution you should see folder "/FailedTestsScreenshots" under src folder
		 
		String destination=System.getProperty("user.dir")+ "/FailedTestsScreenshot/"+sceenshotName+
				dateName + ".png";
		File finaldestination=new File(destination);
		FileUtils.copyDirectory(source, finaldestination);
		return destination;
		
	}
	
	
	@BeforeMethod
	public void setup(){
		
		System.setProperty("webdriver.chrome.driver", "D:\\All_Driver\\chromedriver.exe");
	    cd=new ChromeDriver();
		cd.manage().window().maximize();
		// To go to url
		cd.manage().timeouts().pageLoadTimeout(20,TimeUnit.SECONDS);
		cd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	    cd.get("https://www.freecrm.com/");
	    }
	    
	    @Test
	    public void FreeCRMTitleTest(){
	    	//extentTest=extent.startTest("FreeCRMTitleTest");
	   String title=cd.getTitle();
	   Assert.assertEquals(title, "#1 Free CRM for Any Business: Online Customer Relationship Software");
	   System.out.println("print title:"+title);
	    
		
	
		
	}
	    @AfterMethod
		public void teardown(ITestResult result) throws Exception{
		if 
		(result.getStatus()==ITestResult.FAILURE)
			//To add name in extent report
	    extentTest.log(LogStatus.FAIL, "Test case failed is " +result.getName());
		  //To add error/exception in extent report
	    extentTest.log(LogStatus.FAIL, "Test case failed is " +result.getThrowable());
	    String screenshotPath=FreeCRMTest.getScreenShot(cd, result.getName());
	      // To do screenshot iin extent report
        extentTest.log(LogStatus.FAIL,extentTest.addScreencast(screenshotPath));
		}
	    //else {result.getStatus()==ITestResult.SKIP}{
	    	
		//	extentTest.log(LogStatus.SKIP, "Testcase skip is" +result.getName);	
	   // }
	 	   
	    //}

}
