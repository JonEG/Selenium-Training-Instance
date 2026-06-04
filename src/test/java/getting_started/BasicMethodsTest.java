package getting_started;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BasicMethodsTest {

	private WebDriver driver;

	@BeforeEach
	void setup() {
		driver = WebDriverManager.chromedriver().create();
	}

	@AfterEach
	void teardown() {
		driver.quit();
	}

	@Test
	void testBasicMethods() {
		String sutUrl = "https://github.com/JonEG";

		// open URL on browser
		driver.get(sutUrl);
		// verify right URL was opened
		String currentUrl = driver.getCurrentUrl();
		assertThat(currentUrl).isEqualTo(sutUrl);

		// verify right profile was opened
		String sutTitle = driver.getTitle();
		assertThat(sutTitle).isEqualToIgnoringCase("JonEG (Jon Echeveste González) · GitHub");

		// verify this project is pinned
		String pageSource = driver.getPageSource();
		assertThat(pageSource).containsIgnoringCase("Selenium-Training-Instance");

	}
}
