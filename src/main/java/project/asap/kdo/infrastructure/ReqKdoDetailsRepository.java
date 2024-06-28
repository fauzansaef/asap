package project.asap.kdo.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import project.asap.kdo.domain.entity.ReqKdoDetails;

public interface ReqKdoDetailsRepository extends JpaRepository<ReqKdoDetails, Long> {
}
