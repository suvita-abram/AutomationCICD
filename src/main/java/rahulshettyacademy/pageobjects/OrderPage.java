package rahulshettyacademy.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import rahulshettyacademy.AbstractComponents.AbstractComponent;

public class OrderPage extends AbstractComponent{

	WebDriver driver;
	
	/*@FindBy (xpath="//*[text()='Checkout']")
	WebElement checkoutEle;*/
	
	@FindBy (css="tr td:nth-child(3)")
	List<WebElement> orderProducts;
	
	public OrderPage(WebDriver driver)
	{
		super(driver);
		//Constructor for Initialization. Constructor must take the same class name.
		this.driver = driver;
		PageFactory.initElements(driver,this);
	}
	
	public Boolean VerifyOrderDisplay(String productName)
	{
		Boolean match = orderProducts.stream().anyMatch(product->product.getText().equalsIgnoreCase(productName));
		return match;
	}
	
	/* To delete:
	 * public CheckoutPage goToCheckout()
	{
		checkoutEle.click();
		CheckoutPage checkoutPage = new CheckoutPage(driver);
		return checkoutPage;
		
	}*/
	
}
