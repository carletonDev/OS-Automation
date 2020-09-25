package seleniumUtilities;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.util.Date;

import restAssuredUtilities.Reporting;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.TargetLocator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.JavascriptExecutor;

public class GeneralSeleniumActions {

	// Element locator assistants
	private static final long MAXTIMEOUT_30 = 30;
	private static final long MAXTIMEOUT_10 = 10;
	private static final long MAXTIMEOUT_5 = 5;
	private static final long MAXTIMEOUT_3 = 3;
	private static final int TIMESPAN_5 = 5;
	private static final int TIMESPAN_2 = 2;
	private static final int TIMESPAN_1 = 1;

	/**
	 * @param driver driver
	 * @param element element to scroll to
	 */
	private static void scrollTo(WebDriver driver, WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView();", element);
	}

	// Handle Windows
	/**
	 * @param driver driver
	 * @param index  target window index. windows are indexed in the order they
	 *               are opened
	 */
	public static void windowSelect(WebDriver driver, int index) {
		boolean windowSelected = false;
		Date dateStart = new Date();
		long longCurrentWait;

		do {
			try{
				Object[] windows = driver.getWindowHandles().toArray();
				String strSelected = windows[index-1].toString();
				driver.switchTo().window(strSelected);
				windowSelected = true;
			}catch (Exception e) {
				waitAction(TIMESPAN_2);
				reportError("ERROR: Can't find the window");
			}

			Date currTime = new Date();
			longCurrentWait = Math.abs(currTime.getTime() - dateStart.getTime());
		}while(!windowSelected && longCurrentWait < MAXTIMEOUT_5 );
	}

	/**
	 * Closes the currently focused browser tab
	 */
	public static void closeCurrentWindow(WebDriver driver) {
		driver.close();
	}

	// File Management
	/**
	 * Upload a file to a native browser
	 *
	 * @param file absolute path to the file in local file system. Type String
	 * @return returns method status.
	 */
	public static boolean uploadFile(String file) {
		boolean boolSuccess = true;
		try {
			waitAction(TIMESPAN_1);

			Robot rBot = new Robot();
			StringSelection ss = new StringSelection(file);
			// Setting file path to clipboard so the Robot class can paste it into the open
			// OS dialog
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
			rBot.keyPress(KeyEvent.VK_CONTROL);
			rBot.keyPress(KeyEvent.VK_V);
			rBot.keyRelease(KeyEvent.VK_V);
			rBot.keyRelease(KeyEvent.VK_CONTROL);
			rBot.keyPress(KeyEvent.VK_ENTER);
			rBot.keyRelease(KeyEvent.VK_ENTER);
		} catch (Exception e) {
			boolSuccess = false;
		}
		return boolSuccess;
	}

	/**
	 * Upload a file by sending absolute file path directly to web element
	 *
	 * @param file    Absolute file path. Type String
	 * @param element Target web element
	 */
	public static void uploadFile(String file, WebElement element) {
		element.sendKeys(file);
	}

	// Handle dialogs
	/**
	 * Accepts an html Dialog if one is present.
	 *
   */
	public static void acceptDialog(WebDriver driver) {
		boolean result = false;
		Date dateStart = new Date();
		long longCurrentWait;

		if (waitForDialog(driver)) {
			do{
				try{
					TargetLocator tlTarget = driver.switchTo();
					Alert alertTargetAlert = tlTarget.alert();
					alertTargetAlert.accept();
					result = true;
				}catch (Exception e) {
					waitAction(TIMESPAN_1);
 				}

				Date currentTime = new Date();
				longCurrentWait = Math.abs(currentTime.getTime() - dateStart.getTime());
			}while(!result && longCurrentWait < MAXTIMEOUT_5);
		} else {
			reportError("ERROR: Can't find a dialog");
		}
	}

	/**
	 * If dialog is present, dismiss dialog.
	 *
	 * @param driver WebDriver where dialog is located
	 */
	public static boolean dismissDialog(WebDriver driver) {
		boolean result = false;

		if (waitForDialog(driver)) {
			Date dateStart = new Date();
			long longCurrentWait;

			do{
				try {
					TargetLocator tlTarget = driver.switchTo();
					Alert alertTargetAlert = tlTarget.alert();
					alertTargetAlert.dismiss();
					result = true;
				}catch(Exception e) {
					waitAction(TIMESPAN_1);
				}

				Date currTime = new Date();
				longCurrentWait = Math.abs(currTime.getTime() - dateStart.getTime());

			}while(!result && longCurrentWait < MAXTIMEOUT_5);
		} else {
			reportError("ERROR: Can't find a dialog");
		}
		return result;
	}

