package stepDefinitions;

import cucumber.api.java.After;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class CommonSteps {

    public static Properties prop;
    public static WebDriver driver;

    public CommonSteps(){
        try {
            prop = new Properties();
            FileInputStream ip = new FileInputStream("..\\CucumberSeleniumFramework\\src\\main\\config\\Configuration.properties");
            prop.load(ip);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static WebDriver initialization() {
        String browser = prop.getProperty("browser");
        if (browser.equals("chrome")) {
            System.setProperty("webdriver.chrome.driver", "..\\CucumberSeleniumFramework\\drivers\\Chrome\\chromedriver.exe");
            driver = new ChromeDriver();
        } else if (browser.equals("firefox")) {
            System.setProperty("webdriver.gecko.driver", "..\\CucumberSeleniumFramework\\drivers\\Firefox\\geckodriver.exe");
            driver = new FirefoxDriver();
        } else if (browser.equals("safari")) {
            System.setProperty("webdriver.safari.driver", "..\\CucumberSeleniumFramework\\drivers\\Safari\\safaridriver.exe");
            driver= new SafariDriver();
        } else {
            System.out.println("Please pass the correct browser value: " + browser);
        }

        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().pageLoadTimeout(5000, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(5000, TimeUnit.SECONDS);
        driver.get(prop.getProperty("url"));

        return driver;

    }

    public void waitTillElementVisibilityAndClick(WebElement element){
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.visibilityOf(element)).click();
    }

    public static String getCurrentDateTime(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("d MMM, h:mm a");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);

    }

}
