
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Administrator;
import domain.Services;

@Repository
public interface ServicesRepository extends JpaRepository<Services, Integer> {

	@Query("Select s from Room r join r.services s where r.id=?1")
	Collection<Services> getAllServicesRoom(int roomId);

	@Query("select a.name from Services a")
	Collection<String> getAllNames();

	@Query("select c from Services c where c.name=?1")
	Services getServiceByName(String e);
	
	@Query("Select a from Administrator a")
	Administrator selectAdministrator();
	
	@Query("select s from Services s where s NOT IN (select s from Room r join r.services s where r.id=?1)")
	Collection<Services> getNoServicesRoom(int roomId);


}
