package utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class Browser {

    private static WebDriver driver;

    public static WebDriver driver()
    {
        if (driver == null)
        {
            driver = new ChromeDriver();
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(3000, TimeUnit.MILLISECONDS);
        }

        return driver;
    }


    public static void closeDriver()
    {
        driver.quit();
    }

}
