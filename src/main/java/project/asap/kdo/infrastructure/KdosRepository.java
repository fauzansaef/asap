package project.asap.kdo.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import project.asap.kdo.domain.entity.Kdos;

public interface KdosRepository extends JpaRepository<Kdos, Long>, JpaSpecificationExecutor<Kdos> {
}
