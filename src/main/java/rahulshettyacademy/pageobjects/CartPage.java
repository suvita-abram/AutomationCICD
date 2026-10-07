package rahulshettyacademy.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import rahulshettyacademy.AbstractComponents.AbstractComponent;

public class CartPage extends AbstractComponent{

	WebDriver driver;
	
	@FindBy (xpath="//*[text()='Checkout']")
	WebElement checkoutEle;
	
	@FindBy (css=".cartSection h3")
	List<WebElement> cartProducts;
	
	public CartPage(WebDriver driver)
	{
		super(driver);
		//Constructor for Initialization. Constructor must take the same class name.
		this.driver = driver;
		PageFactory.initElements(driver,this);
	}
	
	public Boolean VerifyProductDisplay(String productName)
	{
		Boolean match = cartProducts.stream().anyMatch(cartProduct->cartProduct.getText().equalsIgnoreCase(productName));
		return match;
	}
	
	public CheckoutPage goToCheckout()
	{
		checkoutEle.click();
		CheckoutPage checkoutPage = new CheckoutPage(driver);
		return checkoutPage;
		
	}
	
}
