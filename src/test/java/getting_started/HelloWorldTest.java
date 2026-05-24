package getting_started;

import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;

import io.github.bonigarcia.wdm.WebDriverManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

import java.lang.invoke.MethodHandles;

public class HelloWorldTest {
	
	static final Logger log = getLogger(MethodHandles.lookup().lookupClass());
	
	private WebDriver driver;
	
	@BeforeAll
	static void setupClass() {
		WebDriverManager.chromedriver().setup();
	}
	
	@BeforeEach
	void setup() {
		driver = new ChromeDriver();
	}
	
	@Test
	void test() {
		driver.get("https://github.com/JonEG");
		
		log.info("You have visited: {}", driver.getTitle());
		
		assertThat(driver.getTitle()).contains("Jon Echeveste");
	}
	
	@AfterEach
	void teardown() {
		driver.quit();
	}
}
