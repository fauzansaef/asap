package project.asap.users.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import project.asap.users.domain.entity.Sections;

public interface SectionsRepository extends JpaRepository<Sections, Long> {
}
