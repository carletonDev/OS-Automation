package poms;

import listeners.generalSeleniumActionEvent.SeleniumAction;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class YouTube extends BasePom {

	public static String pageTitle = "Youtube";
	@FindBy(css="paper-button#button.style-suggestive")
	 WebElement loginButton;
	
	public boolean loginButtonIsDisplayed() {
		//set web element for screenshot
		setWebElement(loginButton);
		//set Web Element Name for reporting
		setWebElementName("loginButton");
		//perform step
		seleniumUtilities.GeneralSeleniumActions.highlightElement(driver, loginButton);
		//set listener to take screenshots and reporting based on step
		setListener(SeleniumAction.highlightElement);
		//add web element to set of interacted elements during step method
		interactedPageElements.add(loginButton);
		//check if web element has landed
		hasLanded(loginButton.isDisplayed(),loginButton);
		//return for assert
		return loginButton.isDisplayed();
	}
		
	public YouTube(WebDriver driver,String testName) {
		//set driver
		this.driver = driver;
		//name pom for gui
		//set Test Name
		setTestName(testName);
		//init pom
		PageFactory.initElements(driver, this);
	}
}
