package project.asap.kdo.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import project.asap.kdo.domain.entity.ReqKdos;

public interface ReqKdosRepository extends JpaRepository<ReqKdos, Long> {
}
