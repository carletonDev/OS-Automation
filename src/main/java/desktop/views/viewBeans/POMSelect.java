package desktop.views.viewBeans;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reflections.Reflections;
import poms.BasePom;

@Data
public class POMSelect implements Serializable {

  private static final long serialVersionUID = 3642720286595893091L;
  private static Logger logger = LogManager.getLogger(POMSelect.class);
  private Class pomClass;
  private String pomTitle;

  public static List<POMSelect>getAllPOMS(){
    Reflections reflections = new Reflections("poms");
    Set<Class<?extends BasePom>> poms = reflections.getSubTypesOf(BasePom.class);
    List<POMSelect> list = new ArrayList<>();
    for(Class<?extends BasePom> pom:poms){
      POMSelect pomAdd = new POMSelect();
      pomAdd.setPomClass(pom);
      try {
        Field f =pom.getField("pageTitle");
        f.setAccessible(true);
        pomAdd.setPomTitle(f.get(null).toString());
      } catch (NoSuchFieldException | IllegalAccessException e) {
        logger.info(e.getMessage());
      }
      list.add(pomAdd);
    }
    return list;
  }
}
