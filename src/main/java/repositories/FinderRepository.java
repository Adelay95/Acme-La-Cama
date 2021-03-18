
package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Finder;
import domain.KindOfOffert;
import domain.KindOfRoom;
import domain.Offert;
import domain.Room;
import domain.Terrain;

@Repository
public interface FinderRepository extends JpaRepository<Finder, Integer> {

	//HABITACIONES
	@Query("select r from Room r where (NOT EXISTS (select o from Room t join t.occupiedDays o where (t.id=r.id)and(o >= ?4)and(o < ?5)))and (r.hotel.request.state='ACCEPTED')and (CURRENT_TIMESTAMP>=r.hotel.request.timeIn and CURRENT_TIMESTAMP<=r.hotel.request.timeOut) and (r.hotel.name like ?1) and (r.originalPriceDays>=?2) and (r.originalPriceDays<=?3) and (r.kindOfRoom LIKE ?6)and(r.hotel.terrain LIKE ?7) and(r.hotel.location.province LIKE ?8)and(r.hotel.location.population LIKE ?9)and(r.capacity >= ?10)and(r.capacity <= ?11)")
	Collection<Room> searchRoomTodo(String hotelName, Double minimumPrice, Double maximumPrice, Date checkIn, Date checkOut, KindOfRoom kindOfRoom, Terrain terrain, String province, String population, Integer minCapacity, Integer maxCapacity);

	@Query("select r from Room r where (NOT EXISTS (select o from Room t join t.occupiedDays o where (t.id=r.id)and(o >= ?4)and(o < ?5)))and (r.hotel.request.state='ACCEPTED')and (CURRENT_TIMESTAMP>=r.hotel.request.timeIn and CURRENT_TIMESTAMP<=r.hotel.request.timeOut) and (r.hotel.name like ?1) and (r.originalPriceDays>=?2) and (r.originalPriceDays<=?3) and(r.hotel.location.province LIKE ?6)and(r.hotel.location.population LIKE ?7)and(r.capacity >= ?8)and(r.capacity <= ?9)")
	Collection<Room> searchRoomSinKyT(String hotelName, Double minimumPrice, Double maximumPrice, Date checkIn, Date checkOut, String province, String population, Integer minCapacity, Integer maxCapacity);
	@Query("select r from Room r where (NOT EXISTS (select o from Room t join t.occupiedDays o where (t.id=r.id)and(o >= ?4)and(o < ?5)))and (r.hotel.request.state='ACCEPTED')and (CURRENT_TIMESTAMP>=r.hotel.request.timeIn and CURRENT_TIMESTAMP<=r.hotel.request.timeOut) and (r.hotel.name like ?1) and (r.originalPriceDays>=?2) and (r.originalPriceDays<=?3)and(r.hotel.terrain LIKE ?6) and(r.hotel.location.province LIKE ?7)and(r.hotel.location.population LIKE ?8)and(r.capacity >= ?9)and(r.capacity <= ?10)")
	Collection<Room> searchRoomSinK(String hotelName, Double minimumPrice, Double maximumPrice, Date checkIn, Date checkOut, Terrain terrain, String province, String population, Integer minCapacity, Integer maxCapacity);
	@Query("select r from Room r where (NOT EXISTS (select o from Room t join t.occupiedDays o where (t.id=r.id)and(o >= ?4)and(o < ?5)))and (r.hotel.request.state='ACCEPTED')and (CURRENT_TIMESTAMP>=r.hotel.request.timeIn and CURRENT_TIMESTAMP<=r.hotel.request.timeOut) and (r.hotel.name like ?1) and (r.originalPriceDays>=?2) and (r.originalPriceDays<=?3) and (r.kindOfRoom LIKE ?6)and(r.hotel.location.province LIKE ?7)and(r.hotel.location.population LIKE ?8)and(r.capacity >= ?9)and(r.capacity <= ?10)")
	Collection<Room> searchRoomSinT(String hotelName, Double minimumPrice, Double maximumPrice, Date checkIn, Date checkOut, KindOfRoom kindOfRoom, String province, String population, Integer minCapacity, Integer maxCapacity);

