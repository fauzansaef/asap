package project.asap.penyimpanan.infrastructure;


import org.springframework.data.jpa.repository.JpaRepository;
import project.asap.penyimpanan.domain.entity.PenyimpananMapping;

public interface PenyimpananMappingRepository extends JpaRepository<PenyimpananMapping, Long> {
}
