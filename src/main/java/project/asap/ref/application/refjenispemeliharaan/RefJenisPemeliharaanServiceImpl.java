package project.asap.ref.application.refjenispemeliharaan;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.asap.exception.ResourceNotFoundException;
import project.asap.kdo.application.KdoServiceImpl;
import project.asap.ref.domain.dto.RefJenisPemeliharaanRequest;
import project.asap.ref.domain.dto.RefTipeArsipRequest;
import project.asap.ref.domain.entity.RefJenisPemeliharaan;
import project.asap.ref.domain.entity.RefTipeArsip;
import project.asap.ref.infrastructure.RefJenisPemeliharaanRepository;
import project.asap.utility.MessageResponse;

@Service
@Transactional
public class RefJenisPemeliharaanServiceImpl implements RefJenisPemeliharaanService{
    private static final Logger logger = (Logger) LoggerFactory.getLogger(KdoServiceImpl.class);
    private final RefJenisPemeliharaanRepository refJenisPemeliharaanRepository;

    public RefJenisPemeliharaanServiceImpl(RefJenisPemeliharaanRepository refJenisPemeliharaanRepository) {
        this.refJenisPemeliharaanRepository = refJenisPemeliharaanRepository;
    }

    @Override
    public Page<RefJenisPemeliharaan> getAll(int page, int size, String sort, String order, String search) {
        Sort.Direction direction = Sort.Direction.fromString(order);
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sort));
        Specification<RefJenisPemeliharaan> specification = (root, query, criteriaBuilder) -> criteriaBuilder.or(
                criteriaBuilder.isTrue(criteriaBuilder.literal(search.equals(""))),
                criteriaBuilder.like(criteriaBuilder.lower(root.get("nama")), "%" + search.toLowerCase() + "%")
        );
        return refJenisPemeliharaanRepository.findAll(specification, pageable);
    }

    @Override
    public RefJenisPemeliharaan getById(Long id) {
        return refJenisPemeliharaanRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(RefJenisPemeliharaan.class, "id", id.toString()));
    }

    @Override
    public MessageResponse save(RefJenisPemeliharaanRequest refJenisPemeliharaanRequest) {
        try {
            RefJenisPemeliharaan refJenisPemeliharaan = new RefJenisPemeliharaan();
            refJenisPemeliharaan.setNama(refJenisPemeliharaanRequest.getNama());
            refJenisPemeliharaanRepository.save(refJenisPemeliharaan);
            logger.info("refJenisPemeliharaan created");
            return new MessageResponse("refJenisPemeliharaan created", HttpStatus.OK);
        } catch (Exception e) {
            logger.error("failed to create refJenisPemeliharaan", e);
            return new MessageResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public MessageResponse update(Long id, RefJenisPemeliharaanRequest refJenisPemeliharaanRequest) {
        try {
            RefJenisPemeliharaan refJenisPemeliharaan = getById(id);
            refJenisPemeliharaan.setNama(refJenisPemeliharaanRequest.getNama());
            refJenisPemeliharaanRepository.save(refJenisPemeliharaan);
            logger.info("refJenisPemeliharaan updated");
            return new MessageResponse("refJenisPemeliharaan updated", HttpStatus.OK);

        } catch (Exception e) {
            logger.error("failed to update refJenisPemeliharaan", e);
            return new MessageResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public MessageResponse delete(Long id) {
        if (refJenisPemeliharaanRepository.existsById(id)) {
            refJenisPemeliharaanRepository.deleteById(id);
            logger.info("refJenisPemeliharaan deleted");
            return new MessageResponse("refJenisPemeliharaan deleted", HttpStatus.OK);
        } else {
            logger.error("failed to delete refJenisPemeliharaan");
            return new MessageResponse("failed to delete refJenisPemeliharaan", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
