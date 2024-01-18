package project.asap.bmn.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import project.asap.bmn.domain.entity.Bmns;

public interface BmnsRepository extends JpaRepository<Bmns, Long>, JpaSpecificationExecutor<Bmns> {
}
