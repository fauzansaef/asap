package project.asap.file_ref.infrsatructure;

import org.springframework.data.jpa.repository.JpaRepository;
import project.asap.file_ref.domain.FailedJobsExcel;

import java.util.List;

public interface FailedJobsExcelRepository extends JpaRepository<FailedJobsExcel, Long> {
    List<FailedJobsExcel> findAllByIdFileRef(Long idFileRef);
}
