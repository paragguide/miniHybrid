package testcases;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.testng.annotations.Test;

import core.Page;

public class AmazonSearch 
{
	
  @Test
  public void searchtest() 
  {
	  if(AmazonLoginTest.b == true)
	   {
	Page.srchbox.sendKeys("Sun Glasses");
	
	Actions a = new Actions(AmazonLoginTest.driver);
	a.sendKeys(Keys.ENTER).perform();
	
	Page.log.debug("search done");
	   }
	  else
	  {
		  System.out.println("not logged in.");
	  }
  }
}
