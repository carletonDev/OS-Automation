package desktop.excel.dataDrive;

import desktop.excel.ExcelFileNames;
import desktop.excel.ExcelReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.Getter;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import restAssuredUtilities.Generic;

public class ExcelDataDrive extends BaseDataDrive {

  private final File file;
  private final Logger logger = LogManager.getLogger(ExcelDataDrive.class);
  @Getter
  private  InputStream stream;
  ExcelReader excelReader;
  public ExcelDataDrive(String filePath) {
    this(new File(Generic.getExcelFilePath(ExcelFileNames.testFramework.name())), filePath);
  }

  public ExcelDataDrive(File file) {
    this.file = file;
    try {
      this.stream= FileUtils.openInputStream(file);
      excelReader = new ExcelReader(stream);
    } catch (IOException e) {
      throw new DataDriveException(e);
    }
  }



  public ExcelDataDrive(File file, int sheetNo) {
    this.file = file;
    try {
      this.stream= FileUtils.openInputStream(file);
      excelReader = new ExcelReader(stream, sheetNo);
    } catch (IOException e) {
      throw new DataDriveException(e);
    }

  }

  private ExcelDataDrive(File file, String sheetName) {
    this.file = file;
    try {
      if (file.exists()) {
        this.stream= FileUtils.openInputStream(file);
        excelReader = new ExcelReader(stream, sheetName);

      }
    } catch (IOException e) {
      throw new DataDriveException(e);
    }
  }

  public File getFile() {
    return file;
  }

  @Override
  public Map<String, List<String>> getData() {
    AtomicInteger count = new AtomicInteger();
    HashMap<String, List<String>> data = new HashMap<>();
    int rowCount = excelReader != null ? excelReader.getNoOfRows() : BigDecimal.ZERO.intValue();
    int zeroRowColumns =
        excelReader != null ? excelReader.getNoOfColumn() : BigDecimal.ZERO.intValue();
  if(zeroRowColumns !=BigDecimal.ZERO.intValue()) {
    for (int column = count.get(); column < zeroRowColumns; column++) {
      String key = excelReader.getData(count.get(), column);
      List<String> valueList = new ArrayList<>();

      for (int row = BigDecimal.ONE.intValue(); row < rowCount; row++) {
        String value = excelReader.getData(row, column);
        valueList.add(value);
      }
      data.put(key, valueList);
    }
  }
    // TODO Auto-generated method stub
    return data;
  }

}