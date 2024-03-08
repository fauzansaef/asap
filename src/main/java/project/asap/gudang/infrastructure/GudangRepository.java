package project.asap.gudang.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import project.asap.gudang.domain.entity.Gudang;

public interface GudangRepository extends JpaRepository<Gudang, Long>, JpaSpecificationExecutor<Gudang> {
}
