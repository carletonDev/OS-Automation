package listeners;


import org.testng.ITestContext;
import restAssuredUtilities.Reporting;
import listeners.generalSeleniumActionEvent.SeleniumAction;
import listeners.generalSeleniumActionEvent.SeleniumActionEvent;
import lombok.Data;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import seleniumUtilities.Screenshots;

@Data
public class SeleniumAttemptActionListeners implements SeleniumActionListener {

  private String testName;
  private WebDriver driver;
  private WebElement webElement;
  private SeleniumAction action;
  private String webElementName;
  private ITestContext context;


  @Override
  public void actionReceived(SeleniumActionEvent event) {
    this.action = event.getAction();
    switch (event.getAction()) {
      case scrollTo:
        break;
      case windowSelect:
        break;
      case closeCurrentWindow:
        break;
      case uploadFilewithPath:
        break;
      case uploadFileToWebElement:
        break;
      case acceptDialog:
        break;
      case dismissDialog:
        break;
      case waitForDialog:
        break;
      case dialogIsPresent:
        break;
      case attemptClick:
        break;
      case attemptJSClick:
        break;
      case attemptSendKeys:
        break;
      case attemptSendKeysWithoutClear:
        break;
      case attemptSendKeysClearOption:
        break;
      case attemptGetText:
        break;
      case attemptGetValue:
        break;
      case waitForPageLoaded:
        break;
      case waitForElementClickable:
        break;
      case waitForJquery:
        break;
      case highlightElementSpreadElements:
        break;
      case highlightElement:
        break;
      case elementExists:
        break;
      case isDisplayed:
        break;
    }

    embedScreenshot();
    reportFrameworkAction();

  }

  private void embedScreenshot() {
    if (webElement != null) {
      Screenshots.embedScreenshot(driver, testName, webElement);
    } else {
      Screenshots.embedScreenshot(driver, testName);
    }
  }

  private void reportFrameworkAction() {
    Reporting.getTestCaseReference().info(
        "Performing action: " + action.name() + " on " + webElementName
    );
  }
}
