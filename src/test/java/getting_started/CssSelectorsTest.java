package getting_started;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class CssSelectorsTest {

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
	void testByCssSelectorBasic() {
		WebElement hidden = driver.findElement(By.cssSelector("input[type=hidden]"));
		assertThat(hidden.isDisplayed()).isFalse();
	}

	@Test
	void testByCssSelectorAdvanced() {
		WebElement checkbox1 = driver.findElement(By.cssSelector("[type=checkbox]:checked"));
		assertThat(checkbox1.getAttribute("id")).isEqualTo("my-check-1");
		assertThat(checkbox1.isSelected()).isTrue();
		WebElement checkbox2 = driver.findElement(By.cssSelector("[type=checkbox]:not(:checked)"));
		assertThat(checkbox2.getAttribute("id")).isEqualTo("my-check-2");
		assertThat(checkbox2.isSelected()).isFalse();
	}

}
