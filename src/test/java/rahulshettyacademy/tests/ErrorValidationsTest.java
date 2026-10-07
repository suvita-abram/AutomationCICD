package rahulshettyacademy.tests;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.IRetryAnalyzer;
import org.testng.annotations.Test;

import com.sun.net.httpserver.Authenticator.Retry;

import rahulshettyacademy.TestComponents.BaseTest;
import rahulshettyacademy.pageobjects.CartPage;
import rahulshettyacademy.pageobjects.ProductCatalogue;

public class ErrorValidationsTest extends BaseTest {
	
	@Test(groups= {"ErrorHandling"}, retryAnalyzer=rahulshettyacademy.TestComponents.Retry.class)
	public void LoginErrorValidation () throws IOException{
		
		//String productName = "ZARA COAT 3";
		landingPage.loginApplication("tobbs@gmail.com", "Shivaay@0707");
		 Assert.assertEquals("Incorrect email or password.", landingPage.getErrorMessage());
	}
	
	@Test
	public void ProductErrorValidation () throws InterruptedException {
	
		String productName = "ZARA COAT 3";	
		ProductCatalogue productCatalogue = landingPage.loginApplication("shivaay@gmail.com", "Shivaay@0707");
	    List<WebElement> products = productCatalogue.getProductList();
		productCatalogue.addProductToCart(productName);
		CartPage cartPage = productCatalogue.goToCartPage();
		Boolean match = cartPage.VerifyProductDisplay(productName);
		Assert.assertTrue(match);
	}
}
		