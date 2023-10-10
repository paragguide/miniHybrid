package testcases;

import org.testng.annotations.Test;

import core.Page;

import java.sql.ResultSetMetaData;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.annotations.DataProvider;

public class AmazonLoginTest extends Page
{
	
	public static boolean b = true;
	
	//public static WebDriver driver = null;
	
  @Test(dataProvider = "dp")
  public void test(String i, String p)
  {
	  //driver = super.driver;
	  
	  Page.signin.click();
		
		Page.auid.sendKeys(i);
		btn1.click();
		 try {
	String msg1 =	Page.err1.getText();
	System.out.println(msg1);
	b = false;
	driver.quit();
		 }
		 catch(Exception e)
		 {
			 System.out.println("userid is valid..");
			 
			 Page.pwd.sendKeys(p);
			 Page.btn2.click();
			    try {
		String msg2 =	 Page.err2.getText();
		System.out.println(msg2);
		b = false;
		
		driver.quit();
			    }
			    catch(Exception e2)
			    {
			    	System.out.println("password is right");
			    	b = true;
			    	
			    	log.debug("loggedin");
			    }
		 }
	
  }

  @DataProvider
  public Object[][] dp() throws Exception
  {
	  Object data[][]= {};  // empty array
	  if(rs != null)
	  {
	  ResultSetMetaData rsmt=rs.getMetaData();
	  int columncount=rsmt.getColumnCount();

	  rs.last(); // place record pointer on last record
	  int rowcount=rs.getRow(); // fetch position of last record

	  System.out.println(columncount+" , "+rowcount);
	  rs.beforeFirst(); // reset

	  data = new Object[rowcount][columncount]; //-> size of array 
	  			
	  for(int rowNum = 1 ; rowNum <= rowcount ; rowNum++)
	     { 
	  				
	  for(int colNum=1 ; colNum<= columncount; colNum++)
	        {
	                   rs.absolute(rowNum); // point to row  
	  	Object data1= rs.getObject(colNum); // getting values from excel
	  	
	  		data[rowNum-1][colNum-1]= data1 ; //adding table data in  array , array starts from 0
	  				}
	  			}
	  }
	  
	 
	  return data;

  }
}
