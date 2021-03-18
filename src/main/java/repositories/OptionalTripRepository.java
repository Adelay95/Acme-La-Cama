
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Bill;
import domain.Client;
import domain.OptionalTrip;

@Repository
public interface OptionalTripRepository extends JpaRepository<OptionalTrip, Integer> {

	@Query("select ot from OptionalTrip ot where ot.hotel.id= ?1")
	Collection<OptionalTrip> tripDisponibles(int hotelId);
	@Query("select b from Bill b where b.reservation!=null and b.reservation.client.id= ?1 and b.reservation.rooms.hotel.id= ?2")
	Collection<Bill> billDelClient(int clientId, int hotelId);
	@Query("select b from Bill b where b.offert!=null and b.offert.client.id= ?1 and b.offert.rooms.hotel.id= ?2")
	Collection<Bill> billDelClient2(int clientId, int hotelId);

	@Query("select c from Client c where EXISTS (select r from Reservation r where r.checkIn<= ANY(select t from OptionalTrip e join e.tripDays t where e.id=?1) and r.checkOut>= ANY(select t from OptionalTrip e join e.tripDays t where e.id=?1) and c.id=r.client.id and r.rooms.hotel.id=?2) or EXISTS (select o from Offert o where o.checkIn<= ANY(select t from OptionalTrip e join e.tripDays t where e.id=?1) and o.checkOut>= ANY(select t from OptionalTrip e join e.tripDays t where e.id=?1) and c.id=o.client.id and o.rooms.hotel.id=?2)")
	Collection<Client> sixtoCarry(int idTrip, int idHotel);

	@Query("select o from OptionalTrip o where o.hotel.request.manager.id=?1")
	Collection<OptionalTrip> holaCasol(int id);
}
