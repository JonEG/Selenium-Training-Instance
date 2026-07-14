package getting_started;

import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;

import io.github.bonigarcia.wdm.WebDriverManager;

public class RangeSlideTest {
	private WebDriver driver;

	static final Logger log = getLogger(MethodHandles.lookup().lookupClass());

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
	void testRangeSlide() throws IOException {
		WebElement slider = driver.findElement(By.name("my-range"));
		String initialValue = slider.getAttribute("value");
		
		slider.sendKeys(Keys.ARROW_RIGHT);
		
		String increasedValue = slider.getAttribute("value");
		
		assertThat(Integer.parseInt(initialValue)).isEqualTo(Integer.parseInt(increasedValue) - 1);
	}
}
