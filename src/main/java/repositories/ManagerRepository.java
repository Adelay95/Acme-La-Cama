
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Manager;
import domain.Offert;
import domain.Worker;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Integer> {

	@Query("select m from Manager m where m.userAccount.id = ?1")
	Manager findByUserAccountId(int userAccountId);

	@Query("select w from Hotel h join h.workers w where h.request.manager.id=?1")
	Collection<Worker> todosMisTrabajadores(int id);

	@Query("select r.offert from Hotel h join h.rooms r where h.request.manager.id=?1")
	Collection<Offert> getAllMyOfferts(int id);

}
