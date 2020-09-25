package desktop.views;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import desktop.views.layout.POMLayout;
import desktop.views.viewBeans.POMSelect;
import java.io.Serializable;

@PageTitle("Create Test")
@Route(value = "test", layout = POMLayout.class)
public class CreateTest extends VerticalLayout implements Serializable {

  private static final long serialVersionUID = -7857655325404015873L;
  private ComboBox<POMSelect> selectPOM = new ComboBox<>();

  public CreateTest() {
    //setPom comboBox
    setPomComboBox();
    add(selectPOM);
  }

  private void setPomComboBox() {
    selectPOM.setDataProvider(getListDataProvider());
    selectPOM.setPlaceholder("Select POM");
    selectPOM.setItemLabelGenerator(this::setLabel);
  }

  private String setLabel(POMSelect pomSelect) {
    return pomSelect.getPomTitle();
  }

  private ListDataProvider<POMSelect> getListDataProvider() {
    return DataProvider.ofCollection(POMSelect.getAllPOMS());
  }
}
