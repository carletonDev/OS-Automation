package restAssuredUtilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import java.time.LocalTime;


public class Reporting {

  public static ExtentReports report;
  private static ExtentHtmlReporter htmlReport;
  private static ExtentTest testcase;

  public static void startReporting() {
    htmlReport = new ExtentHtmlReporter(Generic.getCurrentDirectory() + "src/main/java/result/testReport.html");

    htmlReport.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");

    htmlReport.config().setDocumentTitle("Automation Test Report");
    htmlReport.config().setReportName("Automation Test Report");
    htmlReport.config().setTestViewChartLocation(ChartLocation.TOP);
    htmlReport.config().setChartVisibilityOnOpen(true);

    report = new ExtentReports();
    report.attachReporter(htmlReport);
    report.setSystemInfo("Host Name", "SharePoint InfoNet");
    report.setSystemInfo("Environment", "Test");
    report.setSystemInfo("Execution Date Time", LocalTime.now().toString());
    htmlReport.config().setTheme(Theme.DARK);

  }

  public static void endReporting() {

    report.flush();
  }


  public static void startTestReporting(String testCaseName) {

    testcase = report.createTest(testCaseName);

  }


  public static ExtentTest getTestCaseReference() {
    return testcase;
  }

  public static void logStep(String message) {
    Reporting.getTestCaseReference().info(message);
  }

  public static void endTest() {
    testcase.log(Status.INFO, new LogLink(Generic.getLogFilePath()));
  }
}
