package org.example.seleniumhomeworks.tests;

import org.example.seleniumhomeworks.pages.MainPage;
import org.example.seleniumhomeworks.pages.ResultPage;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class BingSearchTest {
    private WebDriver driver;


    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        // Fix the issue https://github.com/SeleniumHQ/selenium/issues/11750
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(6));
        driver.get("https://www.bing.com/");

    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void searchResultsTest() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        String input = "Selenium";
        MainPage mp = new MainPage(driver);
        mp.sendText(input);

        ResultPage rp = new ResultPage(driver);
        rp.clickElement(0);

        wait.until(ExpectedConditions.and(
                ExpectedConditions.textToBePresentInElementLocated(By.cssSelector(":not(.b_adurl) > cite"), "Selenium"),
                ExpectedConditions.elementToBeClickable(By.cssSelector(":not(.b_adurl) > cite"))
        ));

        assertEquals("https://www.selenium.dev/", driver.getCurrentUrl(), "Открылась неверная ссылка");
    }

    @Test
    public void searchFieldTest() {
        String input = "Selenium";
        MainPage mp = new MainPage(driver);
        mp.sendText(input);

        ResultPage rp = new ResultPage(driver);
        assertEquals(input, rp.getTextFromSearchField(), "Текст не совпал!");
    }


}
