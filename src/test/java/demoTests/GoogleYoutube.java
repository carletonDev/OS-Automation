package demoTests;

import poms.Google;
import poms.YouTube;
import org.testng.ITestContext;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import seleniumUtilities.GeneralSeleniumActions;
import tests.BaseTest;


public class GoogleYoutube extends BaseTest {

  SoftAssert softAssert = new SoftAssert();

  @Test
  public void googleYoutubeTest(ITestContext testContext) {

    //Instantiating required POMS
    Google google = new Google(driver,testContext.getName());
    YouTube youtube = new YouTube(driver,testContext.getName());
    //Begin test steps
    boolean logoDisplayed = google.logoDisplayed();
    google.searchBoxSendText("YouTube");
  //  driver.get("http://www.youtube.com");
    google.clickYouTubeLink();
    boolean loginDisplayed = youtube.loginButtonIsDisplayed();
    //get count of interacted elements and landing elements for test
    interactedElements = google.getInteractedPageElements().size();
    landingElements = google.getLandedElements().size();
    //Ideally you won't use thread sleeps, the POM actions or selenium's implicit wait would take care of any required delays.
    //This is for demo purposes only, to show the click navigation to youtube.
    GeneralSeleniumActions.waitAction(4);
    //End of test steps
    //Begin Asserts
    softAssert.assertEquals(logoDisplayed, true);
    softAssert.assertEquals(loginDisplayed, true);
    softAssert.assertAll();
  }


}
