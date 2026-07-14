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
import org.openqa.selenium.interactions.Actions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class RightAndDoubleClickTest {
	private WebDriver driver;

	@BeforeAll
	static void setupClass() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	void setup() {
		driver = new ChromeDriver();
		driver.get("https://bonigarcia.dev/selenium-webdriver-java/dropdown-menu.html");
	}

	@AfterEach
	void teardown() {
		driver.quit();
	}

	@Test
	void testRightClick() {
		WebElement dropDownButton = driver.findElement(By.id("my-dropdown-2"));
		WebElement contextMenu = driver.findElement(By.id("context-menu-2"));
		
		assertThat(contextMenu.isDisplayed()).isFalse();
		
		Actions actions = new Actions(driver);
		
		actions.click(dropDownButton).build().perform();
		assertThat(contextMenu.isDisplayed()).isFalse();
		
		actions.contextClick(dropDownButton).build().perform();
		assertThat(contextMenu.isDisplayed()).isTrue();
	}
	
	@Test
	void testDoubleClick() {
		WebElement dropDownButton = driver.findElement(By.id("my-dropdown-3"));
		WebElement contextMenu = driver.findElement(By.id("context-menu-3"));
		
		assertThat(contextMenu.isDisplayed()).isFalse();
		
		Actions actions = new Actions(driver);
		
		actions.click(dropDownButton).build().perform();
		assertThat(contextMenu.isDisplayed()).isFalse();
		
		actions.doubleClick(dropDownButton).build().perform();
		assertThat(contextMenu.isDisplayed()).isTrue();
	}
}
