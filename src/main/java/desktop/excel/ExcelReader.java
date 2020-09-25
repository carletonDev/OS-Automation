package desktop.excel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;


/**
 * Utility to read Excel files. This file makes use of apache poi for reading excel files. It
 * supports both "xls" and "xlsx" file extension.
 *
 * @author Varun Menon
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class ExcelReader {

  public static int noOfSheet = 0;
  private Cell openCell;
  private Row openRow;
  private Sheet openSheet;
  private Workbook openWorkbook;
  private Map<String, List<String>> storedData = new LinkedHashMap<>();
  private Logger logger = LogManager.getLogger(ExcelReader.class);

  /**
   * Creates a ExcelReader object based on filePath
   *
   * @param filePath File path of the file to be opened
   */
  public ExcelReader(String filePath) throws
      IOException {
    this(new File(filePath));
  }

  /**
   * Creates a ExcelReader object based on <code>File</code> object passed
   *
   * @param file <code>File</code> object of the file to be opened.
   */
  public ExcelReader(File file) throws IOException {
    this.openFile(file, 0);
  }

  public ExcelReader(InputStream fileStream) throws IOException {
    this.openFile(fileStream, 20);
  }

  /**
   *
   */
  public ExcelReader(String filePath, int sheetNo) throws IOException {
    this.openFile(filePath, sheetNo);
  }

  public ExcelReader(InputStream fileStream, int sheetNo) throws IOException {
    this.openFile(fileStream, sheetNo);
  }

  /**
   *
   */
  public ExcelReader(String filePath, String sheetName) throws IOException {
    this.openFile(filePath, sheetName);
  }

  public ExcelReader(InputStream fileStream, String sheetName) throws IOException {
    this.openFile(fileStream, sheetName);
  }

  /**
   *
   */
  public ExcelReader(File file, int sheetNo) throws IOException {
    this.openFile(file, sheetNo);
  }

  /**
   *
   */
  public ExcelReader(File file, String sheetName) throws IOException {
    this.openFile(file, sheetName);
  }

  /**
   *
   */
  public ExcelReader() {
  }

  /**
   *
   */
  public void openFile(File file, int sheetNo) throws IOException {
    this.openWorkbook(file);
    openSheet = openWorkbook.getSheetAt(sheetNo);
  }


  public void openFile(InputStream fileStream, int sheetNo) throws IOException {
    this.openWorkbook(fileStream);
    openSheet = openWorkbook.getSheetAt(sheetNo);

  }

  /**
   *
   */
  public void openFile(String filePath, int sheetNo)
      throws IOException {
    this.openFile(new File(filePath), sheetNo);
  }

  public void openFile(File file, String sheetName)
      throws IOException {
    this.openWorkbook(file);
    openSheet = openWorkbook.getSheet(sheetName);
  }

  public void openFile(String filePath, String sheetName)
      throws IOException {
    this.openWorkbook(filePath);
    openSheet = openWorkbook.getSheet(sheetName);
  }

  public void openFile(InputStream fileStream, String sheetName)
      throws IOException {
    this.openWorkbook(fileStream);
    openSheet = openWorkbook.getSheet(sheetName);
  }

  private void openWorkbook(String filePath) throws
      IOException {
    this.openWorkbook(new File(filePath));
  }

  private void openWorkbook(File file) throws
      IOException {
    openWorkbook = WorkbookFactory.create(file);

  }

  private void openWorkbook(InputStream fileStream) throws
      IOException {

    openWorkbook = WorkbookFactory.create(fileStream);

  }

  public void openSheet(int sheetNo) {
    openSheet = openWorkbook.getSheetAt(sheetNo);
  }

  public void openSheet(String sheetName) {
    openSheet = openWorkbook.getSheet(sheetName);
  }

  public Workbook getOpenWorkbook() {
    return openWorkbook;
  }

  /**
   * Gets the data from the currently opened sheet based on row and column number
   *
   * @param row Row no. from which the value has to be fetched
   * @param column Respective column no. in the row from which the value has to be fetched
   * @return The data present in the respective row & column. If no value is found it returns and
   * empty String.
   */
  public String getData(int row, int column) {
    String data = "";
    try {

      openRow = openSheet.getRow(row);
      openCell = openRow.getCell(column);
      openCell.getCellType();
      switch (openCell.getCellType()) {

        case _NONE:
          if (DateUtil.isCellDateFormatted(openCell)) {
            Date dt = openCell.getDateCellValue();
            SimpleDateFormat sdf = new SimpleDateFormat(
                "dd MM yyyy HH:mm:ss");
            data = sdf.format(dt);
          } else {
            data = Long.toString(Math.round(openCell.getNumericCellValue()));
          }
          break;
        case NUMERIC:
          data = openCell.getRichStringCellValue().getString();
          break;
        case STRING:
          data = openCell.getRichStringCellValue().getString();
          break;
        case FORMULA:
          data = openCell.getRichStringCellValue().getString();
          break;
        case BLANK:
          data = openCell.getRichStringCellValue().getString();
          break;
        case BOOLEAN:
          data = Boolean.toString(openCell.getBooleanCellValue());
          break;
        case ERROR:
          data = Byte.toString(openCell.getErrorCellValue());
          break;
        default:
          data = openCell.getRichStringCellValue().getString();
          break;

      }
      if (data == null) {
        data = "";
      }
      return data;
    } catch (Exception e) {
      if (openRow == null || openCell == null) {
        data = "";
        return data;
      } else {
        System.out.println(e);
        return "";
      }
    }

  }

  /**
   * Gets the no. of rows in the currently opened sheet
   *
   * @return The actual no of physical rows present
   */
  public int getNoOfRows() {
    return openSheet != null ? openSheet.getPhysicalNumberOfRows() : BigDecimal.ZERO.intValue();
  }

  /**
   * Gets the no. of column present in the first row of the currently opened sheet.
   *
   * @return Return the no. of column present in the first row of the currently opened sheet.
   */
  public int getNoOfColumn() {
    return this.getNoOfColumn(new AtomicInteger().get());
  }

  /**
   * Gets the no. of column present in the specified row of the currently opened sheet.
   *
   * @param rowNo Row no. for which the no. of column have to evaluated.
   * @return Return the no. of column present in the specified row of the currently opened sheet.
   */
  public int getNoOfColumn(int rowNo) {
    if (openSheet != null) {
      return openSheet.getRow(rowNo).getPhysicalNumberOfCells();
    } else {
      return BigDecimal.ZERO.intValue();
    }

  }

  /**
   * Stores the whole data of the currently opened sheet in a Map containing keys
   */
  public void storeData() {
    Row rw;
    int rowCount = openSheet.getPhysicalNumberOfRows();
    storedData.clear();
    for (int i = 1; i < rowCount; i++) {
      rw = openSheet.getRow(i);
      String key = this.getData(0, i);

      List<String> valueList = new ArrayList<>();
      storedData.put(key, valueList);

      for (int j = 1; j <= rw.getPhysicalNumberOfCells(); j++) {
        String data = this.getData(j, i);
        valueList.add(data);
      }
    }
  }

  public Map<String, List<String>> getStoredData() {
    if (storedData.isEmpty()) {
      this.storeData();
    }
    return storedData;
  }
}

