package project.asap.lemari.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import project.asap.lemari.domain.entity.Lemari;

import java.util.List;

public interface LemariRepository extends JpaRepository<Lemari, Long> {
    List<Lemari> findAllByIdGudang(Long idGudang);
}
