package seleniumUtilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;

public class DriverFactory {
	private static WebDriver globalDriver;

	/**
	 * Opens a browser based on browser type
	 *
	 * @param browserType browser to open
	 * @return WebDriver
	 */
	public static WebDriver open(String browserType){
		WebDriver driver;

		if(browserType.equalsIgnoreCase("chrome")){
		//	System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
			WebDriverManager.chromedriver().setup();
			ChromeOptions options = new ChromeOptions();
			options.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.IGNORE);
			driver = new ChromeDriver(options);
		}
		else if (browserType.equalsIgnoreCase("firefox")){
		//	System.setProperty("webdriver.firefox.driver", "src/main/resources/geckodriver.exe");
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		}
		else{
			WebDriverManager.iedriver().setup();
			InternetExplorerOptions options = new InternetExplorerOptions();
			//options.introduceFlakinessByIgnoringSecurityDomains();
			//options.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.IGNORE);
			driver = new InternetExplorerDriver(options);
		}
		globalDriver = driver;

		return driver;
	}

	/**
	 * Opens a browser based on browser type with waiting time limit
	 *
	 * @param browserType browser to open
	 * @param timespan amount of time to wait
	 * @return WebDriver
	 */
	public static WebDriver open(String browserType, int timespan) {
		WebDriver driver = open(browserType);
		driver.manage().timeouts().implicitlyWait(timespan, TimeUnit.SECONDS);
		driver.manage().window().maximize();

		return driver;
	}

	/**
	 * Returns globalDriver
	 *
	 * @return WebDriver
	 * @throws Exception if driver is null
	 */
	public static WebDriver getCurrentDriver() throws Exception{
		if(globalDriver.equals(null)) {
			throw new Exception("No driver has been created!");
		}

		return globalDriver;
	}
}