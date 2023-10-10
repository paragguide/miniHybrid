package testcases;

import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import core.Page;
import java.sql.*;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.annotations.DataProvider;

public class FacebookTest extends Page
{
	@FindBy(xpath = xpath.AllXpath.uid)
	WebElement uid;
	
	@FindBy(xpath = xpath.AllXpath.pd)
	WebElement pd;
	
	@FindBy(xpath = xpath.AllXpath.btn)
	WebElement btn;
	
  @Test(dataProvider = "logindata")
  public void login(String id, String pwd) 
  {
	  uid.clear();
	  uid.sendKeys(id);
	  
	  log.debug("enter uid "+id);
	  test.log(LogStatus.PASS, "enter id "+id);
	  
	  pd.clear();
	  pd.sendKeys(pwd);
	  
	  log.debug("enter pwd "+pwd);
	  test.log(LogStatus.PASS, "enter pwd "+pwd);
	  
	  
	  btn.click();
  }

  @DataProvider
  public Object[][] logindata() throws Exception
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
	  
	  //System.out.println(data);
	  return data;

  }
}
