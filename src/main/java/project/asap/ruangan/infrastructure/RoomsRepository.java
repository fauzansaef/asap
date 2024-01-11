package project.asap.ruangan.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import project.asap.ruangan.domain.entity.Rooms;

public interface RoomsRepository extends JpaRepository<Rooms, Long>, JpaSpecificationExecutor<Rooms> {
}
