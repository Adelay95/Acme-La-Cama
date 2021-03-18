
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Administrator;
import domain.Hotel;
import domain.Manager;
import domain.Request;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Integer> {

	@Query("select c from Administrator c where c.userAccount.id = ?1")
	Administrator findByUserAccountId(int userAccountId);

	@Query("select h from Hotel h where h.request.manager.id= ?1 and h.request.state='ACCEPTED' and (CURRENT_TIMESTAMP>h.request.timeIn and CURRENT_TIMESTAMP<h.request.timeOut)")
	Collection<Hotel> getAllHotelAccepted(int managerId);

	@Query("select count(r) from Room r where r.hotel.id= ?1 and CURRENT_DATE member of r.occupiedDays")
	Long numeroHabitacionesOcupadas(int hotelId);

	//
	//	@Query("select count(o) from Offert o where o.rooms.hotel.request.manager.id= ?1 and o.rooms.hotel.id=?2")
	//	Double numOffert(int managerId, int hotelId);

	@Query("select avg(ot.clients.size) from Hotel h join h.optionalTrips ot where h.id= ?1")
	Double avgClientsTrips(int hotelId);

	@Query("select avg(c.stars) from Comment c where c.hotel.id= ?1")
	Double avgStarsComment(int hotelId);

	@Query("select count(c) from Client c where exists (select r from Reservation r where r.client.id=c.id  and r.rooms.hotel.id=?1)")
	Long numeroClientestotales(int hotelId);
	@Query("select avg(r.bill.totalAmount) from Client c join c.reservations r where r.rooms.hotel.id=?")
	Double avgBill(int hotelId);

	//raking de los manager con el numero de hoteles 
	// rannking de hoteles ordenado por la media de las estrellas de sus comentarios
	//el min el max y la media de las estrellas de los hoteles
	//la media de trabajadores por hoteles

	@Query("select m from Manager m order by m.requests.size DESC")
	Collection<Manager> ordenado();

	@Query("select m from Hotel m join m.comments c order by avg(m.stars) DESC")
	Collection<Hotel> ordenados();

	@Query("select min(h.stars) from Hotel h ")
	Integer min();
	@Query("select avg(h.stars) from Hotel h")
	Double avg();
	@Query("select max(h.stars) from Hotel h")
	Integer max();
	@Query("select avg(h.workers.size) from Hotel h")
	Double avgWorker();
	@Query("select min(h.workers.size) from Hotel h")
	Integer minWorker();
	@Query("select max(h.workers.size) from Hotel h")
	Integer maxWorker();

	@Query("select r from Request r")
	Collection<Request> requests();
}
