package com.stepDefination;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class ap_SmokeTest {

	FileInputStream fis=null;
	static Properties Or=null;
	
	WebDriver wd;
	public String newName="TestuserA";
	
//Login to the Site with valid Username and Password

@Given("^Open firefox browser and start the application$")
public void open_firefox_browser_and_start_the_application() throws Throwable {
	
	fis =new FileInputStream(System.getProperty("user.dir")+"/src/test/resources/locators/config.properties");
	Or=new Properties();
	Or.load(fis);
	if(Or.getProperty("browser").equalsIgnoreCase("firefox")){
	System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+"/drivers/geckodriver.exe");
	wd =new FirefoxDriver();
	}else{
		
		System.setProperty("webdriver.gecko.driver",System.getProperty("user.dir")+"/drivers/chromedriver.exe");
		wd =new ChromeDriver();
	}
	wd.manage().window().maximize();
	wd.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	wd.get(Or.getProperty("url"));
	
	
    }

@When("^I enter the Username and Password$")
public void i_enter_the_Username_and_Password() throws Throwable {
	wd.findElement(By.xpath("//a[@class='login']")).click();
	wd.findElement(By.xpath("//input[@id='email']")).sendKeys(Or.getProperty("username"));
	wd.findElement(By.xpath("//input[@id='passwd']")).sendKeys(Or.getProperty("password"));
}

@Then("^User shoud be able to Login successfully$")
public void user_shoud_be_able_to_Login_successfully() throws Throwable {
	wd.findElement(By.xpath("//button[@id='SubmitLogin']")).click();}

//Scenario1 : Ordet the T-shirts and verify the Order Confirmation

@Given("^Click T-shirts button and Select the size$")
public void click_T_shirts_button_and_Select_the_size() throws Throwable {
	
	wd.findElement(By.xpath(".//*[@id='block_top_menu']/ul/li[3]/a")).click();
	wd.findElement(By.id("layered_id_attribute_group_1")).click();
   

}

@When("^Proceed to checkout and navigate to Order summary page$")
public void proceed_to_checkout_and_navigate_to_Order_summary_page() throws Throwable {
	
	wd.findElement(By.xpath(".//*[@id='center_column']/ul/li/div/div[2]/span/span")).click();
	wd.findElement(By.xpath("//*[contains(text(),'Add to cart')]")).click();
	WebDriverWait wt =new WebDriverWait(wd,30);
	wt.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@id='layer_cart']//*[contains(text(),'Proceed to checkout')]"))).click();
	
	//wd.findElement(By.xpath(".//*[@id='layer_cart']//*[contains(text(),'Proceed to checkout')]")).click();;
	
	wd.findElement(By.xpath(".//*[@id='center_column']//*[contains(text(),'Proceed to checkout')]")).click();
	 
	wd.findElement(By.xpath("//button[@name='processAddress']")).click();
	wd.findElement(By.xpath(".//*[@id='cgv']")).click();
	wd.findElement(By.xpath("//button[@name='processCarrier']")).click();
	wd.findElement(By.xpath(".//*[@id='HOOK_PAYMENT']//*[contains(@title,'Pay by check.')]")).click();
	
		
    
}

@When("^click I confirm My Order button$")
public void click_I_confirm_My_Order_button() throws Throwable {
   
	wd.findElement(By.xpath("//button[@type='submit']/span[text()='I confirm my order']")).click();
}

@Then("^Order confirmation should be done$")
public void order_confirmation_should_be_done() throws Throwable {
	
	boolean status= wd.findElement(By.xpath("//p[@class='alert alert-success']")).isDisplayed();
	if(status){
		
		System.out.println("Order is confirmed");
	}else{
		
		System.out.println("Order is not completed");
	}
	wd.findElement(By.xpath("//a[@class='logout']")).click();
	wd.quit();
	
    }

//Scenario2: Modify the Personal Information in My Account Page



@Given("^Navigate to My Account and Click on Personal Information$")
public void navigate_to_My_Account_and_Click_on_Personal_Information() throws Throwable {
	
	wd.findElement(By.xpath("//a[@title='Manage my personal information']")).click();
	String Fname_beforeModify= wd.findElement(By.xpath("//input[@id='firstname']")).getText();
	System.out.println(Fname_beforeModify);
	
   }

@When("^Modify the First Name$")
public void modify_the_First_Name() throws Throwable {
	
	wd.findElement(By.xpath("//input[@id='firstname']")).click();
	Thread.sleep(2000);
	Robot robot=new Robot();
	
	robot.keyPress(KeyEvent.VK_CONTROL);
	robot.keyPress(KeyEvent.VK_A);
	robot.setAutoDelay(2000);
	robot.keyRelease(KeyEvent.VK_A);
	robot.keyRelease(KeyEvent.VK_CONTROL);
	robot.setAutoDelay(2000);
	robot.keyPress(KeyEvent.VK_DELETE); 
	robot.keyRelease(KeyEvent.VK_DELETE); 
	robot.setAutoDelay(2000);
	wd.findElement(By.xpath("//input[@id='firstname']")).sendKeys(newName);
}

@When("^click Save button$")
public void click_Save_button() throws Throwable {
    
	wd.findElement(By.xpath("//input[@id='old_passwd']")).sendKeys(Or.getProperty("password"));
	wd.findElement(By.xpath("//button[@name='submitIdentity']")).click();
	
}

@Then("^Personal Information should be change$")
public void personal_Information_should_be_change() throws Throwable {
	
	String confirmation=wd.findElement(By.xpath("//p[@class='alert alert-success']")).getText();
	System.out.println(confirmation);
	Thread.sleep(2000);
	wd.findElement(By.xpath("//a[@title='Manage my personal information']")).click();
	Thread.sleep(4000);
	String afterFnameModify= wd.findElement(By.xpath("//input[@id='firstname']")).getAttribute("value");
	System.out.println(afterFnameModify);
	if(afterFnameModify.equalsIgnoreCase(newName)){
		
		System.out.println("First Name is changed successfully");
	}else{
		
		System.out.println("First Name is not changed");
		
	}
    
	wd.findElement(By.xpath("//a[@class='logout']")).click();
	wd.quit();
	
}



}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	