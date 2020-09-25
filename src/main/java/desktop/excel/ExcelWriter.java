package desktop.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;


public class ExcelWriter {

  @Getter
  private AtomicInteger currRow = new AtomicInteger();
  private String sheetName;
  @Getter
  @Setter
  private String recordPath;
  private boolean fileExists;

  @Getter
  private File file;
  @Getter
  private HSSFWorkbook workbook;
  @Getter
  private HSSFSheet sheet;
  private FileInputStream inputStream;
  private Logger logger = LogManager.getLogger(ExcelWriter.class);


  /**
   * Creates a ExcelWriter object based on filePath and desired file extension
   * @param filePath File path of the file to be written into
   */
  public ExcelWriter(String filePath, String sheetName) {

    this.sheetName = sheetName;
    file = getFileIfExists(filePath);
    createSheet(sheetName);
  }


  private void createSheet(String sheetName) {
    // check if sheet with the same name exists
    if (!hasSameSheetName(sheetName)) {
      sheet = workbook.createSheet(sheetName);
    } else {
      sheet = workbook.getSheet(sheetName);
      removeSheetRows(sheetName);
    }
  }


  protected void createHeader(ArrayList<String> headers) {
    if (!fileExists) {
      sheet.createRow(currRow.get());
    }
    logger.info(currRow.get());
    AtomicInteger count = new AtomicInteger();
    headers.forEach(headerName -> {
          sheet.getRow(currRow.get()).createCell(count.get()).setCellValue(headerName);
          count.getAndIncrement();
        }
    );
  }

  private boolean hasSameSheetName(String sheetName) {
    boolean result = false;
    int numSheets = workbook.getNumberOfSheets();
    for (int i = 0; i < numSheets; i++) {
      if (workbook.getSheetAt(i).getSheetName().equalsIgnoreCase(sheetName)) {
        logger.info(workbook.getSheetAt(i).getSheetName() + " exists");
        result = true;
      }
    }

    return result;
  }

  protected Row createNewRow() {
    Row row = workbook.getSheet(sheetName).createRow(currRow.get());
    currRow.getAndIncrement();
    return row;
  }

  public void createNewRowWithMaxNum(String sheetName, int maxRow) {
    workbook.getSheet(sheetName).createRow(maxRow);
  }

  private void removeSheetRows(String sheetName) {
    logger.info(sheetName);
    for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
      if(workbook.getSheetAt(i).getSheetName().equals(sheetName)){
        workbook.removeSheetAt(i);
      }
    }
    writeToWorkBook();

    logger.info(currRow.get());
    currRow.set(BigDecimal.ZERO.intValue());
    createNewRow();
  }


  /**
   *
   */
  public void writeNewCell(Row row, int cell, String value) {
    row.createCell(cell).setCellValue(value);
  }

  protected void writeNewCells(Row row, String... values) {
    List<String> list = new ArrayList<>(Arrays.asList(values));
    AtomicInteger count = new AtomicInteger();
    list.forEach(cellValue -> {
      row.createCell(count.get()).setCellValue(cellValue);
      count.getAndIncrement();
    });
  }

  /**
   * Write into the workbook and close the stream
   */
  protected void writeToWorkBook() {
    try (FileOutputStream outputStream = FileUtils.openOutputStream(file)) {
      workbook.write(outputStream);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Create a file object with input stream if file exists. Otherwise, create a directory and the
   * file object
   */
  private File getFileIfExists(String path) {
    File excelFile = new File(path);
    if (!excelFile.exists()) {
      fileExists = false;
      workbook = new HSSFWorkbook();
    } else {
      fileExists = true;
      try {
        inputStream = new FileInputStream(excelFile);
        logger.info("file exists");
        workbook = new HSSFWorkbook(inputStream);
      } catch (IOException e) {
        e.printStackTrace();
      }

    }
    return excelFile;
  }

  protected void testRowsWrittenToSheet() {
    for (int x = 0; x < this.getSheet().getPhysicalNumberOfRows(); x++) {
      for (int i = 0; i < this.getSheet().getRow(x).getPhysicalNumberOfCells(); i++) {
        logger.info(this.getSheet().getRow(x).getCell(i));
      }
    }
  }
}
