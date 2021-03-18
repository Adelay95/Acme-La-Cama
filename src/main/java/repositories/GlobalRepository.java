
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Global;

@Repository
public interface GlobalRepository extends JpaRepository<Global, Integer> {

	@Query("select g from Global g")
	Global findTheGlobal();
}
