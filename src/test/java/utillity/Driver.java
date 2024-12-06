package utillity;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import java.time.Duration;
import java.util.Set;

public class Driver {
    public static WebDriver driver;

    public static WebDriver getDriver() {

        if (driver == null) {
            switch (ConfigReader.getProperty("browser")) {
                case "chrome" :
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions options = new ChromeOptions();
                    String headlessMode = ConfigReader.getProperty("headless");
                    options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/113.0.5735.110 Safari/537.36");
                    if (headlessMode.equalsIgnoreCase("true")){
                        options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1080");
                    }
                    driver = new ChromeDriver(options);
                    break;
                case "firefox" :
                    WebDriverManager.firefoxdriver().setup();
                    driver = new FirefoxDriver();
                    break;
                default:
                    throw new IllegalStateException("Geçersiz tarayıcı seçimi: " + ConfigReader.getProperty("browser"));
            }
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        }
        return driver;
    }
    public static void closeDriver() throws InterruptedException {
        if (driver != null){
            Thread.sleep(2000);
            driver.quit();
            driver = null;
        }
    }
}

