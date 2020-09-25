package generalUtilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.coordinates.WebDriverCoordsProvider;
import ru.yandex.qatools.ashot.cropper.indent.IndentCropper;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;
import seleniumUtilities.Screenshots;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static restAssuredUtilities.Reporting.logStep;
import static ru.yandex.qatools.ashot.cropper.indent.IndentFilerFactory.blur;

public class ScreenshotFunctions extends Screenshots {

    public static void fullPageScreenShot(WebDriver driver) {
        logStep("taking full page screenshot...");
        //take screenshot of the entire page with scrolling through whole page and stitch together
        Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(driver);
        try {
            ImageIO.write(screenshot.getImage(), "PNG", new File(screenshotDirectory() + "/"
                    + "Fullpage-" + String.join("-", localDateTimeSecondFormatted() + ".png")));
        } catch (IOException e) {
            logStep("Error taking full page screenshot");
            e.printStackTrace();
        }
    }

    public static void elementScreenshot(WebDriver driver, WebElement element, Boolean blur) {
        //take screenshot of individual element
        logStep("Generating screen shot of element: "+element.getTagName()+(blur?" with blur":" without blur"));
        Screenshot screenshot;
        if (blur) screenshot = new AShot() //if blur = true, screenshot element with blur
                .imageCropper(new IndentCropper().addIndentFilter(blur()))
                .coordsProvider(new WebDriverCoordsProvider()).takeScreenshot(driver, element);
        else screenshot = new AShot() //otherwise just screenshot the element
                .coordsProvider(new WebDriverCoordsProvider()).takeScreenshot(driver, element);

        try {
            ImageIO.write(screenshot.getImage(), "PNG", new File(screenshotDirectory() + "/"
                    + "Element-" + String.join("-", localDateTimeSecondFormatted() + ".png")));
        } catch (IOException e) {
            logStep("error taking screenshot "+element.getTagName());
            e.printStackTrace();
        }
    }

    public static File takeScreenshot() {
        return Screenshots.takeScreenshot();
    }

    //add additional functions using Ashot or other library below

    private static String localDateTimeSecondFormatted() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH-mm-ss");

        return now.format(formatter);
    }

    private static Path screenshotDirectory() {
        return Paths.get(System.getProperty("user.dir"), "src/main/resources/Test Images/");
        //TODO:William, plug in directory handling & generation from base Screenshot
        // class, make method in Screenshots to return directory handling, then call here.
    }
}

