package getting_started;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.commons.lang3.SystemUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class CopyAndPasteTest {
	private WebDriver driver;

	@BeforeAll
	static void setupClass() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	void setup() {
		driver = new ChromeDriver();
		driver.get("https://www.selenium.dev/selenium/web/web-form.html");
	}

	@AfterEach
	void teardown() {
		driver.quit();
	}

	@Test
	void testCopyAndPaste() throws InterruptedException {
		WebElement textInput = driver.findElement(By.id("my-text-id"));
		
		Actions actions = new Actions(driver);
		
		actions
		.click(textInput)
		.sendKeys("hello world!")
		.keyDown(SystemUtils.IS_OS_MAC ? Keys.COMMAND : Keys.CONTROL)
		.sendKeys("a")
		.sendKeys("c").build().perform();
		
		WebElement textAreaInput = driver.findElement(By.name("my-textarea"));
		
		actions
		.click(textAreaInput)
		.keyDown(SystemUtils.IS_OS_MAC ? Keys.COMMAND : Keys.CONTROL)
		.sendKeys("v").build().perform();
		
		assertThat(textInput.getAttribute("value"))
		.isEqualTo(textAreaInput.getAttribute("value"));
	}
}
