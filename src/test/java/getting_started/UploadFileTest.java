package getting_started;

import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Path;

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

public class UploadFileTest {
	private WebDriver driver;
	
	private String initUrl = "https://www.selenium.dev/selenium/web/web-form.html";
	
	static final Logger log = getLogger(MethodHandles.lookup().lookupClass());

	@BeforeAll
	static void setupClass() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	void setup() {
		driver = new ChromeDriver();
		driver.get(initUrl);
	}

	@AfterEach
	void teardown() {
		driver.quit();
	}

	@Test
	void testUploadFile() throws IOException {
		WebElement fileInput = driver.findElement(By.name("my-file"));
	
		Path tempFile = Files.createTempFile("tempfiles", ".tmp");
		try {
			String filePath = tempFile.toAbsolutePath().toString();
			log.debug("Using temporary file {} in file uploading", filePath);
			fileInput.sendKeys(filePath);
			
			driver.findElement(By.tagName("form")).submit();
			
			assertThat(driver.getCurrentUrl()).isNotEqualTo(initUrl);
			assertThat(driver.getCurrentUrl()).contains("my-file=" + tempFile.getFileName());
		} finally {
            Files.deleteIfExists(tempFile);
        }
	}
}
