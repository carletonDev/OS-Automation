package desktop.database.services;

import desktop.database.pojos.Steps;
import desktop.database.pojos.Suite;
import desktop.database.pojos.TestCase;
import desktop.database.pojos.TestCaseSteps;
import desktop.excel.ExcelFileNames;
import desktop.excel.dataDrive.ClassParser.ClassParserException;
import desktop.excel.dataDrive.ClassParser.DataNotAvailableException;
import desktop.excel.dataDrive.ExcelDataDrive;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudListener;
import restAssuredUtilities.Generic;

@Service
public class CrudListenerService {


  private final Logger logger = LogManager.getLogger(CrudListenerService.class);
  private final RepositoryFactory repositoryFactory;
  private File workbook;

  public CrudListenerService(RepositoryFactory repositoryFactory) {
    this.repositoryFactory = repositoryFactory;

  }

  <T> CrudListener<T> setCrudListener(Class<T> entity) {
    return new CrudListener<T>() {
      @Override
      public Collection<T> findAll() {
        return findAllGeneric(entity);
      }

      @Override
      public T add(T t) {
        save(t);
        return t;
      }

      @Override
      public T update(T t) {
        save(t);
        return t;
      }

      @Override
      public void delete(T t) {
        deleteType(t);
      }
    };
  }

  private <T> List<T> findAllGeneric(Class<T> entity) {
    List<T> list = new ArrayList<>();
    switch (entity.getName()) {
      case "desktop.database.pojos.Suite":

        list = getGenericList(repositoryFactory.getSuiteRepository(), entity);
        return list;

      case "desktop.database.pojos.Steps":

        list = getGenericList(repositoryFactory.getStepsRepository(), entity);
        return list;

      case "desktop.database.pojos.TestCase":
        list = getGenericList(repositoryFactory.getTestCaseRepository(), entity);
        return list;

      case "desktop.database.pojos.TestCaseSteps":

        list = getGenericList(repositoryFactory.getTestCaseStepsRepository(), entity);
        return list;
    }
    return list;
  }

  @SuppressWarnings("unchecked")
  private <T> List<T> getGenericList(JpaRepository repository, Class<T> entity) {

    if (checkListSize(repository.findAll())) {
      try {
        if (getExcelObjectList(entity) != null && !checkListSize(getExcelObjectList(entity))) {
          repository.saveAll(getExcelObjectList(entity));
        }
      } catch (ClassParserException | DataNotAvailableException e) {
        e.printStackTrace();
      }
    }
    return (List<T>) repository.findAll();
  }

  private <T> boolean checkListSize(List<T> all) {
    return all.size() == BigDecimal.ZERO.intValue();
  }

  private <T> void save(T entity) {

    switch (entity.getClass().getName()) {
      case "desktop.database.pojos.Suite":
        repositoryFactory.getSuiteRepository().save((Suite) entity);
        break;
      case "desktop.database.pojos.Steps":
        repositoryFactory.getStepsRepository().save((Steps) entity);
        break;
      case "desktop.database.pojos.TestCase":
        repositoryFactory.getTestCaseRepository().save((TestCase) entity);

        break;
      case "desktop.database.pojos.TestCaseSteps":
        repositoryFactory.getTestCaseStepsRepository().save((TestCaseSteps) entity);
        break;
    }
  }

  private <T> void deleteType(T entity) {
    switch (entity.getClass().getName()) {
      case "desktop.database.pojos.Suite":
        repositoryFactory.getSuiteRepository().delete((Suite) entity);
        break;
      case "desktop.database.pojos.Steps":
        repositoryFactory.getStepsRepository().delete((Steps) entity);
        break;
      case "desktop.database.pojos.TestCase":
        repositoryFactory.getTestCaseRepository().delete((TestCase) entity);
        break;
      case "desktop.database.pojos.TestCaseSteps":
        repositoryFactory.getTestCaseStepsRepository().delete((TestCaseSteps) entity);
        break;
    }
  }

  private <T> List<T> getExcelObjectList(Class<T> entity)
      throws ClassParserException, DataNotAvailableException {
    List<T> list = new ArrayList<>();
    ExcelDataDrive dataDrive;
    switch (entity.getName()) {
      case "desktop.database.pojos.Suite":
        dataDrive = getDataDrive(ExcelFileNames.suite);
        list = getList(dataDrive, entity);
        closeStream(dataDrive);
        break;
      case "desktop.database.pojos.Steps":
        dataDrive = getDataDrive(ExcelFileNames.step);
        list = getList(dataDrive, entity);
        closeStream(dataDrive);
        break;
      case "desktop.database.pojos.TestCase":
        dataDrive = getDataDrive(ExcelFileNames.testCase);
        list = getList(dataDrive, entity);
        closeStream(dataDrive);

        break;
      case "desktop.database.pojos.TestCaseSteps":
        dataDrive = getDataDrive(ExcelFileNames.testCaseSteps);
        list = getList(dataDrive, entity);
        closeStream(dataDrive);
        break;
    }
    return list;
  }

  private void closeStream(ExcelDataDrive dataDrive) {
    try {
      if(workbook.exists()) {
        dataDrive.getStream().close();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private <T> List<T> getList(ExcelDataDrive dataDrive, Class<T> entity)
      throws ClassParserException, DataNotAvailableException {
     workbook = new File(Generic.getExcelFilePath(ExcelFileNames.testFramework.name()));
    if (workbook.exists()) {
      return dataDrive.getClassObjectList(entity);
    } else {
      return new ArrayList<>();
    }
  }

  private ExcelDataDrive getDataDrive(ExcelFileNames workbookType) {
    return new ExcelDataDrive(workbookType.name());
  }

}

