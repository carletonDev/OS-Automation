package listeners;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import desktop.excel.ExcelWriter;
import generalUtilities.DeleteRunner;
import generalUtilities.RetryAnalyzer;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Row;
import org.testng.IAnnotationTransformer;
import org.testng.IExecutionListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.ITestAnnotation;
import restAssuredUtilities.Core;
import restAssuredUtilities.Generic;
import restAssuredUtilities.Reporting;
import seleniumUtilities.Screenshots;


public class TestNgListeners implements ITestListener, IAnnotationTransformer,
    IExecutionListener {

  public static ExtentTest logger;

  private DeleteRunner deleteFiles = new DeleteRunner();


  public void onTestStart(ITestResult result) {
    logger.info("Executing Test Case: " + result.getName());
    deleteScreenshots(result.getName());
    try {
      Generic.logPrintStream(result.getName());
      Core.setPrintStream();

      //Core.tokenGeneration();

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  public void onTestSuccess(ITestResult result) {

    // TODO Auto-generated method stub
    logger.log(Status.PASS,
        MarkupHelper.createLabel(result.getName() + " PASSED ", ExtentColor.GREEN));

  }

  public void onTestFailure(ITestResult result) {
    String name = result.getName();
    logger.info("deleting old failed screenshots");
    deleteScreenshots(name);
    logger.log(Status.FAIL, MarkupHelper.createLabel(name + " FAILED ", ExtentColor.RED));
    logger.info("Test failure located!");
    logger.fail(result.getThrowable()); //shows stacktrace upon failure
    Calendar cal = Calendar.getInstance();
    SimpleDateFormat format = new SimpleDateFormat("MM_dd_yyyy-HH_mm");

    if (!result.isSuccess()) {
      File src = Screenshots.takeScreenshot();
      try {
        Path root = Paths.get(".").normalize().toAbsolutePath();
        String reportDir = root.toString() + "/src/main/java/Test Images/" + name;
        File destFile = new File(
            reportDir + "/" + name + "_" + format.format(cal.getTime()) + "/error.png");
        FileUtils.copyFile(src, destFile);

        Reporter.log(
            "<a href='" + destFile.getAbsolutePath() + "'> <img src='" + destFile.getAbsolutePath()
                + "' height='100' width='100'/> </a>");
        Reporting.getTestCaseReference().info(
            "<a href='" + destFile.getAbsolutePath() + "'> <img src='" + destFile.getAbsolutePath()
                + "' height='100' width='100'/> </a>");

      } catch (IOException e) {
        System.out.println(e.getMessage());
      }
    }
  }

  public void onTestSkipped(ITestResult result) {
    // TODO Auto-generated method stub
    logger.log(Status.SKIP,
        MarkupHelper.createLabel(result.getName() + " SKIPPED ", ExtentColor.ORANGE));
    logger.skip(result.getThrowable()); //gets stacktrace

  }

  public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    // TODO Auto-generated method stub
    logger.log(Status.WARNING, MarkupHelper
        .createLabel(result.getName() + " SUCCESS WITHIN PERCENTAGE FAILURE ", ExtentColor.TEAL));
  }

  public void onStart(ITestContext context) {
    deleteScreenshots(context.getName());
    Reporting.startTestReporting(context.getName());
    logger = Reporting.getTestCaseReference();
    logger.info("Reporting started...");
    logger.info("deleting old passed screenshots");
    logger.log(Status.INFO,
        MarkupHelper.createLabel(context.getName() + " STARTED ", ExtentColor.PINK));
  }




  public void onFinish(ITestContext context) {
    logger.info("Start tear down.");
  }

  @Override
  public void transform(ITestAnnotation iTestAnnotation, Class aClass, Constructor constructor,
      Method method) {
    iTestAnnotation.setRetryAnalyzer(RetryAnalyzer.class);
  }

  @Override
  public void onExecutionStart() {
    // TODO Auto-generated method stub

  }

  @Override
  public void onExecutionFinish() {
  }

  private void deleteScreenshots(String testName) {
    deleteFiles.deleteFiles(new File(
        Paths.get(".").normalize().toAbsolutePath().toString() + "/src/main/java/Test Images/"
            + testName)
    );
    if (deleteFiles.isDeleted()) {
      logger.info("deleted old files for: " + testName);
    }
  }

}
