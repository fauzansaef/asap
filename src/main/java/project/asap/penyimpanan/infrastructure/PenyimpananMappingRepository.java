package project.asap.penyimpanan.infrastructure;


import org.springframework.data.jpa.repository.JpaRepository;
import project.asap.penyimpanan.domain.entity.PenyimpananMapping;

import java.util.List;
import java.util.Optional;

public interface PenyimpananMappingRepository extends JpaRepository<PenyimpananMapping, Long> {
    Optional<PenyimpananMapping> findByIdArsip(Long idArsip);
    Optional<PenyimpananMapping> findByIdBmn(Long idBmn);
    Optional<PenyimpananMapping> findByIdAtk(Long idAtk);
}
