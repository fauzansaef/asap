package project.asap.users.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import project.asap.users.domain.entity.SubSections;

import java.util.List;

public interface SubSectionsRepository extends JpaRepository<SubSections, Long> {
    List<SubSections> findAllBySectionId(Long id);
}
