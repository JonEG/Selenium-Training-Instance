package getting_started;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.ScriptTimeoutException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TimeoutTest {
	private WebDriver driver;

	@BeforeAll
	static void setupClass() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	void setup() {
		driver = new ChromeDriver();
	}

	@AfterEach
	void teardown() {
		driver.quit();
	}

	@Test
	void testPageLoadTimeout() {
		driver.manage().timeouts().pageLoadTimeout(Duration.ofMillis(1));

		assertThatThrownBy(() -> driver.get("https://www.selenium.dev/selenium/web/web-form.html"))
				.isInstanceOf(TimeoutException.class);
	}

	@Test
	void testScriptTimeout() {
		driver.get("https://www.selenium.dev/selenium/web/web-form.html");
		JavascriptExecutor js = (JavascriptExecutor) driver;
		driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(3));
		assertThatThrownBy(() -> {
			long waitMillis = Duration.ofSeconds(5).toMillis();
			String script = "const callback = arguments[arguments.length - 1];" + "window.setTimeout(callback, "
					+ waitMillis + ");";
			js.executeAsyncScript(script);
		}).isInstanceOf(ScriptTimeoutException.class);
	}
}