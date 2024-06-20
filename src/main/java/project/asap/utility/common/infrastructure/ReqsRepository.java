package project.asap.utility.common.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import project.asap.utility.common.domain.Reqs;

public interface ReqsRepository extends JpaRepository<Reqs, Long> {

}
