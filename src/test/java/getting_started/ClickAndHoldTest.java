package getting_started;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ClickAndHoldTest {

    private WebDriver driver;

    @BeforeAll
    static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setup() {
        driver = new ChromeDriver();
        driver.get(
            "https://bonigarcia.dev/selenium-webdriver-java/draw-in-canvas.html"
        );
    }

    @AfterEach
    void teardown() {
        driver.quit();
    }

    @Test
    void testClickAndHold() {
        WebElement canvas = driver.findElement(By.tagName("canvas"));

        // Left eye
        new Actions(driver)
            .moveToElement(canvas, -20, -20)
            .clickAndHold()
            .moveByOffset(0, 8)
            .release()
            .perform();

        // Right eye
        new Actions(driver)
            .moveToElement(canvas, 20, -20)
            .clickAndHold()
            .moveByOffset(0, 8)
            .release()
            .perform();

        // Smile
        Actions smile = new Actions(driver);

        int previousX = -35;
        int previousY = 5;

        smile.moveToElement(canvas, previousX, previousY)
             .clickAndHold();

        for (int x = -30; x <= 35; x += 5) {
            double position = x / 35.0;

            // The center of the curve is lower than its ends.
            int y = 5 + (int) Math.round(
                20 * (1 - position * position)
            );

            smile.moveByOffset(
                x - previousX,
                y - previousY
            );

            previousX = x;
            previousY = y;
        }

        smile.release().perform();
    }
}