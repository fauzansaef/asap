package project.asap.ref.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import project.asap.kdo.domain.entity.Kdos;
import project.asap.ref.domain.entity.RefJenisPemeliharaan;

public interface RefJenisPemeliharaanRepository extends JpaRepository<RefJenisPemeliharaan, Long>, JpaSpecificationExecutor<RefJenisPemeliharaan> {
}
