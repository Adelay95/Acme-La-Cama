
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Hotel;
import domain.Offert;
import domain.Reservation;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Integer> {

	@Query("select h.request.manager.id from Hotel h where h.id=?1")
	Integer getIdManagerHotel(int hotelId);
	@Query("select h from Hotel h where h.request.state='ACCEPTED' and (CURRENT_TIMESTAMP>=h.request.timeIn and CURRENT_TIMESTAMP<=h.request.timeOut)")
	Collection<Hotel> getAllHotelAccepted();
	@Query("select h from Hotel h where h.request.manager.id=?1")
	Collection<Hotel> getAllHotelManager(int managerId);
	@Query("select r.reservations from Room r where r.hotel.id=?1")
	Collection<Reservation> getAllReservations(int hotelId);
	@Query("select r.offert from Room r where r.hotel.id=?1")
	Collection<Offert> getAllOfferts(int hotelId);
	@Query("select r.offert from Room r where r.hotel.id=?1 and r.offert.client=null and r.offert.checkIn>CURRENT_DATE")
	Collection<Offert> getAllAvailableOfferts(int hotelId);

}
