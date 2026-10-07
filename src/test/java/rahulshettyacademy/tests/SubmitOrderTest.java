package rahulshettyacademy.tests;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import rahulshettyacademy.TestComponents.BaseTest;
import rahulshettyacademy.pageobjects.CartPage;
import rahulshettyacademy.pageobjects.CheckoutPage;
import rahulshettyacademy.pageobjects.ConfirmationPage;
import rahulshettyacademy.pageobjects.OrderPage;
import rahulshettyacademy.pageobjects.ProductCatalogue;

public class SubmitOrderTest extends BaseTest {
	
	//The following global declaration of product is no longer required with data provider:
	 String productName = "ZARA COAT 3";
	
	
	@Test (dataProvider="getData", groups= {"Purchase"})
	public void submitOrder (HashMap<String,String> input) throws IOException, InterruptedException{
		
		ProductCatalogue productCatalogue = landingPage.loginApplication(input.get("email"), input.get("password"));
		
		List<WebElement> products = productCatalogue.getProductList();
		
		
		productCatalogue.addProductToCart(input.get("product"));
		
		CartPage cartPage = productCatalogue.goToCartPage();

		
		Boolean match = cartPage.VerifyProductDisplay(input.get("product"));
	
		Assert.assertTrue(match);
						
		
		
		CheckoutPage checkoutPage = cartPage.goToCheckout();
		checkoutPage.selectCountry("india");
		ConfirmationPage confirmationPage = checkoutPage.submitOrder();
		
		
		
		String comfirmMessage = confirmationPage.getConfirmationMessage();
		Assert.assertTrue(comfirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER.")) ;
		
		
	}
	
	@Test(dependsOnMethods= {"submitOrder"})
	public void OrderHistoryTest()
	{
		ProductCatalogue productCatalogue = landingPage.loginApplication("shivaay@gmail.com", "Shivaay@0707");
		OrderPage orderPage = productCatalogue.goToOrdersPage();
		Assert.assertTrue(orderPage.VerifyOrderDisplay("ZARA COAT 3"));
		
	}
	
	@DataProvider
	public Object[][] getData() throws IOException
	{
		/*1 Data provider method for few datasets:
		return new Object[][] {{"shivaay@gmail.com", "Shivaay@0707", "ZARA COAT 3"},{"tobbili@gmail.com", "Tobbili@1104", "ADIDAS ORIGINAL"}};*/
		
		/*Data provider method using HashMaps:
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("email","shivaay@gmail.com");
		map.put("password", "Shivaay@0707");
		map.put("product", "ZARA COAT 3");
		
		HashMap<String,String> map1 = new HashMap<String,String>();
		map1.put("email","tobbili@gmail.com");
		map1.put("password", "Tobbili@1104");
		map1.put("product", "ADIDAS ORIGINAL");
		
		return new Object[][] {{map},{map1}};*/
		
		//Data provide method using Json:
		List<HashMap<String, String>> data = getJsonDataToMap(System.getProperty("user.dir")+"/src/test/java/rahulshettyacademy/data/PurchaseOrder.json"); 
		return new Object[][] {{data.get(0)},{data.get(1)}};
		
	}
	
	
	
}
