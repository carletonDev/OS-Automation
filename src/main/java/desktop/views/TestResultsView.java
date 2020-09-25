package desktop.views;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import desktop.views.layout.POMLayout;
import desktop.views.layout.TestResultReport;
import java.io.Serializable;

@PageTitle("Test Results")
@Route(value = "results", layout = POMLayout.class)
public class TestResultsView extends VerticalLayout implements Serializable {

  private static final long serialVersionUID = 6994596312015179551L;
  TestResultReport report = new TestResultReport();

  public TestResultsView() {
    this.setSizeFull();
    this.setAlignItems(Alignment.AUTO);
    report.setId("report");
    add(report);
  }


}
