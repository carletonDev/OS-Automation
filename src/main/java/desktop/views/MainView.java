package desktop.views;


import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import desktop.database.services.GridCrudFactory;
import desktop.database.services.RepositoryFactory;
import desktop.excel.pojoexcelwriter.StepsExcelWriter;
import desktop.excel.pojoexcelwriter.SuiteExcelWriter;
import desktop.excel.pojoexcelwriter.TestCaseExcelWriter;
import desktop.excel.pojoexcelwriter.TestCaseStepsExcelWriter;
import desktop.views.layout.POMLayout;
import desktop.views.viewBeans.PojoSelect;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.vaadin.crudui.crud.impl.GridCrud;

@SuppressWarnings("FieldCanBeLocal")
@Route(value = "", layout = POMLayout.class)
@PageTitle(value = "Home")
public class MainView extends VerticalLayout implements Serializable {

  public static final String GRID_CRUD = "gridCrud";
  private static final long serialVersionUID = 2455284396686002811L;
  private final GridCrudFactory factory;
  private final RepositoryFactory repositoryFactory;
  private Button button = new Button("Select Test Level");
  private Button write = new Button("Write To Excel");

  private ComboBox<PojoSelect> pojoSelectComboBox = new ComboBox<>();
  private GridCrud crud;

  public MainView(GridCrudFactory factory,
      RepositoryFactory repositoryFactory) {
    this.factory = factory;
    this.repositoryFactory = repositoryFactory;
    setPojoSelectComboBox();
    button.addClickListener(buttonClickEvent1 -> selectPojo());
    button.addThemeVariants(ButtonVariant.LUMO_SMALL);
    write.addThemeVariants(ButtonVariant.LUMO_SMALL);
    write.addClickListener(buttonClickEvent -> writeToExcel());
    HorizontalLayout layout = new HorizontalLayout();
    layout.add(pojoSelectComboBox, button, write);
    add(layout);
  }


  private void selectPojo() {
    if (pojoSelectComboBox.getValue() != null) {
      Optional<Component> check =
          this.getChildren().filter(component -> Objects
              .equals(component.getId().orElse(null), GRID_CRUD)).findAny();
      if (check.isPresent()) {
        removeCrudGrid(check.get());
      } else {
        setCrud();
      }
    } else {
      createErrorNotification();
    }
  }


  private void removeCrudGrid(Component check) {
    this.remove(check);
    setCrud();
  }

  private void createErrorNotification() {
    Notification notification = new Notification("Select Test Level");
    notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
    notification.setPosition(Position.BOTTOM_END);
    notification.open();
    if (notification.isOpened()) {
      notification.close();
    }
  }

  private void setCrud() {
    String name = pojoSelectComboBox.getValue().getPojoClass().getName();
    crud = null;
    switch (name) {
      case "desktop.database.pojos.Suite":
        crud = factory.createSuiteCrud();
        break;
      case "desktop.database.pojos.Steps":
        crud = factory.createStepsCrud();
        break;
      case "desktop.database.pojos.TestCase":
        crud = factory.createTestCaseCrud();
        break;
      case "desktop.database.pojos.TestCaseSteps":
        crud = factory.createTestCaseStepsCrud();
        break;
    }
    this.add(crud);

  }


  private void setPojoSelectComboBox() {
    pojoSelectComboBox.setPlaceholder("Select Class");
    List<PojoSelect> list = PojoSelect.getAllPojos();
    pojoSelectComboBox.setDataProvider(DataProvider.ofCollection(list));
    pojoSelectComboBox.setItemLabelGenerator(this::setLabel);
  }

  private String setLabel(PojoSelect pojoSelect) {
    String name = pojoSelect.getPojoClass().getName();
    if (name.contains("Suite")) {
      name = "Suite";
    } else if (name.contains("Steps") && !name.contains("TestCase")) {
      name = "Steps";
    } else if (name.contains("TestCase") && !name.contains("Steps")) {
      name = "Test Case";

    } else if (name.contains("TestCaseSteps")) {
      name = "Test Case Steps";
    }
    return name;
  }

  private void writeToExcel() {
    String name = pojoSelectComboBox.getValue() != null ?
        pojoSelectComboBox.getValue().getPojoClass().getName() : "";
    if (!name.isEmpty()) {

      switch (name) {
        case "desktop.database.pojos.Suite":
          new SuiteExcelWriter(repositoryFactory.getSuiteRepository().findAll());
          break;
        case "desktop.database.pojos.Steps":
          new StepsExcelWriter(repositoryFactory.getStepsRepository().findAll());
          break;
        case "desktop.database.pojos.TestCase":
          new TestCaseExcelWriter(repositoryFactory.getTestCaseRepository().findAll());
          break;
        case "desktop.database.pojos.TestCaseSteps":
          new TestCaseStepsExcelWriter(
              repositoryFactory.getTestCaseStepsRepository().findAll());
          break;
      }

    } else {
      Notification notification = new Notification("Select Test Level");
      notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
      notification.setPosition(Position.BOTTOM_END);
      notification.setDuration(3000);
      notification.open();

    }
  }
}
