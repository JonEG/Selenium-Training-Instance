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
import org.openqa.selenium.Point;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DragAndDropTest {
	private WebDriver driver;

	@BeforeAll
	static void setupClass() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	void setup() {
		driver = new ChromeDriver();
		driver.get("https://bonigarcia.dev/selenium-webdriver-java/drag-and-drop.html");
	}

	@AfterEach
	void teardown() {
		driver.quit();
	}

	@Test
	void testMouseHover() throws InterruptedException {
		Actions actions = new Actions(driver);

		WebElement draggable = driver.findElement(By.id("draggable"));

		Point initialLocation = draggable.getLocation();
		
		int offset = 100;
		
		actions.dragAndDropBy(draggable, offset, 0)
		.dragAndDropBy(draggable, 0, offset)
		.dragAndDropBy(draggable, -offset, 0)
		.dragAndDropBy(draggable, 0, -offset).build().perform();
		assertThat(initialLocation).isEqualTo(draggable.getLocation());
		
		WebElement target = driver.findElement(By.id("target"));
		
		actions.dragAndDrop(draggable, target).build().perform();
		
		assertThat(target.getLocation()).isEqualTo(draggable.getLocation());		
	}
}