	/**
	 * Waits for dialog presence for up to 4 seconds.
	 *
	 * @return boolean: True if dialog was found in the timeframe, False otherwise.
	 */
	public static boolean waitForDialog(WebDriver driver) {
		int intMaxWait = getIntValue(MAXTIMEOUT_5);
		int intWaitInterval = TIMESPAN_1;
		int intCurrentWait = 0;

		boolean boolFound;
		do {
			boolFound = dialogIsPresent(driver);
			intCurrentWait += intWaitInterval;
			waitAction(intWaitInterval);
		} while (!boolFound && intCurrentWait < intMaxWait);

		return boolFound;
	}

	/**
	 * Checks to see if a dialog is present.
	 *
	 * @return boolean: True if the dialog is found, False otherwise
	 */
	public static boolean dialogIsPresent(WebDriver driver) {
		boolean boolDialogFound = false;
		try {
			driver.switchTo().alert();
			boolDialogFound = true;
		} catch (Exception e) {
			reportError("Error: Can't find a dialog");
		}
		return boolDialogFound;
	}

	// Clicks

	/**
	 * Will attempt to click the provided WebElement
	 *
	 * @return boolean: True if click successful, false otherwise. Does not
	 *         guarantee expected results, only makes them more likely. Page
	 *         refresh/updates may still cause failure
	 */
	public static boolean attemptClick(WebElement element) {
		return executeClick(element, null,false);
	}

	/**
	 * Will attempt to perform a javascript click on the provided element. NOTE: Not
	 * an acceptable way to UI test! Last resort use only!
	 */
	public static boolean attemptJSClick(WebDriver driver, WebElement element) {
		return executeClick(element, driver, true);

	}
	//ThreadSleep
	/**
	 * @param seconds number in seconds
	 *
	 * will sleep current thread by specified number of seconds
	 */
	public static void waitAction(int seconds){
		try {
			Thread.sleep(seconds*1000);
		} catch (InterruptedException e) {
			reportError("Error occurred during Thread sleep");
		}
	}

	// SendKeys

	/**
	 * Will attempt to send the provided text to the provided WebElement. NOTE: Will
	 * also perform a clear on the element, and is not suitable for dropdowns or
	 * selectors!
	 */
	public static boolean attemptSendKeys(WebDriver driver, WebElement element, String text) {
		return executeSendKeys(driver, element, text, true, false);
	}


	/**
	 * Will attempt to send the provided text to the provided WebElement.
	 */
	public static boolean attemptSendKeysWithoutClear(WebDriver driver, WebElement element, String text) {
		return executeSendKeys(driver, element, text, false, true);
	}

	/**
	 * Will attempt to send the provided text to the provided WebElement. Clear
	 * parameter used to determine whether or not to attempt to clear the WebElement
	 * prior to inserting text
	 */
	public static boolean attemptSendKeys(WebDriver driver, WebElement element, String text, boolean clear) {
		return executeSendKeys(driver, element, text, clear, true);
	}

	// GetData
	/**
	 * Will attempt to retrieve text from the provided WebElement.
	 *
	 * @return String: any text retrieved from the provided WebElemetn
	 */
	public static String attemptGetText(WebDriver driver, WebElement element) {
		String strResponse = "";
		Date dateStart = new Date();
		long longCurrentWait;

		do{
			try {
				strResponse = element.getText();
			} catch (Exception e) {
				scrollTo(driver, element);
			}

			Date currentTime = new Date();
			longCurrentWait = Math.abs(currentTime.getTime() - dateStart.getTime());
		}while(!strResponse.isEmpty() &&longCurrentWait < MAXTIMEOUT_5);

		return strResponse;
	}

	/**
	 * Will attempt to get the value of a WebElement such as text using javascript.
	 * NOTE: to be used as a backup if attemptGetText does not provided expected
	 * results, i.e. not retrieving text from a textbox.
	 *
	 * @return String: value of the provided WebElement
	 */
	public static String attemptGetValue(WebDriver driver, WebElement element) {
		String strResponse = "";
		Date dateStart = new Date();
		long longCurrentWait;

		do{
			try {
				strResponse = ((JavascriptExecutor) driver).executeScript("return arguments[0].value;", element).toString();
			} catch (Exception e) {
				scrollTo(driver, element);
			}

			Date currentTime = new Date();
			longCurrentWait = Math.abs(currentTime.getTime() - dateStart.getTime());
		}while(!strResponse.isEmpty() &&longCurrentWait < MAXTIMEOUT_5);

		return strResponse;
	}

