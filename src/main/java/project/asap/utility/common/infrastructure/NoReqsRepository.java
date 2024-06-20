package project.asap.utility.common.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import project.asap.utility.common.domain.NoReqs;

import java.util.Optional;

public interface NoReqsRepository extends JpaRepository<NoReqs, Long> {
    Optional<NoReqs> findFirstByTanggalAndTypeOrderByIdDesc(String tanggal, Integer type);
}
