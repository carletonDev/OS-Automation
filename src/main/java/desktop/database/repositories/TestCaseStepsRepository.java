package desktop.database.repositories;

import desktop.database.pojos.TestCaseSteps;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestCaseStepsRepository extends JpaRepository<TestCaseSteps,Integer> {

}
