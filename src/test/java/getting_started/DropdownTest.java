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
import org.openqa.selenium.support.ui.Select;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DropdownTest {
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
	void testSelectDropdown() {
		Select select = new Select(driver.findElement(By.name("my-select")));

		select.selectByValue("1");

		if (select.isMultiple() == false) {
			assertThat(select.getFirstSelectedOption().getAttribute("value")).isEqualTo("1");
		} else {
			assertThat(select.getAllSelectedOptions().getLast().getAttribute("value")).isEqualTo("1");
		}

	}
	
	@Test
	void testDatalistDropdown() {
		WebElement input = driver.findElement(By.name("my-datalist"));
		String initialValue = input.getAttribute("value");
		
		WebElement firstOption = driver.findElement(By.xpath("//*[@id=\"my-options\"]/option[1]"));
		String optionValue = firstOption.getAttribute("value");
		assertThat(optionValue).isEqualTo("San Francisco");
		
		assertThat(initialValue).isNotEqualTo(optionValue);
		
		input.click();
		input.sendKeys(optionValue);
		
		assertThat(input.getAttribute("value")).isEqualTo("San Francisco");
	}
}