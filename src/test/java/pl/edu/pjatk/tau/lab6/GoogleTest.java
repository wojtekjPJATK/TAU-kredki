package pl.edu.pjatk.tau.lab6;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;


@RunWith(JUnit4.class)
public class GoogleTest {
    WebDriver driver;
    WebDriverWait wait;

    @Before
    public void setup() {
        System.setProperty("webdriver.gecko.driver", "/home/PJWSTK/s15983/koloroweKredki/geckodriver");
        
        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
        capabilities.setCapability("marionette", true);
        driver = new FirefoxDriver();
        wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void basicCheck() throws InterruptedException {
        driver.get("http://www.google.com/");
        Thread.sleep(1000);
        assertThat(driver.getTitle(), is("Google"));
        // WebElement element = driver.findElement(By.name("q"));
        // element.sendKeys("PJATK");
        // element.submit();
        System.out.println("Page title is: " + driver.getTitle());
    }


    @After
    public void cleanup() {
        driver.quit();
    }
}