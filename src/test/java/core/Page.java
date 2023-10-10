package core;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.Duration;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

//import atu.testrecorder.ATUTestRecorder;
//import atu.testrecorder.exceptions.ATUTestRecorderException;

public class Page {
	
	public static WebDriver driver = null;
	public static Connection con = null;
	public static Statement stm = null;
	public static ResultSet rs = null;
	public static Logger log = null;
	public static ExtentReports report = null;
	public static ExtentTest test = null;
	// public static ATUTestRecorder recorder = null;

	    @FindBy(xpath = xpath.AllXpath.signin)
		public static WebElement signin;
		
		@FindBy(xpath = xpath.AllXpath.auid)
		public static WebElement auid;
		
		@FindBy(xpath = xpath.AllXpath.btn1)
		public static WebElement btn1;
		
		@FindBy(xpath = xpath.AllXpath.err1)
		public static WebElement err1;
		
		@FindBy(xpath = xpath.AllXpath.pwd)
		public static WebElement pwd;
		
		@FindBy(xpath = xpath.AllXpath.btn2)
		public static WebElement btn2;
		
		@FindBy(xpath = xpath.AllXpath.err2)
		public static WebElement err2;
		
		@FindBy(xpath = xpath.AllXpath.srchbox)
		public static WebElement srchbox;
		
		
  
  @Parameters({"browser","url"})	
  @BeforeMethod
  public void openBrowser(String browser,String url) 
  {
	  if(browser.equalsIgnoreCase("chrome"))
		{
			driver = new ChromeDriver();
			log.debug("chrome open..");
			test.log(LogStatus.INFO, "chrome open..");
		}
		else if(browser.equalsIgnoreCase("edge"))
		{
			driver = new EdgeDriver();
		}
		else if(browser.equalsIgnoreCase("firefox"))
		{
			driver = new FirefoxDriver();
		}
		
	//	driver.get(url); // not prefer cannot go back / forward / refresh
	
		     // or.....
		driver.navigate().to(url); // prefer can go back / forward / refrerh
		
		log.debug("url "+url+"open");
		test.log(LogStatus.INFO, "url "+url+"open");
		
		PageFactory.initElements(driver, this); // for @FindBy
				
		// implicit wait
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
		
		driver.manage().window().maximize(); // full screen of laptop
	
  }
  
  @Parameters({"filename"})
  @BeforeMethod(dependsOnMethods = "openBrowser")
  public void takeScreenShot(String filename) throws Exception
  {
	  File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE); // store file to temprary location
		//Now you can do whatever you need to do with it, for example copy somewhere download org.apache.commons.io.FileUtils class API set classpath and use this class to copy.
		String screenshotpath = System.getProperty("user.dir")+"\\src\\test\\java\\screenshot\\"+filename+".jpeg";
		
		FileUtils.copyFile(scrFile, new File(screenshotpath));

		log.debug("screenshot taken");
		test.log(LogStatus.PASS, "screen shot taken");
  }

  //@AfterMethod
  public void closeBrowser() 
  {
	  driver.close();
  }
  
  @Parameters({"wbname","sheet"})
  @BeforeClass
  public void makeWBConnection(String wbname,String sheet) throws Exception
  {
	  Class.forName("com.googlecode.sqlsheet.Driver"); // register excel driver 
	  String wbpath = System.getProperty("user.dir")+"//src//test//java//excel//"+wbname;
	  con = DriverManager.getConnection("jdbc:xls:file:"+wbpath);
	  System.out.println("connected to excel WB..");
	  stm=con.createStatement();  // default top to bottom
	  rs=stm.executeQuery("select * from "+sheet); // Sheet name 

  }

  @AfterClass
  public void closeWBConnection() throws Exception
  {
	  con.commit();
	  con.close();
  }

  @Parameters({"name","obj","key"})
  @BeforeTest
  public void generateLogReport(String name,String obj,String key) throws Exception 
  {
	  if(!Boolean.parseBoolean(key))
	  {
		  throw new SkipException("skip test..");
	  }
	  else
	  {
		  // log
		  Properties p = new Properties();
			FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"//src//test//resources//"+name+".properties");
		
			p.load(fis);
			p.put("log4j.appender."+obj+".File", System.getProperty("user.dir")+"//src//test//java//logs//"+name+".log");
			PropertyConfigurator.configure(p);
			
		log =	Logger.getLogger(name);
		
		  // report
		
		report = new ExtentReports(System.getProperty("user.dir")+"//src//test//java//reports//"+name+".html");
	    test = report.startTest(name);
		
	       // video
	//    recorder = new ATUTestRecorder(System.getProperty("user.dir")+"//src//test//java//video",name,false); // "RecordedVideo-"+dateFormat.format(date)
	//	recorder.start();
		
	  }
  }

  @AfterTest
  public void closeReport() throws Exception 
  {
	  report.endTest(test);
		report.flush();
		
	//	recorder.stop();
  }

}
