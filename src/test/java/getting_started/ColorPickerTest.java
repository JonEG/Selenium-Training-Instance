package getting_started;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.Color;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ColorPickerTest {
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
	void testColorPicker() throws InterruptedException {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		
		WebElement colorPicker = driver.findElement(By.xpath("//input[@type='color']"));
		
		Color initialColor = Color.fromString(colorPicker.getAttribute("value"));
		
		Color green = new Color(0, 255, 0, 1);
		
		String script = String.format("arguments[0].setAttribute('value', '%s');", green.asHex());
		
		jsExecutor.executeScript(script, colorPicker);
		
		Color finalColor = Color.fromString(colorPicker.getAttribute("value"));
		
		assertThat(initialColor).isNotEqualTo(finalColor);
		assertThat(finalColor).isEqualTo(green);
	}
}
