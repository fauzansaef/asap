package project.asap.box.application;

import project.asap.box.domain.dto.BoxRequest;
import project.asap.box.domain.entity.Box;
import project.asap.utility.MessageResponse;

import java.util.List;

public interface BoxService {
    List<Box> getAll();
    List<Box> getByRakId(Long idRak);
    Box getById(Long id);
    MessageResponse save(BoxRequest boxRequest);
    MessageResponse update(Long id, BoxRequest boxRequest);
    MessageResponse delete(Long id);
}
