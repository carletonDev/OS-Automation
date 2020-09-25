package desktop.excel.pojoexcelwriter;

import desktop.database.pojos.Suite;
import desktop.excel.ExcelFileNames;
import desktop.excel.ExcelWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import restAssuredUtilities.Generic;

public class SuiteExcelWriter extends ExcelWriter {

  private Suite suite;
  private List<Suite> suites;

  private Logger logger = LogManager.getLogger(SuiteExcelWriter.class);

  /**
   * Creates a ExcelWriter object based on filePath and desired file extension and writes Suite or
   * List of Suites to Excel Sheet
   */
  public SuiteExcelWriter(Suite suite) {
    super(Generic.getExcelFilePath(ExcelFileNames.testFramework.name()), ExcelFileNames.suite.name());
    this.suite = suite;
    setHeaders();
    logger.info(this.suite);

    writeSuite(suite);
    writeToWorkBook();


  }

  public SuiteExcelWriter(List<Suite> suites) {
    super(Generic.getExcelFilePath(ExcelFileNames.testFramework.name()), ExcelFileNames.suite.name());
    this.suites = suites;
    setHeaders();
    logger.info("writing suites to test framework workbook");
    writeSuite(suites);
    writeToWorkBook();
  }


  private void writeSuite(List<Suite> suites) {
    if(suites !=null) {
      for (Suite suite : suites) {
        writeSuite(suite);
      }
    }
  }

  private void writeSuite(Suite suite) {
    writeNewCells(createNewRow(), suite.getSuiteName(), suite.getApplicationType(),
        suite.getEnvironment(),
        suite.getZapi().toString(), suite.getEnabled().toString());
  }



  private void setHeaders() {
    Field[] headers = Suite.class.getDeclaredFields();
    ArrayList<String> headerNames = new ArrayList<>();
    for (Field field : headers) {
      if (!field.getName().equals("suiteId") && !field.getName().equals("serialVersionUID")) {
        headerNames.add(field.getName());
      }
    }
    createHeader(headerNames);
  }
}
