package project.asap.gudang.application;

import org.springframework.data.domain.Page;
import project.asap.gudang.domain.dto.GudangRequest;
import project.asap.gudang.domain.entity.Gudang;
import project.asap.utility.MessageResponse;

import java.util.List;

public interface GudangService {
    List<Gudang> getAll();

    Gudang getById(Long id);

    MessageResponse save(GudangRequest gudangRequest);

    MessageResponse update(Long id, GudangRequest gudangRequest);

    MessageResponse delete(Long id);
}
