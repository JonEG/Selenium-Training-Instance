package getting_started;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class NavigationTargetTest {
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
	void testNewTab() {
		driver.get("https://bonigarcia.dev/selenium-webdriver-java/");
		String windowHandle = driver.getWindowHandle();

		driver.switchTo().newWindow(WindowType.TAB).get("https://bonigarcia.dev/selenium-webdriver-java/web-form.html");

		assertThat(driver.getWindowHandles().size()).isEqualTo(2);

		driver.switchTo().window(windowHandle);
		driver.close();
		assertThat(driver.getWindowHandles().size()).isEqualTo(1);
	}

	@Test
	void testNewWindow() {
		driver.get("https://bonigarcia.dev/selenium-webdriver-java/");
		String windowHandle = driver.getWindowHandle();

		driver.switchTo().newWindow(WindowType.WINDOW)
				.get("https://bonigarcia.dev/selenium-webdriver-java/web-form.html");

		assertThat(driver.getWindowHandles().size()).isEqualTo(2);

		driver.switchTo().window(windowHandle);
		driver.close();
		assertThat(driver.getWindowHandles().size()).isEqualTo(1);
	}

	@Test
	void testIFrames() {
		driver.get("https://bonigarcia.dev/selenium-webdriver-java/iframes.html");

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("my-iframe"));

		By pName = By.tagName("p");
		wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(pName, 0));

		List<WebElement> paragraphs = driver.findElements(pName);
		assertThat(paragraphs).hasSize(20);
	}

	@Test
	void testFrames() {
		driver.get("https://bonigarcia.dev/selenium-webdriver-java/frames.html");

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		String frameName = "frame-body";
		wait.until(ExpectedConditions.presenceOfElementLocated(By.name(frameName)));
		driver.switchTo().frame(frameName);

		By pName = By.tagName("p");
		wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(pName, 0));

		List<WebElement> paragraphs = driver.findElements(pName);
		assertThat(paragraphs).hasSize(20);
	}

	@Test
	void testAlert() {
		driver.get("https://bonigarcia.dev/selenium-webdriver-java/dialog-boxes.html");
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		driver.findElement(By.id("my-alert")).click();
		wait.until(ExpectedConditions.alertIsPresent());
		Alert alert = driver.switchTo().alert();
		assertThat(alert.getText()).isEqualTo("Hello world!");
		alert.accept();
	}

	@Test
	void testConfirm() {
		driver.get("https://bonigarcia.dev/selenium-webdriver-java/dialog-boxes.html");
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		driver.findElement(By.id("my-confirm")).click();
		wait.until(ExpectedConditions.alertIsPresent());
		Alert confirm = driver.switchTo().alert();
		assertThat(confirm.getText()).isEqualTo("Is this correct?");
		confirm.dismiss();
	}

	@Test
	void testPrompt() {
		driver.get("https://bonigarcia.dev/selenium-webdriver-java/dialog-boxes.html");
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		driver.findElement(By.id("my-prompt")).click();
		wait.until(ExpectedConditions.alertIsPresent());
		Alert prompt = driver.switchTo().alert();
		prompt.sendKeys("John Doe");
		assertThat(prompt.getText()).isEqualTo("Please enter your name");
		prompt.accept();
	}

	@Test
	void testModal() {
		driver.get("https://bonigarcia.dev/selenium-webdriver-java/dialog-boxes.html");
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		driver.findElement(By.id("my-modal")).click();
		WebElement close = driver.findElement(By.xpath("//button[text() = 'Close']"));
		assertThat(close.getTagName()).isEqualTo("button");
		wait.until(ExpectedConditions.elementToBeClickable(close));
		close.click();
	}
}