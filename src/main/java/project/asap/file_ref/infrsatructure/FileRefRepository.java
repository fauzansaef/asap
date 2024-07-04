package project.asap.file_ref.infrsatructure;

import org.springframework.data.jpa.repository.JpaRepository;
import project.asap.file_ref.domain.FileRef;

public interface FileRefRepository extends JpaRepository<FileRef, Long> {
}
