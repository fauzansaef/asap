package project.asap.rak.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import project.asap.rak.domain.entity.Rak;

import java.util.List;

public interface RakRepository extends JpaRepository<Rak, Long> {
    List<Rak> findAllByIdLemari(Long idLemari);
}
