package listeners.generalSeleniumActionEvent;

import java.util.EventObject;
import lombok.Getter;

public class SeleniumActionEvent extends EventObject {

  @Getter
  private final SeleniumAction action;


  /**
   * Constructs a prototypical Event.
   *
   * @param source The object on which the Event initially occurred.
   * @throws IllegalArgumentException if source is null.
   */
  public SeleniumActionEvent(Object source, SeleniumAction action) {
    super(source);
    this.action = action;
  }

  @Override
  public Object getSource() {
    return super.getSource();
  }
}
