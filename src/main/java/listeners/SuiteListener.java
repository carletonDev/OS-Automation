package listeners;

import generalUtilities.DeleteRunner;
import java.io.File;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import restAssuredUtilities.Reporting;
import seleniumUtilities.Recorder;

public class SuiteListener implements ISuiteListener {

  protected int interactedElementCount = 0;
  protected int landingElementsCount = 0;

  @Override
  public void onStart(ISuite suite) {
    Reporting.startReporting();
    deleteRecordings();
  }

  @Override
  public void onFinish(ISuite suite) {
    calcWebElementCoverage(landingElementsCount, interactedElementCount);
//    EmailOutput.sendEmailableReport(suite.getParameter("from")
//        , suite.getParameter("password")
//        , suite.getParameter("to")
//        , suite.getParameter("cc")
//        , suite.getParameter("reportPath"));
    Reporting.endReporting();

  }

  private void calcWebElementCoverage(int landingElementsCount, int interactedElementCount) {
    float elementCoverage = (landingElementsCount * 100f / interactedElementCount);
    Reporting.report.setSystemInfo("Web Element Coverage", String.valueOf(elementCoverage));
    Reporting.report
        .setSystemInfo("Total Web Elements Sucuessfully Landed",
            String.valueOf(landingElementsCount));
    Reporting.report
        .setSystemInfo("Total Web Elements Interacted With",
            String.valueOf(interactedElementCount));

  }


  private void deleteRecordings() {
    new DeleteRunner().deleteFiles(new File(Recorder.targetFolder));
  }

}
