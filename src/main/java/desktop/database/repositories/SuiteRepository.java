package desktop.database.repositories;

import desktop.database.pojos.Suite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SuiteRepository extends JpaRepository<Suite,Integer> {

}
