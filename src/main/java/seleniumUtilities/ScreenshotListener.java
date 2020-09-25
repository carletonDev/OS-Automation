package seleniumUtilities;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.io.FileUtils;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class ScreenshotListener extends TestListenerAdapter{
    /**
     * Takes screenshot and store it in directory if test fails
     *
     * @param result ITestResult
     */
    @Override
    public void onTestFailure(ITestResult result) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("MM_dd_yyyy-HH_mm");
        String name = result.getName();

        if(!result.isSuccess()) {
            File src = Screenshots.takeScreenshot();
            try {
                Path root = Paths.get(".").normalize().toAbsolutePath();
                String reportDir = root.toString()+"/src/main/java/Test Images/"+name;
                File destFile = new File((String)reportDir+"/"+name+"_"+format.format(cal.getTime())+"/error.png");
                FileUtils.copyFile(src, destFile);
            }
            catch(IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}