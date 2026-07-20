package getting_started;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class HistoryNavigationTest {
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
	void testHistory() {
		String baseUrl = "https://bonigarcia.dev/selenium-webdriver-java/";
		String firstPage = baseUrl + "navigation1.html";
		String secondPage = baseUrl + "navigation2.html";
		String thirdPage = baseUrl + "navigation3.html";

		driver.get(firstPage);
		driver.navigate().to(secondPage);
		driver.navigate().to(thirdPage);
		driver.navigate().back();
		driver.navigate().forward();
		driver.navigate().refresh();
		assertThat(driver.getCurrentUrl()).isEqualTo(thirdPage);
	}
}
