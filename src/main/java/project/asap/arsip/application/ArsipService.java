package project.asap.arsip.application;

import org.springframework.data.domain.Page;
import project.asap.arsip.domain.dto.TambahArsipRequest;
import project.asap.arsip.domain.entity.Arsip;
import project.asap.penyimpanan.domain.entity.PenyimpananMapping;
import project.asap.utility.MessageResponse;

import java.io.InputStream;

public interface ArsipService {
    Page<Arsip> getAll(int page, int size, String sort, String order, String search);

    Arsip getById(Long id);

    MessageResponse save(TambahArsipRequest request);

    MessageResponse update(Long id, TambahArsipRequest request);

    MessageResponse delete(Long id);

    MessageResponse saveFromExcel(InputStream inputStream);

    PenyimpananMapping getPenyimpananMapping(Long idArsip);
}
