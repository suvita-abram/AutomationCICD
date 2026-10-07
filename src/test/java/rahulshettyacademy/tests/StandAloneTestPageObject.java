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

public class StandAloneTestPageObject extends BaseTest {
	
	//The following global declaration of product is no longer required with data provider:
	 String productName = "ZARA COAT 3";
	
	
	@Test (dataProvider="getData", groups= {"Purchase"})
	public void submitOrder (HashMap<String,String> input) throws IOException, InterruptedException{
		// e-cart real-life scenario.
		/*The following syntaxes are coded in "Base Test"
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		 *Step 1: Login to the website.
		 * Gobally add implicit wait so that the sync issues are covered.
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));*/
		
		//declare the product name for easy coding
		
		/*The syntax is coded in "BaseTest"
		 * Lecture 163: Page Object: 
		 * 1. Creating an object of LandingPage to send driver as an argument
		LandingPage landingPage = new LandingPage(driver);*/
		
		 /*The syntax is coded in "BaseTest"
		  *  2. Accessing URL
		  * The following code is replaced by "goTo" actions class 
		  * driver.get("https://rahulshettyacademy.com/client/");
		landingPage.goTo();*/
		
		/*2. Call actions method using the object of LandingPage class and send username and passwords as arguments.
		 * Note: Page objects should not hold any data.
		 * It focuses only on elements and actions
		 * All data should come from test.*/
		
		//Accessing BaseTest code:
		/*The following code has been made generic using @BeforeMethod annotation on the parent class "BaseTest"
		 * LandingPage landingPage = launchApplication();*/
		
		ProductCatalogue productCatalogue = landingPage.loginApplication(input.get("email"), input.get("password"));
		/*The following 3 steps has been replaced using actions method.
		 * 1.2. Enter email and password
		driver.findElement(By.id("userEmail")).sendKeys("shivaay@gmail.com");
		driver.findElement(By.id("userPassword")).sendKeys("Shivaay@0707");
		driver.findElement(By.id("login")).click();*/
		
		
		
		/*Modified to add ADIDAS
		*Add explicit wait to load the page
		 * Provide the WebDriverWait object creation step here...*/
		
		//wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));
		
		/*Page Object method:
		 * Create a new object for ProductCatalogue*/
		//NOT REQUIRED, object created in Landing Page: ProductCatalogue productCatalogue = new ProductCatalogue(driver);
		List<WebElement> products = productCatalogue.getProductList();
		
		/*2. Add items to cart
		 * 2.1. Grab all available products in a list.
		 * The following code is not required here as it has been replaced with the Page Object method:
		List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));*/
		
		//Page object method of adding products to cart:
		productCatalogue.addProductToCart(input.get("product"));
		/*The following syntaxes are used in the Product Catalogue as part of page objects:
		 * WebElement prod = products.stream().filter(product->product.findElement(By.cssSelector("b"))
				.getText().equals(productName)).findFirst().orElse(null);
		prod.findElement(By.cssSelector(".card-body button:last-of-type")).click();*/
		
		/*3. Verifying the products are added to card
		 * i. Loading icon should disappear,
		 * ii. Toast message, "Product Added To Cart" should be displayed
		 * Add explicit Wait
		 * i. create object of WebDriverWait*/	
		/*Incase the implicit wait is not provided at the top, to avoid failing the code while loading the pages, add explicit wait.
		 * then, the creation of the object of the WebDriverWait is to be provided globally, right after login.*/
		
		//WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(5));
		/*Visibility wait has been coded in the AbstractComponent class:
		 * wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
		wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));*/
		
		
		//Adding one more product, LAPTOP to cart for the lecture 159.
		
		//4. Click on Cart
		/*The following code:
		driver.findElement(By.cssSelector("[routerlink*='cart']")).click();
		is replaced as:*/
	    
		CartPage cartPage = productCatalogue.goToCartPage();

		/*Replaced as Page Object:
		//5. Fetch all items in 'My Cart'
		List <WebElement> cartProducts = driver.findElements(By.cssSelector(".cartSection h3"));*/
		
		/*6. Verify whether the desired product is present in 'My Cart'
		Boolean match = cartProducts.stream().anyMatch(cartProduct->cartProduct.getText().equalsIgnoreCase(productName));*/
		Boolean match = cartPage.VerifyProductDisplay(input.get("product"));
		//Assertions should not be done in any of the page object classes.
		Assert.assertTrue(match);
						
		/*Replaced as Page Object:
		7. Click on "Checkout" button.
		driver.findElement(By.xpath("//*[text()='Checkout']")).click();
		
		//8. Fill details in checkout page
		//Actions class is used here.
		Actions a = new Actions(driver);
		a.sendKeys(driver.findElement(By.cssSelector("[placeholder='Select Country']")), "india").build().perform();
		/*A delay of 1 or 2 s is taken to display the auto suggestive drop-down list to display. 
		 * Adding explicit wait for this
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".ta-results")));

		//Select the country
		driver.findElement(By.xpath("//button[contains(@class, 'ta-item')][2]")).click();*/
		
		CheckoutPage checkoutPage = cartPage.goToCheckout();
		checkoutPage.selectCountry("india");
		ConfirmationPage confirmationPage = checkoutPage.submitOrder();
		
		/*Replaced as Page Object:
		//9. Click on 'Place Order'
		//In lecture the css locator used is: ".action__submit"
		driver.findElement(By.cssSelector("a[class*='ng-star-inserted']")).click();
		
		 *10. Validate using assertions whether the title of the 'Thankyou page', "THANKYOU FOR THE ORDER." is displayed.
		 * In the HTML script the message is " Thankyou for the order.", to use this is an issue as the assertion fails caused due to cased sensitivity.
		 * To overcome this issue, use ignore cases otherwise the code fails as Se looks at what is written in the page and not what is coded.
		String comfirmMessage = driver.findElement(By.cssSelector(".hero-primary")).getText();*/
		
		String comfirmMessage = confirmationPage.getConfirmationMessage();
		Assert.assertTrue(comfirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER.")) ;
		
		//11. Finally close the driver.
		/*The following code has been made generic using @BeforeMethod annotation on the parent class "BaseTest"
		 * driver.close();*/
	}
	
	@Test(dependsOnMethods= {"submitOrder"})
	public void OrderHistoryTest()
	{
		ProductCatalogue productCatalogue = landingPage.loginApplication("shivaay@gmail.com", "Shivaay@0707");
		OrderPage orderPage = productCatalogue.goToOrdersPage();
		Assert.assertTrue(orderPage.VerifyOrderDisplay(productName));
		
	}
	
	@DataProvider
	public Object[][] getData()
	{
		/*Data provider method for few datasets:
		return new Object[][] {{"shivaay@gmail.com", "Shivaay@0707", "ZARA COAT 3"},{"tobbili@gmail.com", "Tobbili@1104", "ADIDAS ORIGINAL"}};*/
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("email","shivaay@gmail.com");
		map.put("password", "Shivaay@0707");
		map.put("product", "ZARA COAT 3");
		
		HashMap<String,String> map1 = new HashMap<String,String>();
		map1.put("email","tobbili@gmail.com");
		map1.put("password", "Tobbili@1104");
		map1.put("product", "ADIDAS ORIGINAL");
		
		return new Object[][] {{map},{map1}};
		
	}
	
	
	
}
