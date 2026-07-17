package getting_started;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ScrollingTest {
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
	void testScrollToBottom() throws InterruptedException {
		driver.get("https://bonigarcia.dev/selenium-webdriver-java/long-page.html");

		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;

		WebElement longText = driver.findElement(By.id("content"));

		assertThat(longText.isDisplayed()).isTrue();

		jsExecutor.executeScript("window.scrollTo({top: document.body.scrollHeight,behavior: 'smooth'});");
	}

	@Test
	void testScrollIntoView() {
		driver.get("https://bonigarcia.dev/selenium-webdriver-java/long-page.html");

		JavascriptExecutor js = (JavascriptExecutor) driver;

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		WebElement lastElement = driver.findElement(By.cssSelector("p:last-child"));
		String script = "arguments[0].scrollIntoView();";

		js.executeScript(script, lastElement);
	}

	@Test
	void testTriggerInfiniteScroll() {
		driver.get("https://bonigarcia.dev/selenium-webdriver-java/infinite-scroll.html");

		JavascriptExecutor js = (JavascriptExecutor) driver;

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		List<WebElement> paragraphs = wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.tagName("p"), 0));

		int initialParagraphsCount = paragraphs.size();

		WebElement lastParagraph = driver.findElement(By.xpath(String.format("//p[%d]", initialParagraphsCount)));
		
		String script = "arguments[0].scrollIntoView();";
		js.executeScript(script, lastParagraph);
		
		wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.tagName("p"), initialParagraphsCount));
	}

}