	//OFERTAS

	@Query("select o from Offert o join o.rooms r where (o.client IS NULL) and (?4<=o.checkIn and ?5>=o.checkOut) and (o.client=null) and (r.hotel.request.state='ACCEPTED')and (CURRENT_TIMESTAMP>=r.hotel.request.timeIn and CURRENT_TIMESTAMP<=r.hotel.request.timeOut)and(r.hotel.name like ?1) and (o.totalPrice>=?2) and (o.totalPrice<=?3) and (r.kindOfRoom LIKE ?6)and(r.hotel.terrain LIKE ?7)and (o.kindOfOffert LIKE ?8)and(r.hotel.location.province LIKE ?9)and(r.hotel.location.population LIKE ?10)and(r.capacity >= ?11)and(r.capacity <= ?12)")
	Collection<Offert> searchOffertTodo(String hotelName, Double minimumPrice, Double maximumPrice, Date checkIn, Date checkOut, KindOfRoom kindOfRoom, Terrain terrain, KindOfOffert kindOfOffert, String province, String population, Integer minCapacity,
		Integer maxCapacity);

	@Query("select o from Offert o join o.rooms r where (o.client IS NULL) and (?4<=o.checkIn and o.checkOut<=?5) and (o.client=null) and (r.hotel.request.state='ACCEPTED')and (CURRENT_TIMESTAMP>=r.hotel.request.timeIn and CURRENT_TIMESTAMP<=r.hotel.request.timeOut)and(r.hotel.name like ?1) and (o.totalPrice>=?2) and (o.totalPrice<=?3) and (r.kindOfRoom LIKE ?6)and (o.kindOfOffert LIKE ?7)and(r.hotel.location.province LIKE ?8)and(r.hotel.location.population LIKE ?9)and(r.capacity >= ?10)and(r.capacity <= ?11)")
	Collection<Offert> searchOffertSinT(String hotelName, Double minimumPrice, Double maximumPrice, Date checkIn, Date checkOut, KindOfRoom kindOfRoom, KindOfOffert kindOfOffert, String province, String population, Integer minCapacity, Integer maxCapacity);
	@Query("select o from Offert o join o.rooms r where (o.client IS NULL) and (?4<=o.checkIn and ?5>=o.checkOut) and (o.client=null) and (r.hotel.request.state='ACCEPTED')and (CURRENT_TIMESTAMP>=r.hotel.request.timeIn and CURRENT_TIMESTAMP<=r.hotel.request.timeOut)and(r.hotel.name like ?1) and (o.totalPrice>=?2) and (o.totalPrice<=?3)and(r.hotel.terrain LIKE ?6)and (o.kindOfOffert LIKE ?7)and(r.hotel.location.province LIKE ?8)and(r.hotel.location.population LIKE ?9)and(r.capacity >= ?10)and(r.capacity <= ?11)")
	Collection<Offert> searchOffertSinKR(String hotelName, Double minimumPrice, Double maximumPrice, Date checkIn, Date checkOut, Terrain terrain, KindOfOffert kindOfOffert, String province, String population, Integer minCapacity, Integer maxCapacity);
	@Query("select o from Offert o join o.rooms r where (o.client IS NULL) and (?4<=o.checkIn and ?5>=o.checkOut) and (o.client=null) and (r.hotel.request.state='ACCEPTED')and (CURRENT_TIMESTAMP>=r.hotel.request.timeIn and CURRENT_TIMESTAMP<=r.hotel.request.timeOut)and(r.hotel.name like ?1) and (o.totalPrice>=?2) and (o.totalPrice<=?3) and (o.kindOfOffert LIKE ?6)and(r.hotel.location.province LIKE ?7)and(r.hotel.location.population LIKE ?8)and(r.capacity >= ?9)and(r.capacity <= ?10)")
	Collection<Offert> searchOffertSinKRyT(String hotelName, Double minimumPrice, Double maximumPrice, Date checkIn, Date checkOut, KindOfOffert kindOfOffert, String province, String population, Integer minCapacity, Integer maxCapacity);
	@Query("select o from Offert o join o.rooms r where (o.client IS NULL) and (?4<=o.checkIn and ?5>=o.checkOut) and (o.client=null) and (r.hotel.request.state='ACCEPTED')and (CURRENT_TIMESTAMP>=r.hotel.request.timeIn and CURRENT_TIMESTAMP<=r.hotel.request.timeOut)and(r.hotel.name like ?1) and (o.totalPrice>=?2) and (o.totalPrice<=?3) and (r.kindOfRoom LIKE ?6)and(r.hotel.terrain LIKE ?7)and(r.hotel.location.province LIKE ?8)and(r.hotel.location.population LIKE ?9)and(r.capacity >= ?10)and(r.capacity <= ?11)")
	Collection<Offert> searchOffertSinKO(String hotelName, Double minimumPrice, Double maximumPrice, Date checkIn, Date checkOut, KindOfRoom kindOfRoom, Terrain terrain, String province, String population, Integer minCapacity, Integer maxCapacity);
	@Query("select o from Offert o join o.rooms r where (o.client IS NULL) and (?4<=o.checkIn and ?5>=o.checkOut) and (o.client=null) and (r.hotel.request.state='ACCEPTED')and (CURRENT_TIMESTAMP>=r.hotel.request.timeIn and CURRENT_TIMESTAMP<=r.hotel.request.timeOut)and(r.hotel.name like ?1) and (o.totalPrice>=?2) and (o.totalPrice<=?3) and (r.kindOfRoom LIKE ?6)and(r.hotel.location.province LIKE ?7)and(r.hotel.location.population LIKE ?8)and(r.capacity >= ?9)and(r.capacity <= ?10)")
	Collection<Offert> searchOffertSinKOyT(String hotelName, Double minimumPrice, Double maximumPrice, Date checkIn, Date checkOut, KindOfRoom kindOfRoom, String province, String population, Integer minCapacity, Integer maxCapacity);
	@Query("select o from Offert o join o.rooms r where (o.client IS NULL) and (?4<=o.checkIn and ?5>=o.checkOut) and (o.client=null) and (r.hotel.request.state='ACCEPTED')and (CURRENT_TIMESTAMP>=r.hotel.request.timeIn and CURRENT_TIMESTAMP<=r.hotel.request.timeOut)and(r.hotel.name like ?1) and (o.totalPrice>=?2) and (o.totalPrice<=?3)and(r.hotel.terrain LIKE ?6)and(r.hotel.location.province LIKE ?7)and(r.hotel.location.population LIKE ?8)and(r.capacity >= ?9)and(r.capacity <= ?10)")
	Collection<Offert> searchOffertSinKOyKR(String hotelName, Double minimumPrice, Double maximumPrice, Date checkIn, Date checkOut, Terrain terrain, String province, String population, Integer minCapacity, Integer maxCapacity);
	@Query("select o from Offert o join o.rooms r where (o.client IS NULL) and (?4<=o.checkIn and ?5>=o.checkOut) and (o.client=null) and (r.hotel.request.state='ACCEPTED')and (CURRENT_TIMESTAMP>=r.hotel.request.timeIn and CURRENT_TIMESTAMP<=r.hotel.request.timeOut)and(r.hotel.name like ?1) and (o.totalPrice>=?2) and (o.totalPrice<=?3)and(r.hotel.location.province LIKE ?6)and(r.hotel.location.population LIKE ?7)and(r.capacity >= ?8)and(r.capacity <= ?9)")
	Collection<Offert> searchOffertSinKOyKRyT(String hotelName, Double minimumPrice, Double maximumPrice, Date checkIn, Date checkOut, String province, String population, Integer minCapacity, Integer maxCapacity);
}
