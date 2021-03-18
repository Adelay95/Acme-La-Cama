
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Bill;

@Repository
public interface BillRepository extends JpaRepository<Bill, Integer> {

	@Query("select b from Bill b where b.reservation.client.id=?1")
	Collection<Bill> billzReservations(int id);
	@Query("select b from Bill b where b.offert.client.id=?1")
	Collection<Bill> billzOfferts(int id);

}
