package desktop.database.services;

import desktop.database.repositories.StepsRepository;
import desktop.database.repositories.SuiteRepository;
import desktop.database.repositories.TestCaseRepository;
import desktop.database.repositories.TestCaseStepsRepository;
import java.io.Serializable;
import java.util.Objects;
import org.springframework.stereotype.Service;

@Service
public class RepositoryFactory implements Serializable {

  private static final long serialVersionUID = -2251006299495435660L;
  private final StepsRepository stepsRepository;
  private final SuiteRepository suiteRepository;
  private final TestCaseRepository testCaseRepository;
  private final TestCaseStepsRepository testCaseStepsRepository;

  public RepositoryFactory(StepsRepository stepsRepository,
      SuiteRepository suiteRepository,
      TestCaseRepository testCaseRepository,
      TestCaseStepsRepository testCaseStepsRepository) {
    this.stepsRepository = stepsRepository;
    this.suiteRepository = suiteRepository;
    this.testCaseRepository = testCaseRepository;
    this.testCaseStepsRepository = testCaseStepsRepository;
  }

  public StepsRepository getStepsRepository() {
    return stepsRepository;
  }

  public SuiteRepository getSuiteRepository() {
    return suiteRepository;
  }

  public TestCaseRepository getTestCaseRepository() {
    return testCaseRepository;
  }

  public TestCaseStepsRepository getTestCaseStepsRepository() {
    return testCaseStepsRepository;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof RepositoryFactory)) {
      return false;
    }
    RepositoryFactory that = (RepositoryFactory) o;
    return Objects.equals(getStepsRepository(), that.getStepsRepository()) &&
        Objects.equals(getSuiteRepository(), that.getSuiteRepository()) &&
        Objects.equals(getTestCaseRepository(), that.getTestCaseRepository()) &&
        Objects.equals(getTestCaseStepsRepository(), that.getTestCaseStepsRepository());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getStepsRepository(), getSuiteRepository(), getTestCaseRepository(),
        getTestCaseStepsRepository());
  }
}
