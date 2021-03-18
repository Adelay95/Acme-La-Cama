
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Actor;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Integer> {

	@Query("select c from Actor c where c.userAccount.id = ?1")
	Actor findByUserAccountId(int id);

	@Query("select a from Actor a where a.id!=?1")
	Collection<Actor> todosActoresMenosYo(int id);

	@Query("select a.name from Folder a where a.actor.id =?1")
	Collection<String> todasFoldersActor(int id);
}
