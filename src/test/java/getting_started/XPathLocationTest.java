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

public class XPathLocationTest {

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
	void testByXPathBasic() {
		WebElement hidden = driver.findElement(By.xpath("//input[@type='hidden']"));
		assertThat(hidden.isDisplayed()).isFalse();
	}

	@Test
	void testByXPathAdvanced() {
		WebElement radio1 = driver.findElement(By.xpath("//*[@type='radio' and @checked]"));
		assertThat(radio1.getAttribute("id")).isEqualTo("my-radio-1");
		assertThat(radio1.isSelected()).isTrue();
		WebElement radio2 = driver.findElement(By.xpath("//*[@type='radio' and not(@checked)]"));
		assertThat(radio2.getAttribute("id")).isEqualTo("my-radio-2");
		assertThat(radio2.isSelected()).isFalse();
	}

}
