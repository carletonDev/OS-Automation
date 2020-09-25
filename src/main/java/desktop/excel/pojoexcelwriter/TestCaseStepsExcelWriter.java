package desktop.excel.pojoexcelwriter;

import desktop.database.pojos.TestCaseSteps;
import desktop.excel.ExcelFileNames;
import desktop.excel.ExcelWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import restAssuredUtilities.Generic;

public class TestCaseStepsExcelWriter extends ExcelWriter {

  private List<TestCaseSteps> testCaseSteps;
  private TestCaseSteps testCaseStep;

  /**
   * Creates a ExcelWriter object based on filePath and desired file extension
   */
  public TestCaseStepsExcelWriter(TestCaseSteps testCaseStep) {
    super(Generic.getExcelFilePath(ExcelFileNames.testFramework.name()), ExcelFileNames.testCaseSteps.name());
    this.testCaseStep = testCaseStep;
    setHeaders();
    writeTestCase(testCaseStep);
    writeToWorkBook();
  }

  public TestCaseStepsExcelWriter(List<TestCaseSteps> testCaseSteps) {
    super(Generic.getExcelFilePath(ExcelFileNames.testFramework.name()), ExcelFileNames.testCaseSteps.name());
    this.testCaseSteps = testCaseSteps;
    writeTestCases(testCaseSteps);
    writeToWorkBook();
  }

  //for gui spreadsheet creation;
  public TestCaseStepsExcelWriter(String sheetName) {
    super(Generic.getExcelFilePath(ExcelFileNames.testFramework.name()), sheetName);
    setHeaders();
    writeToWorkBook();

  }

  private void writeTestCases(List<TestCaseSteps> testCaseSteps) {
    if (testCaseSteps != null) {
      for (TestCaseSteps testCaseStep : testCaseSteps) {
        writeTestCase(testCaseStep);
      }
    }
  }

  private void writeTestCase(TestCaseSteps testCaseStep) {
    writeNewCells(createNewRow(), testCaseStep.getTestCase().getTestCaseName(),
        testCaseStep.getSteps().getAction());
  }

  private void setHeaders() {
    Field[] headers = TestCaseSteps.class.getDeclaredFields();
    ArrayList<String> headerNames = new ArrayList<>();
    for (Field field : headers) {
      if (!field.getName().equals("teststepsId") && !field.getName().equals("serialVersionUID")) {
        if (field.getName().contains("pojos.Steps")) {
          headerNames.add(ExcelFileNames.step.name());
        } else if (field.getName().contains("pojos.TestCase")) {
          headerNames.add(ExcelFileNames.testCase.name());
        } else {
          headerNames.add(field.getName());
        }
      }
    }
    createHeader(headerNames);
  }
}
