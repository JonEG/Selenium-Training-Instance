package getting_started;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Window;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BrowserSizeAndPositionTest {
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
	void testWindow() throws InterruptedException {
		Window window = driver.manage().window();
		Point initialPosition = window.getPosition();
		Dimension initialSize = window.getSize();

		window.maximize();

		Point maximizedPosition = window.getPosition();
		Dimension maximizedSize = window.getSize();

		assertThat(initialPosition).isNotEqualTo(maximizedPosition);
		assertThat(initialSize).isNotEqualTo(maximizedSize);
	}
}
