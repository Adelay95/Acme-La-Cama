
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

	@Query("select c from Hotel h join h.comments c where h.id=?1 order by c.creationDate desc")
	Collection<Comment> cometariosOrdenadosHotel(int hotelId);

}
