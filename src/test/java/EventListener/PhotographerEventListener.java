package EventListener;

import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.openqa.selenium.support.events.WebDriverListener;

import org.slf4j.Logger;
import static org.slf4j.LoggerFactory.getLogger;

public class PhotographerEventListener implements WebDriverListener {
	static final Logger log = getLogger(MethodHandles.lookup().lookupClass());
	
	private static final Path SCREENSHOT_DIR = Paths.get("screenshots");

	@Override
	public void afterGet(WebDriver driver, String url) {
		WebDriverListener.super.afterGet(driver, url);
		takeScreenshot(driver);
	}

	@Override
	public void beforeQuit(WebDriver driver) {
		takeScreenshot(driver);
		WebDriverListener.super.beforeQuit(driver);
	}

	private void takeScreenshot(WebDriver driver) {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File screenshot = ts.getScreenshotAs(OutputType.FILE);
		
		SessionId sessionId = ((RemoteWebDriver) driver).getSessionId();
		Date today = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd_HH.mm.ss.SSS");
		String screenshotFileName = String.format("%s-%s.png", dateFormat.format(today), sessionId.toString());
		
		try {
			Files.createDirectories(SCREENSHOT_DIR);
			
			Path destination = SCREENSHOT_DIR.resolve(screenshotFileName);
			Files.move(screenshot.toPath(), destination);
		} catch (IOException e) {
			 log.error("Exception writing screenshot to screenshots folder", e);
		}
	}

}
