package project.asap.rak.application;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.asap.exception.ResourceNotFoundException;
import project.asap.rak.domain.dto.RakRequest;
import project.asap.rak.domain.entity.Rak;
import project.asap.rak.infrastructure.RakRepository;
import project.asap.utility.MessageResponse;

import java.util.List;

@Service
@Transactional
public class RakServiceImpl implements RakService{
    private static final Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(RakServiceImpl.class);
    private final RakRepository rakRepository;

    @Autowired
    public RakServiceImpl(RakRepository rakRepository) {
        this.rakRepository = rakRepository;
    }

    @Override
    public List<Rak> getAll() {
        return rakRepository.findAll();
    }

    @Override
    public List<Rak> getByLemariId(Long idLemari) {
        return rakRepository.findAllByIdLemari(idLemari);
    }

    @Override
    public Rak getById(Long id) {
        return rakRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(Rak.class, "id", id.toString()));
    }

    @Override
    public MessageResponse save(RakRequest rakRequest) {
        Rak rak = new Rak();
        rak.setNama(rakRequest.getNama());
        rak.setCode(rakRequest.getCode());
        rak.setIdLemari(rakRequest.getIdLemari());
        rakRepository.save(rak);
        logger.info("rak created");
        return new MessageResponse("rak created", HttpStatus.OK);
    }

    @Override
    public MessageResponse update(Long id, RakRequest rakRequest) {
        Rak rak = getById(id);
        rak.setNama(rakRequest.getNama());
        rak.setCode(rakRequest.getCode());
        rak.setIdLemari(rakRequest.getIdLemari());
        rakRepository.save(rak);
        logger.info("rak updated");
        return new MessageResponse("rak updated", HttpStatus.OK);
    }

    @Override
    public MessageResponse delete(Long id) {
        if (rakRepository.existsById(id)) {
            rakRepository.deleteById(id);
            logger.info("rak deleted");
            return new MessageResponse("rak deleted", HttpStatus.OK);
        } else {
            logger.info("rak not found");
            return new MessageResponse("rak not found", HttpStatus.NOT_FOUND);
        }
    }
}
