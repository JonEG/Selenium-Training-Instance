package getting_started;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class AsyncScriptTest {
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
	void testAsyncScript() {
		JavascriptExecutor js = (JavascriptExecutor) driver;

		Duration pause = Duration.ofSeconds(5);
		String script = "const done = arguments[arguments.length - 1];" 
		+ "window.setTimeout(done, " + pause.toMillis() + ");";

		long initMillis = System.currentTimeMillis();
		js.executeAsyncScript(script);
		Duration elapsed = Duration.ofMillis(System.currentTimeMillis() - initMillis);
		assertThat(elapsed).isGreaterThanOrEqualTo(pause);
	}
}