package getting_started;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ByIdOrName;
import org.openqa.selenium.support.pagefactory.ByAll;
import org.openqa.selenium.support.pagefactory.ByChained;

import io.github.bonigarcia.wdm.WebDriverManager;

public class CompoundLocatorsTest {

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
	void testByIdOrName() {
		WebElement fileElement = driver.findElement(new ByIdOrName("my-file"));
		assertThat(fileElement.getAttribute("name")).isNotBlank();
		assertThat(fileElement.getAttribute("id")).isBlank();
	}

	@Test
	void testByChained() {
		// Find all rows inside the form tag
		List<WebElement> formRows = driver.findElements(new ByChained(By.tagName("form"), By.className("row")));
		assertThat(formRows.size()).isEqualTo(1);
	}

	@Test
	void testByAll() {
		// Find all form tags and rows
		List<WebElement> formsAndRows = driver.findElements(new ByAll(By.tagName("form"), By.className("row")));
		assertThat(formsAndRows.size()).isEqualTo(3);
	}

}
