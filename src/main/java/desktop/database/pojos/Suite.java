package desktop.database.pojos;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import restAssuredUtilities.Generic;

@Data
@Entity
@Table(name="suite")
public class Suite implements Serializable {

  private static final long serialVersionUID = -9099596402605283471L;

  @Id
  @Column(name="suiteId",unique = true,nullable = false,insertable = false,updatable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer suiteId;

  @Column(name="suiteName")
  public String suiteName;

  @Column(name="applicationType")
  public String applicationType;

  @Column(name = "environment")
  public String environment;

  @Column(name="zapi")
  public Boolean zapi;

  @Column(name="enable")
  public Boolean enabled;


}
