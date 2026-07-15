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
import org.openqa.selenium.support.locators.RelativeLocator;

import io.github.bonigarcia.wdm.WebDriverManager;

public class MouseHoverTest {
	private WebDriver driver;

	@BeforeAll
	static void setupClass() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	void setup() {
		driver = new ChromeDriver();
		driver.get("https://bonigarcia.dev/selenium-webdriver-java/mouse-over.html");
	}

	@AfterEach
	void teardown() {
		driver.quit();
	}

	@Test
	void testMouseHover() {
		Actions actions = new Actions(driver);
		
		WebElement image = driver.findElement(By.xpath("//img[@src='img/compass.png']"));
		
		actions.moveToElement(image).build().perform();
		
		WebElement caption = driver.findElement(RelativeLocator.with(By.tagName("div")).near(image));
		assertThat(caption.getText()).containsIgnoringCase("compass");
	}
}
