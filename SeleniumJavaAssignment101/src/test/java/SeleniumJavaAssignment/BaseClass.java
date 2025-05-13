package SeleniumJavaAssignment;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.annotations.Parameters;

public class BaseClass {

	private String username = "prasad_sutar2";
	private String accesskey = "vowO0bsxfuhkcGtC8hH2p2g9ee6x6GGDPfRtifxyILK5VIjVNL";
	private String hubURL = "hub.lambdatest.com/wd/hub";

	protected RemoteWebDriver driver;


	@Parameters({"platformname","browser","browserversion"})
	@BeforeTest
	public void setUp(String platformname,String browser,String browserversion) {

		DesiredCapabilities capabilities = new DesiredCapabilities();

		capabilities.setCapability("platformName", platformname);
		capabilities.setCapability("browserName", browser);
		capabilities.setCapability("browserVersion", browserversion); 


		// LambdaTest-specific options
		HashMap<String, Object> ltOptions = new HashMap<>();

		ltOptions.put("username", username); //user name
		ltOptions.put("accessKey", accesskey); //Password
		ltOptions.put("visual", true); //Screenshots
		ltOptions.put("video", true); //Video Recording
		ltOptions.put("network", true); //Network Log
		ltOptions.put("build", "final-Build"); //Build Name
		ltOptions.put("project", "LabmdaTest"); //Project Name
		ltOptions.put("name", "Selenium Java Lambda Test"); //Test Name 
		ltOptions.put("tunnel", false); // Disabling the tunnel for anywhere accessible Uri
		ltOptions.put("console", true); //Console Log
		ltOptions.put("w3c", true); // Enable W3C WebDriver protocol for compatibility with the latest WebDriver standards
		ltOptions.put("terminal", true); //terminal Log


		// Set the LT options in ChromeOptions
		capabilities.setCapability("LT:Options", ltOptions);

		// Create RemoteWebDriver instance and connect to LambdaTest cloud
		try {
			driver = new RemoteWebDriver(new URL("https://" + username + ":" + accesskey + "@" + hubURL), capabilities);
		} catch (MalformedURLException e) {
			System.out.println("Invalid grid URL: " + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Error while initializing driver: " + e.getMessage());
			e.printStackTrace();
		}

	}
	//@BeforeTest
	public void startUp() {
		driver = new ChromeDriver();
	}
	@BeforeMethod
	public void setURI() {
		if (driver != null) {
			driver.manage().window().maximize();
			driver.get("https://www.lambdatest.com/selenium-playground/");
		} else {
			System.out.println("Driver is not initialized!");
		}
	}
	@AfterMethod
	public void onFailureTakeSS(ITestResult testResult) {

		TakesScreenshot snap = (TakesScreenshot) driver;
		File source = snap.getScreenshotAs(OutputType.FILE);
		File Destination = new File(System.getProperty("user.dir")+ "/snaps/"+ testResult.getName() + ".png");

		try {
			FileHandler.copy(source, Destination);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@AfterTest
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}
}
