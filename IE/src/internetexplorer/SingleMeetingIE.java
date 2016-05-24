package internetexplorer;

import static org.testng.Assert.fail;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.thoughtworks.selenium.webdriven.WebDriverBackedSelenium;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


public class SingleMeetingIE {
	
	  private WebDriver driver;
	  private String baseUrl;
	  public static Logger logger = Logger.getLogger(SingleMeetingIE.class);
	  
	  
	  
  @BeforeClass(alwaysRun = true)
  public void setUp() throws Exception {
    	PropertyConfigurator.configure("log4j.properties");
    	
    	System.setProperty("webdriver.ie.driver","C:\\IEDriverServer.exe");
        driver = new InternetExplorerDriver();
        baseUrl = "https://10.77.197.240/";
        
	    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	   
  }
  
 @Test  
public void open() throws Exception {
	
	  driver.get(baseUrl + "/tmsagent/tmsportal/#home");
	 
	  //now we are going to use javascipt ,This will click on "Continue to this website (not recommended)" text and will //push us to the page
	  driver.navigate().to("javascript:document.getElementById('overridelink').click()");
	  Thread.sleep(1000);
	  
	  Alert alert = driver.switchTo().alert();
	
        
	    
	        Robot robot = new Robot();
	        alert.sendKeys("administrator");

	        robot.keyPress(KeyEvent.VK_TAB);//go to password feild
        
	        
	        robot.keyPress(KeyEvent.VK_C);
	        robot.keyPress(KeyEvent.VK_I);
	        robot.keyPress(KeyEvent.VK_S);
	        robot.keyPress(KeyEvent.VK_C);
	        robot.keyPress(KeyEvent.VK_O);
	        robot.keyPress(KeyEvent.VK_SHIFT);
	        robot.keyPress(KeyEvent.VK_2);
	        robot.keyRelease(KeyEvent.VK_2);
	        robot.keyRelease(KeyEvent.VK_SHIFT);
	        robot.keyPress(KeyEvent.VK_1);
	        robot.keyPress(KeyEvent.VK_2);
	        robot.keyPress(KeyEvent.VK_3);
   
	        alert.accept();
         
	      	    
	    
	  WebElement ele = driver.findElement(By.xpath("/html/body/div[1]/div/ul/li[2]/a/span"));
	  String str = ele.getText();
			  
	  String atitle = "TelePresence User Portal: 10.77.197.240";
	  String btitle = "TelePresence User Portal";
	  String ctitle = "The TMS User Portal is currently unavailable. Please try again later."; 
	
	  //get page title and store in btitle
	  String dtitle = driver.getTitle();	
	  System.out.println(dtitle);
	  
	  WebElement element = driver.findElement(By.xpath("html/body/div"));
	  String strng = element.getText();	
   
	  if (dtitle.equals(atitle))
	  {	  	  
         create();    
         edit();
         delete();
      }	  
	  else if (dtitle.equals(btitle))
	  {
		  create();    
	      edit();
	      delete();
	  }
	  else if (ctitle.equals(strng))
	  {
		 logger.error("Server is restarting");
		 org.testng.Assert.fail("Fail");
		 driver.quit();
	  }
	  else  
      {	  
	     logger.error("Server not accessible");
	     org.testng.Assert.fail("Fail");
	     driver.quit();
      }	  
  }

 

