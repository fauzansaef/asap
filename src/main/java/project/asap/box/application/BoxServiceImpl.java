package project.asap.box.application;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.asap.box.domain.dto.BoxRequest;
import project.asap.box.domain.entity.Box;
import project.asap.box.infrastructure.BoxRepository;
import project.asap.exception.ResourceNotFoundException;
import project.asap.utility.MessageResponse;

import java.util.List;

@Service
@Transactional
public class BoxServiceImpl implements BoxService {
    private static final Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(BoxServiceImpl.class);
    private final BoxRepository boxRepository;

    @Autowired
    public BoxServiceImpl(BoxRepository boxRepository) {
        this.boxRepository = boxRepository;
    }

    @Override
    public List<Box> getAll() {
        return boxRepository.findAll();
    }

    @Override
    public List<Box> getByRakId(Long idRak) {
        return boxRepository.findAllByIdRak(idRak);
    }

    @Override
    public Box getById(Long id) {
        return boxRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Box.class, "id", id.toString()));
    }

    @Override
    public MessageResponse save(BoxRequest boxRequest) {
        Box box = new Box();
        box.setNama(boxRequest.getNama());
        box.setCode(boxRequest.getCode());
        box.setIdRak(boxRequest.getIdRak());
        boxRepository.save(box);
        logger.info("box created");
        return new MessageResponse("box created", HttpStatus.OK);
    }

    @Override
    public MessageResponse update(Long id, BoxRequest boxRequest) {
        Box box = getById(id);
        box.setNama(boxRequest.getNama());
        box.setCode(boxRequest.getCode());
        box.setIdRak(boxRequest.getIdRak());
        boxRepository.save(box);
        logger.info("box updated");
        return new MessageResponse("box updated", HttpStatus.OK);

    }

    @Override
    public MessageResponse delete(Long id) {
        if (boxRepository.existsById(id)) {
            boxRepository.deleteById(id);
            logger.info("box deleted");
            return new MessageResponse("box deleted", HttpStatus.OK);
        } else {
            logger.info("box not found");
            return new MessageResponse("box not found", HttpStatus.NOT_FOUND);
        }
    }
}