	// Waits
	/**
	 * Will wait up to 30 seconds for the page to be loaded. NOTE: does not function
	 * as expected on pages using jQuery or AJAX. Updated data using these methods
	 * will not be accounted for.
	 */
	public static void waitForPageLoaded(WebDriver driver) {
		new WebDriverWait(driver, MAXTIMEOUT_30).until((ExpectedCondition<Boolean>) wd -> ((JavascriptExecutor) wd)
				.executeScript("return document.readyState").equals("complete"));
	}

	/**
	 * Waits for an element to be clickable for up to 10 seconds
	 */
	public static void waitForElementClickable(WebDriver driver, WebElement element) {
		new WebDriverWait(driver, MAXTIMEOUT_10).until(ExpectedConditions.elementToBeClickable(element));
	}

	/**
	 * Waits for jQuery to be loaded. Partially successful
	 */
	public static boolean waitForJquery(WebDriver driver) {
		int intCurrentWaitMS = 0;
		int intMaxWaitMS = getIntValue(MAXTIMEOUT_3);
		int intPollMS = 500;

		boolean boolJQueryFound = false;
		boolean boolWaitResult = false;
		do {
			boolJQueryFound = (boolean) (((JavascriptExecutor) driver)
					.executeScript("return window.jQuery != undefined"));
			if (!boolJQueryFound) {
				waitAction(intPollMS);
				intCurrentWaitMS += intPollMS;
			}

		} while (!boolJQueryFound && intCurrentWaitMS < intMaxWaitMS);

		if (boolJQueryFound) {

			WebDriverWait wait = new WebDriverWait(driver, 30);
			ExpectedCondition<Boolean> conditionJQueryLoad = new ExpectedCondition<Boolean>() {

				@Override
				public Boolean apply(WebDriver driver) {
					try {
						return ((int) (((JavascriptExecutor) driver).executeScript("return jQuery.active"))) == 0;
					} catch (Exception ex) {
						return true;
					}
				}
			};

			waitAction(TIMESPAN_1);
			boolWaitResult = wait.until(conditionJQueryLoad);
		}
		return boolJQueryFound && boolWaitResult;
	}

	/**
	 * Highlights elements in the target webDriver
	 *
	 * @param driver WebDriver with elements
	 * @param elements List of elements to highlight
	 */
	public static void highlightElement(WebDriver driver, WebElement... elements) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		for (WebElement element : elements) {
			jse.executeScript("arguments[0].style.border='3px solid green'", element);
		}
	}

	/**
	 * Checks if an element exists
	 *
	 * @param driver WebDriver with element to find
	 * @param selector The locating mechanism
	 * @return True if the element exists. Otherwise, false
	 */
	public static boolean elementExists(WebDriver driver, By selector) {
		boolean result = false;
		Date dateStart = new Date();
		long longCurrentWait;

		do {
			try {
				driver.findElement(selector);
				result = true;
			} catch (Exception e) {
				waitAction(TIMESPAN_1);
			}

			Date currTime = new Date();
			longCurrentWait = Math.abs(currTime.getTime() - dateStart.getTime());
		}while(!result && longCurrentWait < MAXTIMEOUT_5);

		return result;
	}

	private static boolean executeClick(WebElement element, WebDriver driver, boolean isJSClick) {
		boolean success = false;
		Date dateStart = new Date();

		long longCurrentWait = 0;
		do {
			try {
				if(isJSClick){
					JavascriptExecutor executor = (JavascriptExecutor) driver;
					executor.executeScript("arguments[0].click();", element);
				}else{
					element.click();
				}
				success = true;

			} catch (Exception e) {
				waitAction(TIMESPAN_1);

				if(isJSClick) {
					scrollTo(driver, element);
				}

				Date currentTime = new Date();
				longCurrentWait = Math.abs(currentTime.getTime() - dateStart.getTime());
			}
		} while (!success && longCurrentWait < MAXTIMEOUT_5);
		return success;
	}

	private static boolean executeSendKeys(WebDriver driver, WebElement element, String text, boolean isClear, boolean scrollTo){
        boolean boolSent = false;
        Date dateStart = new Date();

        long longCurrentWait = 0;

        do{
            try{
                if(isClear) {
                    element.clear();
                }

                element.sendKeys(text);
                boolSent = true;
            }catch (Exception e){
				waitAction(TIMESPAN_1);

				if(scrollTo){
					scrollTo(driver, element);
				}

                Date currTime = new Date();
                longCurrentWait = Math.abs(currTime.getTime() - dateStart.getTime());
            }
        }while(!boolSent && longCurrentWait < MAXTIMEOUT_5);

        return boolSent;
    }

    private static void reportError(String text){
		Reporting.getTestCaseReference().info(text);
	}

	private static int getIntValue(long longValue) {
		return new Long(longValue).intValue();
	}

}