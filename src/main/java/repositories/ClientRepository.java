
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Client;
import domain.Reservation;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {

	@Query("select c from Client c where c.userAccount.id = ?1")
	Client findByUserAccountId(int id);

	@Query("select s from Room r join r.reservations s where r.hotel.id=?1 and s.client.id=?2 order by s.checkIn ASC")
	Collection<Reservation> reservationThisHotel(int id, int id2);
	@Query("select c from Client c where c.dniNif=?1")
	Collection<Client> checkDNI(String dniNif);
}
