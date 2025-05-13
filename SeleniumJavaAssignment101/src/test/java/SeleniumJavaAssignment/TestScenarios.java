package SeleniumJavaAssignment;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

public class TestScenarios extends BaseClass{


	@Test (priority=1)
	public void simpleFormDemo() {

		//1. Open LambdaTest’s Selenium Playground from https://www.lambdatest.com/selenium-playground

		//2. Click “Simple Form Demo”.
		driver.findElement(By.linkText("Simple Form Demo")).isDisplayed();
		driver.findElement(By.linkText("Simple Form Demo")).click();

		//3. Validate that the URL contains “simple-form-demo”
		Assert.assertTrue(driver.getCurrentUrl().contains("simple-form-demo"), "URL does not contain 'simple-form-demo'");

		//4. Create a variable for a string value, e.g., “Welcome to LambdaTest”.
		String inputTxt = "Welcome to LambdaTest" ;

		//5. Use this variable to enter values in the “Enter Message” text box.
		driver.findElement(By.id("user-message")).clear();
		driver.findElement(By.id("user-message")).sendKeys(inputTxt);

		//6. Click “Get Checked Value”.
		driver.findElement(By.xpath("//button[@id='showInput']")).isDisplayed();
		driver.findElement(By.xpath("//button[@id='showInput']")).click();

		//7. Validate whether the same text message is displayed in the right-hand panel under the “Your Message:” section.
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(7));
		WebElement outputText = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[@id='message']")));
		Assert.assertEquals(inputTxt, outputText.getText(),"Text does not match as Expected:");

	}

	@Test (priority=2)
	public void sliderDemo() {

		//1. Open the https://www.lambdatest.com/selenium-playground page and click “Drag & Drop Sliders” under “Progress Bars & Sliders”.
		driver.findElement(By.linkText("Drag & Drop Sliders")).isDisplayed();
		driver.findElement(By.linkText("Drag & Drop Sliders")).click();

		//2. Select the slider “Default value 15” and drag the bar to make it 95 by validating whether the range value shows 95.
		WebElement slider = driver.findElement(By.xpath("//div[@id='slider3']//input[@type='range']"));
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(slider));
		WebElement output = driver.findElement(By.id("rangeSuccess"));
		Actions actions = new Actions(driver);
		actions.moveToElement(slider).clickAndHold().moveByOffset(215, 0).release().perform();
		Assert.assertEquals(output.getText(), "95", "Slider value did not update correctly.");

	}
	@Test(priority=3)
	public void fromDemo() {

		//1. Open the https://www.lambdatest.com/selenium-playground page and click “Input Form Submit”.
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(7));
		driver.findElement(By.linkText("Input Form Submit")).click();

		//2. Click “Submit” without filling in any information in the form.	
		WebElement submitBttn = driver.findElement(By.xpath("(//button[@type='submit'])[2]"));
		submitBttn.click();

		//3. Assert “Please fill out this field.” error message.
		WebElement companyField = driver.findElement(By.id("company"));
		String validationMessage = 
				(String) ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("return arguments[0].validationMessage;", companyField);
		Assert.assertEquals(validationMessage, "Please fill out this field.");

		//4. Fill in Name, Email, and other fields.
		driver.findElement(By.name("name")).sendKeys("John");
		driver.findElement(By.id("inputEmail4")).sendKeys("johndoe14@mail.com");
		driver.findElement(By.id("inputPassword4")).sendKeys("Password@12345");
		driver.findElement(By.name("company")).sendKeys("ABC Org");
		driver.findElement(By.xpath("//input[@placeholder='Website']")).sendKeys("www.google.com");

		//5. From the Country drop-down, select “United States” using the text property.
		WebElement Country = driver.findElement(By.name("country"));
		Select countries = new Select(Country);
		countries.selectByVisibleText("United States");

		//6. Fill in all elds and click “Submit”.
		driver.findElement(By.name("city")).sendKeys("us");
		driver.findElement(By.id("inputAddress1")).sendKeys("address 1");
		driver.findElement(By.id("inputAddress2")).sendKeys("address 2");
		driver.findElement(By.xpath("//input[@placeholder='State']")).sendKeys("united state");
		driver.findElement(By.name("zip")).sendKeys("441133");
		submitBttn.click();

		//7. Once submitted, validate the success message “Thanks for contacting us, we will get back to you shortly.” on the screen.
		WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[@class='success-msg hidden']")));
		String expMsg = successMessage.getText();
		Assert.assertEquals("Thanks for contacting us, we will get back to you shortly.",expMsg,"Msg Does not match:");
	}

}
