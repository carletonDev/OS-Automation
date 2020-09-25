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
@Table(name="testCase")
public class TestCase implements Serializable {

  private static final long serialVersionUID = 1090946976685061754L;
  @Id
  @Column(name="testId",unique = true,nullable = false,insertable = false,updatable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer testCaseId;

  @Column(name="testCaseName")
  public String testCaseName;

  @Column(name="description")
  public String description;

  @Column(name="browser")
  public String browser;

  @Column(name="testType")
  public String testType;

  @Column(name="enabled")
  public Boolean enabled;

  @Column(name="record")
  public Boolean record;

  @ManyToOne
  @JoinColumn(name="suiteId",insertable = false,updatable = false)
  public Suite suite;

}
