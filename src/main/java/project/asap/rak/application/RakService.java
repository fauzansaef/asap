package project.asap.rak.application;

import project.asap.rak.domain.dto.RakRequest;
import project.asap.rak.domain.entity.Rak;
import project.asap.utility.MessageResponse;

import java.util.List;

public interface RakService {
    List<Rak> getAll();
    List<Rak> getByLemariId(Long idLemari);
    Rak getById(Long id);
    MessageResponse save(RakRequest rakRequest);
    MessageResponse update(Long id, RakRequest rakRequest);
    MessageResponse delete(Long id);
}