  public void create() throws InterruptedException {
	  System.out.println("enter");
  try{	
	  //click on open smart scheduler
	  driver.findElement(By.linkText("Open Smart Scheduler")).click();
      Thread.sleep(1000);
      //click on new meeting
      driver.findElement(By.xpath("//div[3]/div/div/div/div/div[1]/div[1]/h2/button")).click();
	  Thread.sleep(1000);
	    
	  //select participants
      driver.findElement(By.xpath("//*[@id='SchedEdit-ParticipantsCollapse']/div/div/div/div[2]/div[1]/div/div/div[1]/input")).click();
      driver.findElement(By.name("search")).click();
      driver.findElement(By.name("search")).clear();
	  driver.findElement(By.name("search")).sendKeys("a");
	  Thread.sleep(1000);
	  driver.findElement(By.xpath("//div[@id='SchedEdit-ParticipantsCollapse']/div/div/div/div[2]/div/div/div/div[2]/div/a/span[2]")).click();
	  Thread.sleep(1000);
	  //select dial-in participant
	  driver.findElement(By.xpath(" //*[@id='SchedEdit-ParticipantsCollapse']/div/div/div/div[2]/div[1]/div/div/div[1]/div/button")).click();
	  Thread.sleep(1000);
	  driver.findElement(By.xpath("//*[@id='SchedEdit-ParticipantsCollapse']/div/div/div/div[2]/div[1]/div/div/div[1]/div/ul/li[1]/a")).click();  
      
	  //change title name
	  driver.findElement(By.xpath("//*[@id='title']")).click();
	  Thread.sleep(1000);
	  driver.findElement(By.xpath("//*[@id='title']")).clear();
	  Thread.sleep(1000);
	  driver.findElement(By.xpath("//*[@id='title']")).sendKeys("single meeting");
	  
	  //select start time
	  driver.findElement(By.xpath("html/body/div[3]/div/div/div[2]/div[1]/div/div/div/div[2]/div/div[2]/div[2]/div[2]/input")).click();
      driver.findElement(By.xpath("html/body/ul/li[81]")).click();
	  
	  //click save button
	  driver.findElement(By.xpath("html/body/div[3]/div/div/div[3]/div/button[1]")).click();    
	  logger.info("Meeting scheduled successfully");
	  
    }
	catch (Exception e1){
		   logger.error("Meeting not scheduled");
	//	   org.testng.Assert.fail("Fail");
		   driver.close();
	}
	  
	  //back to home page
	  driver.navigate().back();
	  Thread.sleep(1000);
    
  }


  
  
  public void edit() throws InterruptedException { 
  try{
	  //click on open smart scheduler
	  driver.findElement(By.linkText("Open Smart Scheduler")).click();
      Thread.sleep(1000);
      
      //click on new meeting
      driver.findElement(By.xpath("html/body/div[3]/div/div/div/div/div[1]/div[2]/div/div[2]/div/div/a/div[2]/div")).click();
	  Thread.sleep(1000);
	    
	  //remove and add participants
	  driver.findElement(By.xpath("//*[@id='SchedEdit-ParticipantsCollapse']/div/div/div/div[2]/div[2]/div/div[1]/div/div[1]/button")).click();
	  Thread.sleep(1000);
	  driver.findElement(By.xpath(" //*[@id='SchedEdit-ParticipantsCollapse']/div/div/div/div[2]/div[1]/div/div/div[1]/div/button")).click();
	  Thread.sleep(1000);
	  driver.findElement(By.xpath("//*[@id='SchedEdit-ParticipantsCollapse']/div/div/div/div[2]/div[1]/div/div/div[1]/div/ul/li[2]/a")).click();
	  Thread.sleep(1000);	
	  
	  //click save button
	  driver.findElement(By.xpath("html/body/div[3]/div/div/div[3]/div/button[1]")).click();    
	  logger.info("Meeting edited successfully");
	  
    }
	catch (Exception e2){
		   logger.error("Meeting not edited");
		   org.testng.Assert.fail("Fail");
		   driver.quit();
	}
	  //back to home page
	  driver.navigate().back();
	  Thread.sleep(1000);
    
  }

  
  
  
  public void delete() throws Exception {
  try{
	    driver.get(baseUrl + "/tmsagent/tmsportal/#home");
	    driver.manage().window().maximize();
		 
	      
	    driver.findElement(By.linkText("Open Smart Scheduler")).click();
	    Thread.sleep(1000);
	    driver.findElement(By.xpath("html/body/div[3]/div/div/div/div/div[1]/div[2]/div/div[2]/div/div/a/div[2]/div")).click();
	    
	    driver.findElement(By.xpath("html/body/div[3]/div/div/div[3]/div/button[3]")).click();
	    
	    driver.findElement(By.xpath("html/body/div[3]/div/div/div[3]/div/div/div[2]/div/div/div[1]/button")).click();
	    
	    logger.info("Meeting deleted successfully");
	    
    }
	catch (Exception e3){
		   logger.error("Meeting not deleted");
		   org.testng.Assert.fail("Fail");
		   driver.quit();
	}	 
  
	    //back to home page
		driver.navigate().back();
		Thread.sleep(1000);
    
}
  
  
  
  
  @AfterClass(alwaysRun = true)
  public void tearDown() throws Exception {
	  Thread.sleep(5000);
      driver.quit();
 }
  
}
