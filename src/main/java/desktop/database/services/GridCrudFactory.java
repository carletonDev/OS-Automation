package desktop.database.services;

import static desktop.views.MainView.GRID_CRUD;

import com.vaadin.flow.component.button.ButtonVariant;
import desktop.database.pojos.Steps;
import desktop.database.pojos.Suite;
import desktop.database.pojos.TestCase;
import desktop.database.pojos.TestCaseSteps;
import desktop.excel.dataDrive.ClassParser.ClassParserException;
import desktop.excel.dataDrive.ClassParser.DataNotAvailableException;
import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudListener;
import org.vaadin.crudui.crud.impl.GridCrud;
import org.vaadin.crudui.form.impl.field.provider.ComboBoxProvider;
import org.vaadin.crudui.layout.impl.HorizontalSplitCrudLayout;

@Service
public class GridCrudFactory {

  private final CrudListenerService crudListenerService;
  private final ExcelToDatabase excelToDatabase;
  private final RepositoryFactory repositoryFactory;


  public GridCrudFactory(CrudListenerService crudListenerService,
      ExcelToDatabase excelToDatabase, RepositoryFactory repositoryFactory) {
    this.crudListenerService = crudListenerService;

    this.excelToDatabase = excelToDatabase;
    this.repositoryFactory = repositoryFactory;

  }

  public GridCrud<Suite> createSuiteCrud() {
    GridCrud<Suite> suite = createCrud(Suite.class);
    suite.getCrudFormFactory().setDisabledProperties("suiteId");
    return suite;
  }

  public GridCrud<Steps> createStepsCrud() {
    GridCrud<Steps> steps = createCrud(Steps.class);
    steps.getCrudFormFactory().setDisabledProperties("stepId");
    return steps;
  }

  public GridCrud<TestCase> createTestCaseCrud() {
    excelToDatabase.insertSuitesFromWorkbook();
    GridCrud<TestCase> testCaseGridCrud = createCrud(TestCase.class);
    testCaseGridCrud.getCrudFormFactory().setFieldProvider("suite",
        new ComboBoxProvider<>(repositoryFactory.getSuiteRepository().findAll()));
    testCaseGridCrud.getCrudFormFactory().setDisabledProperties("testCaseId");
    return testCaseGridCrud;
  }

  public GridCrud<TestCaseSteps> createTestCaseStepsCrud() {
    excelToDatabase.insertStepsFromWorkbook();
    try {
      excelToDatabase.insertTestCaseFromWorkbook();
    } catch (ClassParserException | DataNotAvailableException e) {
      e.printStackTrace();
    }
    GridCrud<TestCaseSteps> testCaseStepsGridCrud = createCrud(TestCaseSteps.class);
    testCaseStepsGridCrud.getCrudFormFactory().setFieldProvider("testCase",
        new ComboBoxProvider<>(repositoryFactory.getTestCaseRepository().findAll()));
    testCaseStepsGridCrud.getCrudFormFactory().setFieldProvider("steps",
        new ComboBoxProvider<>(repositoryFactory.getStepsRepository().findAll()));
    return testCaseStepsGridCrud;
  }

  private <T> GridCrud<T> createCrud(Class<T> entity) {

    GridCrud<T> crud = new GridCrud<>(entity, new HorizontalSplitCrudLayout());
    crud.setId(GRID_CRUD);
    crud.setCrudListener(setCrudListener(entity));
    crud.setDeletedMessage(entity.getSimpleName() + " Deleted!");
    crud.setId("gridCrud");
    crud.getAddButton().addThemeVariants(ButtonVariant.MATERIAL_CONTAINED);
    crud.getDeleteButton().addThemeVariants(ButtonVariant.LUMO_ERROR);
    crud.getUpdateButton().setEnabled(true);
    crud.getDeleteButton().setEnabled(true);
    crud.setSavedMessage(entity.getSimpleName() + " Saved!");
    return crud;
  }


  private <T> CrudListener<T> setCrudListener(Class<T> entity) {
    return crudListenerService.setCrudListener(entity);
  }

}
