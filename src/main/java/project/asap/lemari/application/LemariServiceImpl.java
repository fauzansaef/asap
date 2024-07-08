package project.asap.lemari.application;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.asap.exception.ResourceNotFoundException;
import project.asap.lemari.domain.dto.LemariRequest;
import project.asap.lemari.domain.entity.Lemari;
import project.asap.lemari.infrastructure.LemariRepository;
import project.asap.utility.MessageResponse;

import java.util.List;
@Service
@Transactional
public class LemariServiceImpl implements LemariService{
    private static final Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(LemariServiceImpl.class);
    private final LemariRepository lemariRepository;

    @Autowired
    public LemariServiceImpl(LemariRepository lemariRepository) {
        this.lemariRepository = lemariRepository;
    }

    @Override
    public List<Lemari> getAll() {
        return lemariRepository.findAll();
    }

    @Override
    public List<Lemari> getByGudangId(Long idGudang) {
        return lemariRepository.findAllByIdGudang(idGudang);
    }

    @Override
    public Lemari getById(Long id) {
        return lemariRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(Lemari.class, "id", id.toString()));
    }

    @Override
    public MessageResponse save(LemariRequest lemariRequest) {
        Lemari lemari = new Lemari();
        lemari.setNama(lemariRequest.getNama());
        lemari.setCode(lemariRequest.getCode());
        lemari.setIdGudang(lemariRequest.getIdGudang());
        lemari.setPic(lemariRequest.getIdUser());
        lemariRepository.save(lemari);
        logger.info("lemari created");
        return new MessageResponse("lemari created", HttpStatus.OK);
    }

    @Override
    public MessageResponse update(Long id, LemariRequest lemariRequest) {
        Lemari lemari = getById(id);
        lemari.setNama(lemariRequest.getNama());
        lemari.setCode(lemariRequest.getCode());
        lemari.setIdGudang(lemariRequest.getIdGudang());
        lemari.setPic(lemariRequest.getIdUser());
        lemariRepository.save(lemari);
        logger.info("lemari updated");
        return new MessageResponse("lemari updated", HttpStatus.OK);
    }

    @Override
    public MessageResponse delete(Long id) {
        if (lemariRepository.existsById(id)) {
            lemariRepository.deleteById(id);
            logger.info("lemari deleted");
            return new MessageResponse("lemari deleted", HttpStatus.OK);
        } else {
            logger.info("lemari not found");
            return new MessageResponse("lemari not found", HttpStatus.NOT_FOUND);
        }
    }
}
