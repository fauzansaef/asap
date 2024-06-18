package project.asap.ref.application;

import org.springframework.data.domain.Page;
import project.asap.kdo.domain.dto.KdoRequest;
import project.asap.kdo.domain.entity.Kdos;
import project.asap.ref.domain.dto.RefTipeArsipRequest;
import project.asap.ref.domain.entity.RefTipeArsip;
import project.asap.utility.MessageResponse;

public interface RefTipeArsipService {
    Page<RefTipeArsip> getAll(int page, int size, String sort, String order, String search);

    RefTipeArsip getById(Long id);

    MessageResponse save(RefTipeArsipRequest refTipeArsipRequest);

    MessageResponse update(Long id, RefTipeArsipRequest refTipeArsipRequest);

    MessageResponse delete(Long id);
}
