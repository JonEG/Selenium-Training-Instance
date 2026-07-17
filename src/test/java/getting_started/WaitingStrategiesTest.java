package getting_started;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class WaitingStrategiesTest {
	private WebDriver driver;

	@BeforeAll
	static void setupClass() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	void setup() {
		driver = new ChromeDriver();
	}

	@AfterEach
	void teardown() {
		driver.quit();
	}

	@Test
	void testImplicitWait() {
		driver.get("https://bonigarcia.dev/selenium-webdriver-java/loading-images.html");
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		WebElement landscape = driver.findElement(By.id("landscape"));

		assertThat(landscape.getAttribute("src")).containsIgnoringCase("landscape");
	}

	@Test
	void testExplicitWait() {
		driver.get("https://bonigarcia.dev/selenium-webdriver-java/loading-images.html");
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement landscape = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("landscape")));

		assertThat(landscape.getAttribute("src")).containsIgnoringCase("landscape");
	}

	/**
	 * Example of explicit waiting strategy
	 */
	@Test
	void testSlowCalculator() {
		driver.get("https://bonigarcia.dev/selenium-webdriver-java/slow-calculator.html");
		// 1 + 1
		driver.findElement(By.xpath("//span[text()='1']")).click();
		driver.findElement(By.xpath("//span[text()='+']")).click();
		driver.findElement(By.xpath("//span[text()='1']")).click();
		driver.findElement(By.xpath("//span[text()='=']")).click();
		// ... should be 2, wait for it
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(6));
		wait.until(ExpectedConditions.textToBe(By.className("screen"), "2"));
	}

	/**
	 * Example of fluent waiting strategy
	 */
	@Test
	void testFluentWait() {
		driver.get("https://bonigarcia.dev/selenium-webdriver-java/loading-images.html");
		
		Wait<WebDriver> wait = new FluentWait<>(driver)
				.withTimeout(Duration.ofSeconds(10))
				.pollingEvery(Duration.ofSeconds(1))
				.ignoring(NoSuchElementException.class);
		
		WebElement landscape = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("landscape")));
		assertThat(landscape.getAttribute("src")).containsIgnoringCase("landscape");
	}
}
