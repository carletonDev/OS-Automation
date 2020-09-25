package desktop.excel.pojoexcelwriter;

import desktop.database.pojos.Steps;
import desktop.excel.ExcelFileNames;
import desktop.excel.ExcelWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import restAssuredUtilities.Generic;

public class StepsExcelWriter extends ExcelWriter {

  private List<Steps> steps;
  private Steps step;
  private Logger logger = LogManager.getLogger(StepsExcelWriter.class);

  /**
   * Creates a ExcelWriter object based on filePath and desired file extension
   */
  public StepsExcelWriter(Steps step) {
    super(Generic.getExcelFilePath(ExcelFileNames.testFramework.name()),
        ExcelFileNames.step.name());
    this.step = step;
    setHeaders();
    writeStep(step);
    writeToWorkBook();
  }

  public StepsExcelWriter(List<Steps> steps) {
    super(Generic.getExcelFilePath(ExcelFileNames.testFramework.name()),
        ExcelFileNames.step.name());
    this.steps = steps;
    setHeaders();
    writeSteps(steps);
    writeToWorkBook();
  }

  //for gui spreadsheet creation;
  public StepsExcelWriter() {
    super(Generic.getExcelFilePath(ExcelFileNames.step.name()), ExcelFileNames.step.name());
    setHeaders();
    writeToWorkBook();

  }

  private void writeSteps(List<Steps> steps) {
    if (steps != null) {
      for (Steps step : steps) {
        writeStep(step);
      }
    }
  }

  private void writeStep(Steps step) {
    writeNewCells(createNewRow(), step.getScreenName(), step.getAction()
        , step.getKeyword(), String.valueOf(step.getParameter()));
  }

  private void setHeaders() {
    Field[] headers = Steps.class.getDeclaredFields();
    ArrayList<String> headerNames = new ArrayList<>();
    for (Field field : headers) {
      if (!field.getName().equals("stepId") && !field.getName().equals("serialVersionUID")) {
        headerNames.add(field.getName());
      }
    }
    createHeader(headerNames);
  }
}
