package poms;

import restAssuredUtilities.Generic;
import listeners.generalSeleniumActionEvent.SeleniumAction;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import seleniumUtilities.GeneralSeleniumActions;

public class Google extends BasePom {

  @FindBy(name = "q")
  public WebElement searchBox;
  //title of the page as shown in the Tab set for gui write page title like this
  public static String pageTitle = "Google";
  //FindBy annotation allows the page factory to recognize the WebElement variables and
  //provide the appropriate path type and value.
  @FindBy(css = "[src*='logo']")
  WebElement googleLogo;
  @FindBy(linkText = "YouTube")
  WebElement ytLinkText;
  @FindBy(css = "div.r>a[href='https://www.youtube.com/']")
  WebElement ytLinkCSS;

  public Google(WebDriver driver,String testName) {
    this.driver = driver;
    setTestName(testName);
    PageFactory.initElements(driver, this);
  }


  public boolean logoDisplayed() {
      interactedPageElements.add(googleLogo);
    setWebElementName("googleLogo");
    Generic.getPrintStream()
        .println("<b>Validating google logo is displayed... (custom html)</b><br>");
    GeneralSeleniumActions.highlightElement(driver,googleLogo);
    setListener(SeleniumAction.highlightElement);
  //  interactedPageElements.add(googleLogo);
    hasLanded(googleLogo.isDisplayed(), googleLogo);
    if(googleLogo.isDisplayed()){
      setListener(SeleniumAction.isDisplayed);
    }
    return googleLogo.isDisplayed();
  }

  public void searchBoxSendText(String text) {
    setWebElementName("searchBox");
    Generic.getPrintStream().println("<b>Sending text to search box... (custom html)</b><br>");
    interactedPageElements.add(searchBox);
    seleniumUtilities.GeneralSeleniumActions.highlightElement(driver,searchBox);
    setListener(SeleniumAction.highlightElement);
    seleniumUtilities.GeneralSeleniumActions
        .attemptSendKeys(driver, searchBox, text + Keys.ENTER, false);
    setListener(SeleniumAction.attemptSendKeys);
    hasLanded(true, searchBox);
  }

  public void searchBoxClearAndSend(String text) {
    setWebElementName("searchBox");
    Generic.getPrintStream()
        .println("<b>Clearing and Sending text to search box... (custom html)</b><br>");
    interactedPageElements.add(searchBox);
    seleniumUtilities.GeneralSeleniumActions
        .attemptSendKeys(driver, searchBox, text + Keys.ENTER, true);
    hasLanded(true, searchBox);
  }

  public void clickYouTubeLink() {
    setWebElementName("ytLinkCSS");
    interactedPageElements.add(ytLinkCSS);
    Generic.getPrintStream().println("<b>Clicking YouTube Link... (custom html)</b><br>");
    GeneralSeleniumActions.highlightElement(driver, ytLinkCSS);
    setListener(SeleniumAction.highlightElement);
    hasLanded(true,ytLinkCSS);
    seleniumUtilities.GeneralSeleniumActions.attemptClick(ytLinkCSS);
    setListener(SeleniumAction.attemptClick);
  }
}