package restAssuredUtilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Generic {

  private static PrintStream ps;
  private static String logFilePath;

  public static void logPrintStream(String testCaseName) throws FileNotFoundException {
    logFilePath =
        Generic.getCurrentDirectory() + "\\src\\main\\java\\result" + File.separator + testCaseName + ".html";

    File file = new File(logFilePath);
    FileOutputStream fos = new FileOutputStream(file, true);
    ps = new PrintStream(fos);
  }

  public static PrintStream getPrintStream() {
    return ps;
  }

  public static String getLogFilePath() {
    return logFilePath;
  }

  public static String getCurrentDirectory() {
    Path root = Paths.get(".").normalize().toAbsolutePath();
    return root.toString();
  }

  public static String getExcelFilePath(String name) {
    return Generic.getCurrentDirectory() + "\\src\\main\\java\\excelWorkBooks\\" + name
        + ".xls";
  }
}