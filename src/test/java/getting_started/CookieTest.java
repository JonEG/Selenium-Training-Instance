package getting_started;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Options;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class CookieTest {

	private WebDriver driver;

	@BeforeAll
	static void setupClass() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	void setup() {
		driver = new ChromeDriver();
		driver.get("https://bonigarcia.dev/selenium-webdriver-java/cookies.html");
	}

	@AfterEach
	void teardown() {
		driver.quit();
	}

	@Test
	void testReadCookies() {
		Options options = driver.manage();
		Set<Cookie> cookies = options.getCookies();
		assertThat(cookies).hasSize(2);

		Cookie username = options.getCookieNamed("username");
		assertThat(username.getValue()).isEqualTo("John Doe");
		assertThat(username.getPath()).isEqualTo("/");

		driver.findElement(By.id("refresh-cookies")).click();
	}

	@Test
	void testAddCookies() {
		Options options = driver.manage();
		Cookie newCookie = new Cookie("my-name", "Jon Echeveste");
		options.addCookie(newCookie);

		String readValue = options.getCookieNamed(newCookie.getName()).getValue();
		assertThat(newCookie.getValue()).isEqualTo(readValue);
		driver.findElement(By.id("refresh-cookies")).click();
	}

	@Test
	void testEditCookies() {
		Options options = driver.manage();

		Cookie readCookie = options.getCookieNamed("date");
		String readName = readCookie.getName();
		String initialValue = readCookie.getValue();

		assertThat(initialValue).isNotEmpty();

		String today = getDayMonthYear(new Date());

		Cookie updatedCookie = new Cookie(readName, today);

		options.addCookie(updatedCookie);

		driver.findElement(By.id("refresh-cookies")).click();

		Cookie overridenCookie = options.getCookieNamed(updatedCookie.getName());

		assertThat(initialValue).isNotEqualTo(overridenCookie.getValue());
		assertThat(overridenCookie.getValue()).isEqualTo(today);
	}

	private String getDayMonthYear(Date date) {
		SimpleDateFormat patternBased = new SimpleDateFormat("dd-MM-yyyy", Locale.of("es", "ES"));
		String patternOutput = patternBased.format(date);

		return patternOutput;
	}

	@Test
	void testDeleteCookies() {
		Options options = driver.manage();
		
		Set<Cookie> cookies = options.getCookies();
		Cookie dateCookie = options.getCookieNamed("date");
		
		options.deleteCookie(dateCookie);
		
		assertThat(options.getCookies()).hasSize(cookies.size() - 1);
		driver.findElement(By.id("refresh-cookies")).click();
	}
}