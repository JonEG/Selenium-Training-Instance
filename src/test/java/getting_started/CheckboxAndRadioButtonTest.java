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

public class CheckboxAndRadioButtonTest {
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
	void testCheckbox() {
		WebElement checkedCheckbox = driver.findElement(By.id("my-check-1"));
		assertThat(checkedCheckbox.isSelected()).isTrue();
		
		checkedCheckbox.click();
		assertThat(checkedCheckbox.isSelected()).isFalse();
	}
	
	@Test
	void testRadioButton() throws InterruptedException {
		WebElement checkedRadioButton = driver.findElement(By.id("my-radio-1"));
		assertThat(checkedRadioButton.isSelected()).isTrue();
		
		WebElement secondRadioButton = driver.findElement(By.id("my-radio-2"));
		assertThat(secondRadioButton.isSelected()).isFalse();
		
		secondRadioButton.click();
		
		assertThat(checkedRadioButton.isSelected()).isFalse();
		assertThat(secondRadioButton.isSelected()).isTrue();
	}
	
	
}
