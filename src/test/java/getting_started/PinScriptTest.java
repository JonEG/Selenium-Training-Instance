package getting_started;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.ScriptKey;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.locators.RelativeLocator;

import io.github.bonigarcia.wdm.WebDriverManager;

public class PinScriptTest {
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
	void testPinnedScripts() {
		String initialUrl = "https://www.selenium.dev/selenium/web/web-form.html";
		
		driver.get(initialUrl);
		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		
		String linkScript = "return document.getElementsByTagName('a')[0];";
		ScriptKey linkKey = js.pin(linkScript);
		
		String firstProvidedArgumentScript = "return arguments[0];";
		ScriptKey firstProvidedArgumentKey = js.pin(firstProvidedArgumentScript);
		
		assertThat(js.getPinnedScripts()).hasSize(2);
		
		WebElement formLink = (WebElement) js.executeScript(linkKey);
		formLink.click();
		
		assertThat(driver.getCurrentUrl()).isNotEqualTo(initialUrl);
		
		String message = "Hello world!";
		String executeScript = (String) js.executeScript(firstProvidedArgumentKey, message);
		assertThat(executeScript).isEqualTo(message);
		
		js.unpin(linkKey);
		assertThat(js.getPinnedScripts()).hasSize(1);
	}
}
