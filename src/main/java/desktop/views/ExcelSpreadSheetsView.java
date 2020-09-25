package desktop.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.router.Route;
import desktop.database.pojos.Steps;
import desktop.database.pojos.Suite;
import desktop.database.pojos.TestCase;
import desktop.database.pojos.TestCaseSteps;
import desktop.database.services.MapToPojos;
import desktop.excel.ExcelFileNames;
import desktop.excel.dataDrive.ClassParser.ClassParserException;
import desktop.excel.dataDrive.ClassParser.DataNotAvailableException;
import desktop.excel.dataDrive.ExcelDataDrive;
import desktop.views.layout.POMLayout;
import java.io.File;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import restAssuredUtilities.Generic;


@Route(value = "spreadsheets", layout = POMLayout.class)
public class ExcelSpreadSheetsView extends VerticalLayout implements Serializable {

  public static final String SPREAD_SHEET = "spreadSheet";
  private static final long serialVersionUID = -1600610306570057010L;
  private ComboBox<ExcelFileNames> selector = new ComboBox<>();
  private Button select = new Button("Select SpreadSheet");
  private Grid spreadsheet;
  private HorizontalLayout layout = new HorizontalLayout();
  private ExcelFileNames name;
  private File workBook;
  private MapToPojos mapper;

  public ExcelSpreadSheetsView(MapToPojos mapper) {
    this.mapper=mapper;
    setComboBox();
    setButton();
    layout.setSpacing(true);
    layout.add(selector, select);
    add(layout);
    this.setMargin(true);
    this.setSpacing(true);
    this.setSizeUndefined();
  }

  private void setComboBox() {
    selector.setLabel("Select ExcelFile");
    selector.setDataProvider(DataProvider.ofCollection(Arrays.asList(ExcelFileNames.values())));
  }

  private void setButton() {
    select.addThemeVariants(ButtonVariant.MATERIAL_CONTAINED);
    select.addClickListener(buttonClickEvent -> displaySpreadSheet());
  }

  private void displaySpreadSheet() {
    if (selector.getValue() != null) {
      this.name = selector.getValue();
      Optional<Component> check =
          this.getChildren().filter(component -> Objects
              .equals(component.getId().orElse(null), SPREAD_SHEET)).findAny();
      if (check.isPresent()) {
        removeSpreadSheet(check.get());
      } else {
        display(selector.getValue());
      }
    } else {
      Notification notify = new Notification("No Workbook Selected");
      notify.addThemeVariants(NotificationVariant.LUMO_ERROR);
      notify.setPosition(Position.BOTTOM_END);
      notify.open();
    }
  }

  private void removeSpreadSheet(Component check) {
    this.remove(check);
    display(selector.getValue());
  }


  private void display(ExcelFileNames name) {
    try {
      File workbook = new File(Generic.getExcelFilePath(name.name()));
      if (workbook.exists()) {
        createListDataProvider(workbook, name);
        this.workBook = workbook;
      } else {
        setSpreadSheet(name);
      }
    } catch (ClassParserException | DataNotAvailableException e) {
      e.printStackTrace();
    }
  }

  private void createListDataProvider(File workbook, ExcelFileNames name)
      throws ClassParserException, DataNotAvailableException {
    ExcelDataDrive dataDrive = new ExcelDataDrive(workbook);
    DataProvider dataProvider = null;
    switch (name) {

      case suite:
        dataProvider = DataProvider.ofCollection(dataDrive.getClassObjectList(Suite.class));
        spreadsheet = new Grid<>(Suite.class);
        break;
      case step:
        dataProvider = DataProvider.ofCollection(dataDrive.getClassObjectList(Steps.class));
        spreadsheet = new Grid<>(Steps.class);
        break;
      case testCase:
        dataProvider = DataProvider.ofCollection(mapper.maptoTestCasePojo(dataDrive.getData()));
        spreadsheet = new Grid<>(TestCase.class);
        break;
      case testCaseSteps:
        dataProvider = DataProvider
            .ofCollection(mapper.maptoTestCaseStepsPojo(dataDrive.getData()));
        spreadsheet = new Grid<>(TestCaseSteps.class);
        break;
    }
    setGrid(spreadsheet, dataProvider);
    add(spreadsheet);
  }


  private void setSpreadSheet(ExcelFileNames name) {
    switch (name) {

      case suite:
        spreadsheet = new Grid<>(Suite.class);
        break;
      case step:
        spreadsheet = new Grid<>(Steps.class);
        break;
      case testCase:
        spreadsheet = new Grid<>(TestCase.class);
        break;
      case testCaseSteps:
        spreadsheet = new Grid<>(TestCaseSteps.class);
        break;
    }

    setGrid(spreadsheet, null);
    add(spreadsheet);
  }

  private void setGrid(Grid spreadsheet, DataProvider dataProvider) {
    spreadsheet.addThemeVariants(GridVariant.MATERIAL_COLUMN_DIVIDERS);
    spreadsheet.setId(SPREAD_SHEET);
    if (dataProvider != null) {
      spreadsheet.setDataProvider(dataProvider);
    }

  }
}

