package project.asap.bmn.application;

import org.springframework.data.domain.Page;
import project.asap.bmn.domain.dto.BmnRequest;
import project.asap.bmn.domain.entity.Bmns;
import project.asap.utility.MessageResponse;

public interface BmnService {
    Page<Bmns> getAll(int page, int size, String sort, String order, String search);

    Bmns getById(Long id);

    MessageResponse save(BmnRequest bmnRequest);

    MessageResponse update(Long id, BmnRequest bmnRequest);

    MessageResponse delete(Long id);
}
