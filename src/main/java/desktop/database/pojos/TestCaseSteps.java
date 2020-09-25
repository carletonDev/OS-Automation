package desktop.database.pojos;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "testCaseSteps")
public class TestCaseSteps implements Serializable {

  private static final long serialVersionUID = -1139752391447003740L;
  @Id
  @Column(name = "teststepsId",unique = true,nullable = false,insertable = false,updatable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer teststepsId;

  @ManyToOne
  @JoinColumn(name = "testid", insertable = false, updatable = false)
  public TestCase testCase;

  @ManyToOne
  @JoinColumn(name = "stepId", insertable = false, updatable = false)
  public Steps steps;

  @Column(name="stepOrder")
  public Integer stepOrder;


}
