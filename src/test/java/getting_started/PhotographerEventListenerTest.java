package getting_started;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringDecorator;

import EventListener.PhotographerEventListener;
import io.github.bonigarcia.wdm.WebDriverManager;

public class PhotographerEventListenerTest {
	WebDriver driver;

	@BeforeEach
	void setup() {
		PhotographerEventListener listener = new PhotographerEventListener();
		WebDriver originalDriver = WebDriverManager.chromedriver().create();
		driver = new EventFiringDecorator<WebDriver>(listener).decorate(originalDriver);
	}

	@AfterEach
	void teardown() {
		driver.quit();
	}

	@Test
	void testEventListener() {
		driver.get("https://www.selenium.dev/selenium/web/web-form.html");
		assertThat(driver.getTitle()).isEqualTo("Web form");
		
		driver.findElement(By.id("my-text-id")).sendKeys("Hello Photographer!");
	}

}
