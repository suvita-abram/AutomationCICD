package rahulshettyacademy.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import rahulshettyacademy.AbstractComponents.AbstractComponent;

public class ProductCatalogue extends AbstractComponent {
	WebDriver driver;
	
	public ProductCatalogue(WebDriver driver)
	{
		super(driver);
		//Constructor for Initialization. Constructor must take the same class name.
		this.driver = driver;
		PageFactory.initElements(driver,this);
	}
	
	/*The following step is not required if page factory is used.
	 * WebElement userEmail = driver.findElement(By.id("userEmail"));*/
	
	@FindBy (css=".mb-3")
	List<WebElement> products;
	
	@FindBy (css=".ng-animating")
	WebElement spinner;
	
	By productsBy = By.cssSelector(".mb-3");
	By addToCart = By.cssSelector(".card-body button:last-of-type");
	By toastMessage = By.cssSelector("#toast-container");

	
	public List<WebElement> getProductList() throws InterruptedException
	{
		waitForElementToAppear(productsBy);
		return products;
	}
	
	public WebElement getProductByName(String productName) throws InterruptedException
	{
		WebElement prod = getProductList().stream().filter(product->product.findElement(By.cssSelector("b"))
				.getText().equals(productName)).findFirst().orElse(null);
		return prod;
		
	}
	
	public void addProductToCart(String productName) throws InterruptedException
	{
		WebElement prod= getProductByName(productName);
		prod.findElement(addToCart).click();
		waitForElementToAppear(toastMessage);
		waitForElementDisappear(spinner);
		//The thread.sleep is added following failures during the lecture 181
		//Thread.sleep(2000);
	}

}
