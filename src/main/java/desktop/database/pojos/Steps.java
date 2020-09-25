package desktop.database.pojos;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="steps")
public class Steps implements Serializable {

  private static final long serialVersionUID = -4914793012222839717L;
  @Id
  @Column(name="stepId",unique = true,nullable = false,insertable = false,updatable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer stepId;

  @Column(name="screenName")
  public String screenName;

  @Column(name="action")
  public String action;

  @Column(name="keyword")
  public String keyword;

  @Column(name="parameter")
  public String parameter;

}
