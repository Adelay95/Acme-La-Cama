
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Worker;

@Repository
public interface WorkerRepository extends JpaRepository<Worker, Integer> {

	@Query("select w from Worker w where w.userAccount.id = ?1")
	Worker findByUserAccountId(int id);

}
