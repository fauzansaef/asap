package project.asap.ref.application.refjenispemeliharaan;

import org.springframework.data.domain.Page;
import project.asap.ref.domain.dto.RefJenisPemeliharaanRequest;
import project.asap.ref.domain.dto.RefTipeArsipRequest;
import project.asap.ref.domain.entity.RefJenisPemeliharaan;
import project.asap.ref.domain.entity.RefTipeArsip;
import project.asap.utility.MessageResponse;

public interface RefJenisPemeliharaanService {
    Page<RefJenisPemeliharaan> getAll(int page, int size, String sort, String order, String search);

    RefJenisPemeliharaan getById(Long id);

    MessageResponse save(RefJenisPemeliharaanRequest refJenisPemeliharaanRequest);

    MessageResponse update(Long id, RefJenisPemeliharaanRequest refJenisPemeliharaanRequest);

    MessageResponse delete(Long id);
}
