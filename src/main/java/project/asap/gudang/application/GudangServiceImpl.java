package project.asap.gudang.application;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.asap.exception.ResourceNotFoundException;
import project.asap.gudang.domain.dto.GudangRequest;
import project.asap.gudang.domain.entity.Gudang;
import project.asap.gudang.infrastructure.GudangRepository;
import project.asap.utility.MessageResponse;

import java.util.List;

@Service
@Transactional
public class GudangServiceImpl implements GudangService {

    private static final Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(GudangService.class);
    private final GudangRepository gudangRepository;

    @Autowired
    public GudangServiceImpl(GudangRepository gudangRepository) {
        this.gudangRepository = gudangRepository;
    }

    @Override
    public List<Gudang> getAll() {
        return gudangRepository.findAll();
    }

    @Override
    public Gudang getById(Long id) {
        return gudangRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Gudang.class, "id", id.toString()));
    }

    @Override
    public MessageResponse save(GudangRequest gudangRequest) {
        Gudang gudang = new Gudang();
        gudang.setNama(gudangRequest.getNama());
        gudang.setCode(gudangRequest.getCode());
        gudangRepository.save(gudang);
        logger.info("kdo created");
        return new MessageResponse("gudang created", HttpStatus.OK);
    }

    @Override
    public MessageResponse update(Long id, GudangRequest gudangRequest) {
        Gudang gudang = getById(id);
        gudang.setNama(gudangRequest.getNama());
        gudang.setCode(gudangRequest.getCode());
        gudangRepository.save(gudang);
        logger.info("gudang updated");
        return new MessageResponse("gudang updated", HttpStatus.OK);
    }

    @Override
    public MessageResponse delete(Long id) {
        if (gudangRepository.existsById(id)) {
            gudangRepository.deleteById(id);
            logger.info("gudang deleted");
            return new MessageResponse("gudang deleted", HttpStatus.OK);
        } else {
            logger.info("gudang not found");
            return new MessageResponse("gudang not found", HttpStatus.NOT_FOUND);
        }

    }
}
