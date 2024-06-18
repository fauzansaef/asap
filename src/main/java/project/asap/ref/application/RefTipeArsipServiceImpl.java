package project.asap.ref.application;

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
import project.asap.ref.domain.dto.RefTipeArsipRequest;
import project.asap.ref.domain.entity.RefTipeArsip;
import project.asap.ref.infrastructure.RefTipeArsipRepository;
import project.asap.utility.MessageResponse;

@Service
@Transactional
public class RefTipeArsipServiceImpl implements RefTipeArsipService{
    private static final Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(KdoServiceImpl.class);
    private final RefTipeArsipRepository refTipeArsipRepository;

    public RefTipeArsipServiceImpl(RefTipeArsipRepository refTipeArsipRepository) {
        this.refTipeArsipRepository = refTipeArsipRepository;
    }

    @Override
    public Page<RefTipeArsip> getAll(int page, int size, String sort, String order, String search) {
        Sort.Direction direction = Sort.Direction.fromString(order);
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sort));
        Specification<RefTipeArsip> specification = (root, query, criteriaBuilder) -> criteriaBuilder.or(
                criteriaBuilder.isTrue(criteriaBuilder.literal(search.equals(""))),
                criteriaBuilder.like(criteriaBuilder.lower(root.get("nama")), "%" + search.toLowerCase() + "%")
        );
        return refTipeArsipRepository.findAll(specification, pageable);
    }

    @Override
    public RefTipeArsip getById(Long id) {
        return refTipeArsipRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(RefTipeArsip.class, "id", id.toString()));
    }

    @Override
    public MessageResponse save(RefTipeArsipRequest refTipeArsipRequest) {
        try {
            RefTipeArsip refTipeArsip = new RefTipeArsip();
            refTipeArsip.setNama(refTipeArsipRequest.getNama());
            refTipeArsipRepository.save(refTipeArsip);
            logger.info("refTipeArsip created");
            return new MessageResponse("refTipeArsip created", HttpStatus.OK);
        } catch (Exception e) {
            logger.error("failed to create refTipeArsip", e);
            return new MessageResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public MessageResponse update(Long id, RefTipeArsipRequest refTipeArsipRequest) {
        try {
            RefTipeArsip refTipeArsip = getById(id);
            refTipeArsip.setNama(refTipeArsipRequest.getNama());
            refTipeArsipRepository.save(refTipeArsip);
            logger.info("refTipeArsip updated");
            return new MessageResponse("refTipeArsip updated", HttpStatus.OK);

        } catch (Exception e) {
            logger.error("failed to update refTipeArsip", e);
            return new MessageResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public MessageResponse delete(Long id) {
        if (refTipeArsipRepository.existsById(id)) {
            refTipeArsipRepository.deleteById(id);
            logger.info("refTipeArsip deleted");
            return new MessageResponse("refTipeArsip deleted", HttpStatus.OK);
        } else {
            logger.error("failed to delete refTipeArsip");
            return new MessageResponse("failed to delete refTipeArsip", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
