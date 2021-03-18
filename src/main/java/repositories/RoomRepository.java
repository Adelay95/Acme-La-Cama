
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Offert;
import domain.Reservation;
import domain.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {

	@Query("select r from Room r where r.hotel.id=?1")
	Collection<Room> getAllRoomHotel(int hotelId);

	@Query("select max(r.number) from Hotel h join h.rooms r where h.id=?1")
	Integer getMaxNumberHotel(int id);
	@Query("select r from Room r where (r.hotel.id=?1)and(r.number=?2)")
	Collection<Room> getSearchRoomWorker(int hotelId, int search);

	@Query("select r from Reservation r where r.rooms.id=?1 and r.checkIn<=CURRENT_DATE and r.checkOut>CURRENT_DATE")
	Reservation myReservations(int roomId);

	@Query("select o from Offert o where o.rooms.id=?1 and o.checkIn<=CURRENT_DATE and o.checkOut>CURRENT_DATE and o.client!=null")
	Offert myOffert(int roomId);

	@Query("select o.rooms from Offert o where o.rooms.hotel.id=?1 and o.checkIn<=CURRENT_DATE and o.checkOut>CURRENT_DATE and o.client!=null")
	Collection<Room> myOffertZ(int hotelId);
	@Query("select r.rooms from Reservation r where r.rooms.hotel.id=?1 and r.checkIn<=CURRENT_DATE and r.checkOut>CURRENT_DATE")
	Collection<Room> myReservationZ(int hotelId);

}
