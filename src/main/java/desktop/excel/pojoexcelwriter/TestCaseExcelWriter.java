package desktop.excel.pojoexcelwriter;

import desktop.database.pojos.TestCase;
import desktop.excel.ExcelFileNames;
import desktop.excel.ExcelWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import restAssuredUtilities.Generic;

public class TestCaseExcelWriter extends ExcelWriter {

  private List<TestCase> testCases;
  private TestCase testCase;
  private Logger logger = LogManager.getLogger(StepsExcelWriter.class);

  /**
   * Creates a ExcelWriter object based on filePath and desired file extension
   */
  public TestCaseExcelWriter(TestCase testCase) {
    super(Generic.getExcelFilePath(ExcelFileNames.testFramework.name()), ExcelFileNames.testCase.name());
    this.testCase = testCase;
    setHeaders();
    writeTestCase(testCase);
    writeToWorkBook();
  }

  public TestCaseExcelWriter(List<TestCase> testCases) {
    super(Generic.getExcelFilePath(ExcelFileNames.testFramework.name()), ExcelFileNames.testCase.name());
    this.testCases = testCases;
    writeTestCases(testCases);
    writeToWorkBook();
  }

  //for gui spreadsheet creation;
  public TestCaseExcelWriter(String sheetName) {
    super(Generic.getExcelFilePath(ExcelFileNames.testCase.name()), sheetName);
    setHeaders();
    writeToWorkBook();

  }


  private void writeTestCases(List<TestCase> testCases) {
    if (testCases != null) {
      for (TestCase testCase : testCases) {
        writeTestCase(testCase);
      }
    }
  }

  private void writeTestCase(TestCase testCase) {
    writeNewCells(createNewRow(), testCase.getTestCaseName(), testCase.getDescription(),
        testCase.getBrowser(), testCase.getTestType(), testCase.getEnabled().toString(),
        testCase.getRecord().toString(), testCase.getSuite().getSuiteName());
  }

  private void setHeaders() {
    Field[] headers = TestCase.class.getDeclaredFields();
    ArrayList<String> headerNames = new ArrayList<>();
    for (Field field : headers) {
      if (!field.getName().equals("testCaseId") && !field.getName().equals("serialVersionUID")) {
        if(!field.getName().contains("Suite")) {
          headerNames.add(field.getName());
        }
        else{
          headerNames.add(ExcelFileNames.suite.name());
        }
      }
    }
    createHeader(headerNames);
  }

}
