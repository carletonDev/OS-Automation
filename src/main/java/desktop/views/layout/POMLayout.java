package desktop.views.layout;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.page.BodySize;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.material.Material;
import desktop.views.CreateTest;
import desktop.views.ExcelSpreadSheetsView;
import desktop.views.MainView;
import desktop.views.TestResultsView;
import java.util.HashMap;
import java.util.Map;


@BodySize
@Theme(value = Material.class, variant = Material.DARK)
@PWA(name = "Onshore EZ Testing", shortName = "EZ Test")
@Viewport("width=device-width, minimum-scale=1, initial-scale=1, user-scalable=yes")
public class POMLayout extends AppLayout {

  private static final long serialVersionUID = -6857623592036948815L;
  private Tabs tabs = new Tabs();
  private Map<Class<? extends Component>, Tab> navigationTargetToTab = new HashMap<>();

  public POMLayout() {
    Image img = new Image("https://cs210032000b209ecd4.blob.core.windows.net/adma/onshore.ico",
        "Onshore");
    img.setHeight("44px");
    addMenuTab("Onshore EZ Testing", MainView.class);
    addMenuTab("Test Report", TestResultsView.class);
    addMenuTab("Excel SpreadSheet View", ExcelSpreadSheetsView.class);
    addMenuTab("Create Test", CreateTest.class);
    addToNavbar(img, tabs);

  }

  private void addMenuTab(String label, Class<? extends Component> target) {
    Tab tab = new Tab(new RouterLink(label, target));
    navigationTargetToTab.put(target, tab);
    tabs.add(tab);
  }
}
