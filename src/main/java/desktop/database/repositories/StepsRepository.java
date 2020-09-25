package desktop.database.repositories;


import desktop.database.pojos.Steps;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StepsRepository extends JpaRepository<Steps,Integer> {

}
