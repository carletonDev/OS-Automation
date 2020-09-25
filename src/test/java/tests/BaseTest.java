package tests;

import java.util.Map;
import listeners.SuiteListener;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import seleniumUtilities.DriverFactory;
import seleniumUtilities.Recorder;

public class BaseTest extends SuiteListener {

  protected int interactedElements = 0;
  protected WebDriver driver;
  protected String browser;
  protected int landingElements = 0;

  @BeforeTest
  @Parameters("browser")
  public void beforeTest(String browser, ITestContext testContext) {
    //get all parameters
    Map<String, String> parameters = testContext.getCurrentXmlTest().getAllParameters();
    //if there is no browser in the test case default to google chrome
    checkBrowserParameter(browser, testContext, parameters);
    //set browser to browser
    this.browser = browser;
    //maximize window set to url parameter
    driver.manage().window().maximize();
    driver.get(parameters.get("url"));
    try {
      Recorder.startRecording(testContext.getName());
    } catch (Exception ex) {
      System.out.println("Exception encountered on recording");
    }
  }

  private void checkBrowserParameter(String browser, ITestContext testContext,
      Map<String, String> parameters) {
    if (parameters.get("browser") != null) {
      driver = DriverFactory
          .open(testContext.getCurrentXmlTest().getAllParameters().get("browser"), 15);
    } else {
      driver = DriverFactory.open(browser, 15);
    }
  }


  @AfterTest
  public void afterTest() throws Exception {
    //add current elements interacted and landing to suite level interacted elements count
    interactedElementCount = interactedElements;
    landingElementsCount = landingElements;
    Recorder.endRecording();
    driver.quit();

  }


}
