package rahulshettyacademy.tests;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.github.bonigarcia.wdm.WebDriverManager;

public class StandAloneTest {

	public static void main(String[] args) {
		// e-cart real-life scenario.
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		/*Step 1: Login to the website.
		 * Gobally add implicit wait so that the sync issues are covered.*/
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		//declare the product name for easy coding
		String productName = "ZARA COAT 3";
		
		 // 1.1. Access URL
		driver.get("https://rahulshettyacademy.com/client/");
		//1.2. Enter email and password
		driver.findElement(By.id("userEmail")).sendKeys("shivaay@gmail.com");
		driver.findElement(By.id("userPassword")).sendKeys("Shivaay@0707");
		driver.findElement(By.id("login")).click();
		/*Modified to add ADIDAS
		/*Add explicit wait to load the page
		 * Provide the WebDriverWait object creation step here...*/
		
		//wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));
		
		/*2. Add items to cart
		 * 2.1. Grab all available products in a list.*/
		List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));
		
		WebElement prod = products.stream().filter(product->product.findElement(By.cssSelector("b"))
				.getText().equals(productName)).findFirst().orElse(null);
		prod.findElement(By.cssSelector(".card-body button:last-of-type")).click();
		
		/*3. Verifying the products are added to card
		 * i. Loading icon should disappear,
		 * ii. Toast message, "Product Added To Cart" should be displayed
		 * Add explicit Wait
		 * i. create object of WebDriverWait*/	
		/*Incase the implicit wait is not provided at the top, to avoid failing the code while loading the pages, add explicit wait.
		 * then, the creation of the object of the WebDriverWait is to be provided globally, right after login.*/
		
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
		wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));
		
		
		//Adding one more product, LAPTOP to cart for the lecture 159.
		
		//4. Click on Cart
		//Thread.sleep(2000);
		driver.findElement(By.cssSelector("[routerlink*='cart']")).click();
		
		//5. Fetch all items in 'My Cart'
		List <WebElement> cartProducts = driver.findElements(By.cssSelector(".cartSection h3"));
		
		//6. Verify whether the desired product is present in 'My Cart'
		Boolean match = cartProducts.stream().anyMatch(cartProduct->cartProduct.getText().equalsIgnoreCase(productName));
		Assert.assertTrue(match);
		
		//7. Click on "Checkout" button.
		driver.findElement(By.xpath("//*[text()='Checkout']")).click();
		
		//8. Fill details in checkout page
		//Actions class is used here.
		Actions a = new Actions(driver);
		a.sendKeys(driver.findElement(By.cssSelector("[placeholder='Select Country']")), "india").build().perform();
		/*A delay of 1 or 2 s is taken to display the auto suggestive drop-down list to display. 
		 * Adding explicit wait for this*/
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".ta-results")));
		
		//Select the country
		driver.findElement(By.xpath("//button[contains(@class, 'ta-item')][2]")).click();
		
		//9. Click on 'Place Order'
		//In lecture the css locator used is: ".action__submit"
		driver.findElement(By.cssSelector("a[class*='ng-star-inserted']")).click();
		
		/*10. Validate using assertions whether the title of the 'Thankyou page', "THANKYOU FOR THE ORDER." is displayed.
		 * In the HTML script the message is " Thankyou for the order.", to use this is an issue as the assertion fails caused due to cased sensitivity.
		 * To overcome this issue, use ignore cases otherwise the code fails as Se looks at what is written in the page and not what is coded.*/
		String comfirmMessage = driver.findElement(By.cssSelector(".hero-primary")).getText();
		Assert.assertTrue(comfirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER.")) ;
		
		//11. Finally close the driver.
		driver.close();
		
	}

}
