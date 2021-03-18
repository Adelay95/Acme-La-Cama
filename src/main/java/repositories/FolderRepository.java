
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Folder;

@Repository
public interface FolderRepository extends JpaRepository<Folder, Integer> {

	@Query("Select c from Folder c where c.name=?1 and c.actor.id=?2")
	public Folder getINBOXByActorId(String param, Integer actor);

	@Query("Select c from Folder c where c.name=?1 and c.actor.id=?2")
	public Folder getOUTBOXByActorId(String param, Integer actor);

}
