package desktop.database.services;

import desktop.database.pojos.Steps;
import desktop.database.pojos.Suite;
import desktop.database.pojos.TestCase;
import desktop.database.pojos.TestCaseSteps;
import desktop.excel.ExcelFileNames;
import desktop.excel.dataDrive.ClassParser;
import desktop.excel.dataDrive.ClassParser.ClassParserException;
import desktop.excel.dataDrive.ClassParser.DataNotAvailableException;
import desktop.excel.dataDrive.ExcelDataDrive;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@SuppressWarnings({"WeakerAccess", "FieldCanBeLocal", "unused"})
@Service
public class ExcelToDatabase {


  private final ClassParser parser;
  private final MapToPojos mapToPojos;
  private final RepositoryFactory repositoryFactory;
  private ExcelDataDrive excelDataDrive;

  public ExcelToDatabase(RepositoryFactory repositoryFactory
      , ClassParser parser
      , MapToPojos mapToPojos) {
    this.repositoryFactory = repositoryFactory;

    this.parser = parser;
    this.mapToPojos = mapToPojos;
  }


  public void insertSuitesFromWorkbook() {
    List<Suite> suites = getData(Suite.class);
    repositoryFactory.getSuiteRepository().saveAll(suites);
    repositoryFactory.getSuiteRepository().flush();
  }


  public void insertStepsFromWorkbook() {
    List<Steps> steps = getData(Steps.class);
    repositoryFactory.getStepsRepository().saveAll(steps);
    repositoryFactory.getStepsRepository().flush();
  }


  public void insertTestCaseFromWorkbook() throws ClassParserException, DataNotAvailableException {
    excelDataDrive = setDataDrive(ExcelFileNames.testCase);
    Map<String, List<String>> data = excelDataDrive.getData();
    List<TestCase> list = mapToPojos
        .maptoTestCasePojo(data, repositoryFactory.getSuiteRepository());
    repositoryFactory.getTestCaseRepository().saveAll(list);
    repositoryFactory.getTestCaseRepository().flush();
  }


  public void insertTestCaseStepsFromWorkBook()
      throws ClassParserException, DataNotAvailableException {
    excelDataDrive = setDataDrive(ExcelFileNames.testCaseSteps);
    Map<String, List<String>> data = excelDataDrive.getData();
    //getList of Test Cases and find Test Cases and Steps from Database  to parse into list matching excel id names
    List<TestCaseSteps> testCaseSteps = mapToPojos
        .maptoTestCaseStepsPojo(data, repositoryFactory.getTestCaseRepository(),
            repositoryFactory.getStepsRepository());
    //save list to database
    repositoryFactory.getTestCaseStepsRepository().saveAll(testCaseSteps);
    //flush connection
    repositoryFactory.getTestCaseStepsRepository().flush();

  }


  private ExcelDataDrive setDataDrive(ExcelFileNames name) {
    return new ExcelDataDrive(name.name());
  }


  private <T> List<T> getData(Class<T> entity) {
    List<T> list = new ArrayList<>();
    excelDataDrive = setExcelDataDrive(entity);

    try {
      list = excelDataDrive.getClassObjectList(entity);
    } catch (ClassParserException | DataNotAvailableException e) {
      e.printStackTrace();
    }
    return list;
  }

  private <T> ExcelDataDrive setExcelDataDrive(Class<T> entity) {
    if (entity.getName().contains("Suite")) {
      return new ExcelDataDrive(ExcelFileNames.suite.name());
    } else if (entity.getName().contains("Steps")) {
      return new ExcelDataDrive(ExcelFileNames.step.name());
    } else if (!entity.getName().contains("TestCaseSteps")) {
      return new ExcelDataDrive(ExcelFileNames.testCase.name());
    } else {
      return new ExcelDataDrive(ExcelFileNames.testCaseSteps.name());
    }
  }

}

