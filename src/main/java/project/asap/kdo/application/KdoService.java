package project.asap.kdo.application;

import org.springframework.data.domain.Page;
import project.asap.kdo.domain.dto.KdoRequest;
import project.asap.kdo.domain.entity.Kdos;
import project.asap.utility.MessageResponse;

public interface KdoService {
    Page<Kdos> getAll(int page, int size, String sort, String order, String search);

    Kdos getById(Long id);

    MessageResponse save(KdoRequest kdoRequest);

    MessageResponse update(Long id, KdoRequest kdoRequest);

    MessageResponse delete(Long id);
}
