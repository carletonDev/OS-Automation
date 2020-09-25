package poms;

import com.aventstack.extentreports.ExtentTest;
import restAssuredUtilities.Reporting;
import java.util.HashSet;
import java.util.Set;
import listeners.SeleniumAttemptActionListeners;
import listeners.generalSeleniumActionEvent.SeleniumAction;
import listeners.generalSeleniumActionEvent.SeleniumActionEvent;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class BasePom {

  //enables the class to see the driver and interact with the browser
  WebDriver driver;


  private SeleniumAttemptActionListeners listeners = new SeleniumAttemptActionListeners();

  private ExtentTest logger = Reporting.getTestCaseReference();
  //web elements that used selenium elements for test coverage
  @Getter
  Set<WebElement> interactedPageElements = new HashSet<>();
  // web elements that successfuly performed selenium action
  @Getter
  Set<WebElement> landedElements = new HashSet<>();

  public void setTestName(String testName){
    listeners.setTestName(testName);
    listeners.setDriver(driver);
  }
  void hasLanded(boolean landed, WebElement webElement) {
    if (landed) {
      landedElements.add(webElement);
    }
  }

  void logStep(String message) {
    logger.info(message);
  }

  void setListener(SeleniumAction action) {
    listeners.actionReceived(new SeleniumActionEvent(this, action));
  }

  void setWebElement(WebElement element) {
    listeners.setWebElement(element);
  }

  void setWebElementName(String name) {
    listeners.setWebElementName(name);
  }
}

