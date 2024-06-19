package project.asap.box.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import project.asap.box.domain.entity.Box;

import java.util.List;

public interface BoxRepository extends JpaRepository<Box, Long> {
    List<Box> findAllByIdRak(Long idRak);
}
