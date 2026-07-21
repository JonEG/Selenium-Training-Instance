package getting_started;

import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

import java.lang.invoke.MethodHandles;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;

import io.github.bonigarcia.wdm.WebDriverManager;

public class WebStorageTest {
	private WebDriver driver;
	
	static final Logger log = getLogger(MethodHandles.lookup().lookupClass());

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
	void testWebStorage() {
	    driver.get("https://bonigarcia.dev/selenium-webdriver-java/web-storage.html");

	    JavascriptExecutor js = (JavascriptExecutor) driver;

	    Long sessionSize = (Long) js.executeScript("return window.sessionStorage.length;");
	    
	    @SuppressWarnings("unchecked")
		List<String> keys = (List<String>) js.executeScript("return Object.keys(window.sessionStorage);");
	    for (String key : keys) {
	        String value = (String) js.executeScript(
	                "return window.sessionStorage.getItem(arguments[0]);",
	                key
	        );
	        log.info("Session storage: {}={}", key, value);
	    }

	    assertThat(sessionSize).isEqualTo(2);

	    js.executeScript("window.sessionStorage.setItem('new element', 'new value');");

	    Long sessionSizeAfter = (Long) js.executeScript("return window.sessionStorage.length;");
	    assertThat(sessionSizeAfter).isEqualTo(3);

	    driver.findElement(By.id("display-session")).click();
	}
}