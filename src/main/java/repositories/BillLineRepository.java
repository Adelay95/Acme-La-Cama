
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.BillLine;

@Repository
public interface BillLineRepository extends JpaRepository<BillLine, Integer> {

}
