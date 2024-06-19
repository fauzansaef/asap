package project.asap.arsip.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import project.asap.arsip.domain.entity.Arsip;

public interface ArsipRepository extends JpaRepository<Arsip, Long>, JpaSpecificationExecutor<Arsip> {
}
