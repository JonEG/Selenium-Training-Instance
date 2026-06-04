package getting_started;

import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

import java.lang.invoke.MethodHandles;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;

import io.github.bonigarcia.wdm.WebDriverManager;

public class LocationStrategyTest {

	static final Logger log = getLogger(MethodHandles.lookup().lookupClass());

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
	void testLocateByTagName() {
		WebElement textarea = driver.findElement(By.tagName("textarea"));
		assertThat(textarea.getDomAttribute("rows")).isEqualTo("3");
	}

	@Test
	void testLocateByHtmlAttributes() {
		// By name
		WebElement textByName = driver.findElement(By.name("my-text"));
		assertThat(textByName.isDisplayed());

		// By id
		WebElement textById = driver.findElement(By.id("my-text-id"));
		assertThat(textById.getAttribute("type")).isEqualTo("text");
		assertThat(textById.getDomAttribute("type")).isEqualTo("text");
		assertThat(textById.getDomProperty("type")).isEqualTo("text");

		// By class name
		List<WebElement> byClassName = driver.findElements(By.className("form-control"));
		assertThat(byClassName.size()).isPositive();
	}

	@Test
	void testLocateByLinkText() {
		WebElement linkByText = driver.findElement(By.linkText("Return to index"));
		assertThat(linkByText.getTagName()).isEqualTo("a");
		assertThat(linkByText.getCssValue("text-decoration")).isEqualTo("underline");

		WebElement linkByPartialText = driver.findElement(By.partialLinkText("index"));
		assertThat(linkByPartialText.getLocation()).isEqualTo(linkByText.getLocation());
		assertThat(linkByPartialText.getRect()).isEqualTo(linkByText.getRect());
	}

}
