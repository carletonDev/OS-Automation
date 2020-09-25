package desktop.views.viewBeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.persistence.Entity;
import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reflections.Reflections;

@Data
public class PojoSelect implements Serializable {

  private static final long serialVersionUID = 4692804404949238711L;
  private static Logger logger = LogManager.getLogger(PojoSelect.class);
  private Class pojoClass;

  public static List<PojoSelect> getAllPojos() {
    Reflections reflections = new Reflections("desktop.database.pojos");
    Set<Class<?>> pojos = reflections.getTypesAnnotatedWith(Entity.class);
    List<PojoSelect> list = new ArrayList<>();
    for (Class<?> pojo : pojos) {
      PojoSelect pojoAdd = new PojoSelect();
      pojoAdd.setPojoClass(pojo);
      list.add(pojoAdd);
    }
    return list;
  }
}
