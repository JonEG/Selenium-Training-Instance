package getting_started;

import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;

import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ScreenshotTest {
	private WebDriver driver;

	static final Logger log = getLogger(MethodHandles.lookup().lookupClass());

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
	void testPngScreenshot() throws IOException {
		TakesScreenshot camera = (TakesScreenshot) driver;

		File screenshot = camera.getScreenshotAs(OutputType.FILE);

		Path path = Paths.get("screenshot.png");

		Files.move(screenshot.toPath(), path, StandardCopyOption.REPLACE_EXISTING);

		assertThat(path).exists();
	}

	/**
	 * Logging the screenshot in Base64 as presented in the previous example could
	 * be very useful for diagnosing failures when running tests in CI servers in
	 * which we do not have access to the file system (e.g., GitHub Actions).
	 */
	@Test
	void testScreenshotBase64() {
		TakesScreenshot camera = (TakesScreenshot) driver;
		String screenshot = camera.getScreenshotAs(OutputType.BASE64);

		assertThat(screenshot).isNotEmpty();

		log.info("Screenshot in base64 " + "(you can copy and paste it into a browser navigation bar to watch it)\n"
				+ "data:image/png;base64,{}", screenshot);
	}

	@Test
	void testWebElementScreenshot() throws IOException {
		WebElement form = driver.findElement(By.tagName("form"));
		
		File screenshot = form.getScreenshotAs(OutputType.FILE);
		
		Path path = Paths.get("webelement-screenshot.png");
		
		Files.move(screenshot.toPath(), path, StandardCopyOption.REPLACE_EXISTING);
		
		assertThat(path).exists();
	}

}