package seleniumUtilities;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class Screenshots {

    /**
     * Take screenshot, append date and store in unique folder for the test
     *
     * @param driver WebDriver
     * @param testName Test name
     */
    public static void embedScreenshot(WebDriver driver, String testName) {
        Path root = Paths.get(".").normalize().toAbsolutePath();
        String testImagesPath = root.toString() + "/src/main/java/Test Images/";
        seleniumUtilities.GeneralSeleniumActions.waitForPageLoaded(driver);
     //   seleniumUtilities.GeneralSeleniumActions.waitForJquery(driver);

        File fileSrc = takeScreenshot();
        String strDate = generalUtilities.DateUtilities.getCurrentDate("MM-dd-yyyy_HH-mm");
        String strDestination = testImagesPath + testName + "/" + strDate + ".png";

        int intIndex = 1;

        while (new File(strDestination).exists()) {
            strDestination = testImagesPath + testName + "/" + strDate + "_" + intIndex + ".png";
            intIndex++;
        }

        try {
            FileUtils.copyFile(fileSrc, new File(strDestination));
        } catch (IOException e) {
            System.out.println("Failed to capture image!");
        }
    }

    /**
     * Take screenshot of highlighted element, append date and store in unique folder for the test
     *
     * @param driver WebDriver
     * @param testName Test name
     * @param element Target element
     */
    public static void embedScreenshot(WebDriver driver, String testName, WebElement element) {
        Path root = Paths.get(".").normalize().toAbsolutePath();
        String testImagesPath = root.toString() + "/src/main/java/Test Images/";

        seleniumUtilities.GeneralSeleniumActions.waitForPageLoaded(driver);
   //     seleniumUtilities.GeneralSeleniumActions.waitForJquery(driver);

        Actions action = new Actions(driver);
        action.moveToElement(element).perform();

        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("arguments[0].style.border='3px solid green'", element);

        File fileSrc = takeScreenshot();
        //jse.executeScript("arguments[0].style.border='0px'", element);
        String strDate = generalUtilities.DateUtilities.getCurrentDate("MM-dd-yyyy_HH-mm");
        String strDestination = testImagesPath + testName + "/" + strDate + ".png";

        int intIndex = 1;

        while (new File(strDestination).exists()) {
            strDestination = testImagesPath + testName + "/" + strDate + "_" + intIndex + ".png";
            intIndex++;
        }

        try {
            FileUtils.copyFile(fileSrc, new File(strDestination));
        } catch (IOException e) {
            System.out.println("Failed to capture image!");
        }
    }

    /**
     * Get screenshot destination
     *
     * @return Location where screenshot is stored
     */
    public static File takeScreenshot() {
        File fileSrc = null;

        try {
            WebDriver driver = seleniumUtilities.DriverFactory.getCurrentDriver();
            fileSrc = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return fileSrc;
    }
}