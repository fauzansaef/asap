package project.asap.lemari.application;

import project.asap.lemari.domain.dto.LemariRequest;
import project.asap.lemari.domain.entity.Lemari;
import project.asap.utility.MessageResponse;

import java.util.List;

public interface LemariService {
    List<Lemari> getAll();
    List<Lemari> getByGudangId(Long idGudang);
    Lemari getById(Long id);
    MessageResponse save(LemariRequest lemariRequest);
    MessageResponse update(Long id, LemariRequest lemariRequest);
    MessageResponse delete(Long id);
}
