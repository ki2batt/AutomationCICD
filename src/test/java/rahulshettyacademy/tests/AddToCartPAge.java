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
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import rahulshettyacademy.pageobjects.LandingPage;

public class AddToCartPAge {
	//new comments are added
	public WebDriver driver;

	@Test
	public void addToCartTest() throws InterruptedException {
		String productName = "ADIDAS ORIGINAL";
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		driver.get("https://rahulshettyacademy.com/client"); 
		LandingPage landingPage = new LandingPage(driver);
																
		driver.findElement(By.id("userEmail")).sendKeys("copowis711@intady.com");
																
		driver.findElement(By.id("userPassword")).sendKeys("tTesting!128^");
																 
		driver.findElement(By.id("login")).click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class, 'mb-3')]")));




		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[@class='card']")));
														
		 List<WebElement> products =  driver.findElements(By.xpath("//div[contains(@class, 'mb-3')]")); 
		 WebElement prod =  products.stream().filter(product-> product.findElement(By.xpath(".//div[@class='card-body']//b")).getText().equals(productName)).findFirst().orElse(null);
		 prod.findElement(By.xpath(".//div[@class='card-body']/button[2]")).click();
		 
		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
		 //ng-animating
		 wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));
		 driver.findElement(By.cssSelector("[routerlink*='cart']")).click();
		 
		List<WebElement> cartProducts = driver.findElements(By.cssSelector(".cartSection h3"));	
		Boolean match = cartProducts.stream().anyMatch(cartProduct-> cartProduct.getText().equalsIgnoreCase(productName));
		Assert.assertTrue(match);
		driver.findElement(By.cssSelector(".totalRow button")).click();
		
		
		Actions a = new Actions(driver);
		a.sendKeys(driver.findElement(By.cssSelector("[placeholder='Select Country']")), "india").build().perform();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-results")));
		driver.findElement(By.cssSelector(".ta-item:nth-of-type(2)")).click();
		driver.findElement(By.cssSelector(".action__submit")).click();
		
		String confirmMessage = driver.findElement(By.cssSelector(".hero-primary")).getText();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
		driver.close();
		

	 }
	}


