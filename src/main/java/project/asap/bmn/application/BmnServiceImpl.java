package project.asap.bmn.application;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.asap.bmn.domain.dto.BmnRequest;
import project.asap.bmn.domain.entity.Bmns;
import project.asap.bmn.infrastructure.BmnsRepository;
import project.asap.exception.ResourceNotFoundException;
import project.asap.files.FilesService;
import project.asap.kdo.application.KdoServiceImpl;
import project.asap.utility.MessageResponse;

@Service
@Transactional
public class BmnServiceImpl implements BmnService {
    private static final Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(BmnServiceImpl.class);
    private final BmnsRepository bmnsRepository;
    private final FilesService filesService;

    @Autowired
    public BmnServiceImpl(BmnsRepository bmnsRepository, FilesService filesService) {
        this.bmnsRepository = bmnsRepository;
        this.filesService = filesService;
    }

    @Override
    public Page<Bmns> getAll(int page, int size, String sort, String order, String search) {
        Sort.Direction direction = Sort.Direction.fromString(order);
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sort));
        Specification<Bmns> specification = (root, query, criteriaBuilder) -> criteriaBuilder.or(
                criteriaBuilder.isTrue(criteriaBuilder.literal(search.equals(""))),
                criteriaBuilder.like(criteriaBuilder.lower(root.get("kode")), "%" + search.toLowerCase() + "%"),
                criteriaBuilder.like(criteriaBuilder.lower(root.get("namaBmn")), "%" + search.toLowerCase() + "%"),
                criteriaBuilder.like(criteriaBuilder.lower(root.get("tahun")), "%" + search.toLowerCase() + "%")
        );
        return bmnsRepository.findAll(specification, pageable);
    }

    @Override
    public Bmns getById(Long id) {
        return bmnsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Bmns.class, "id", id.toString()));
    }

    @Override
    public MessageResponse save(BmnRequest bmnRequest) {
        try {
            Bmns bmns = new Bmns();
            bmns.setKode(bmnRequest.getKode());
            bmns.setNamaBmn(bmnRequest.getNamaBmn());
            bmns.setTahun(bmnRequest.getTahun());
            bmns.setDeskripsi(bmnRequest.getDeskripsi());
            bmns.setPhoto(bmnRequest.getPhoto());
            bmns.setStock(bmnRequest.getStock());
            bmnsRepository.save(bmns);
            logger.info("bmn created");
            return new MessageResponse("bmn created", HttpStatus.OK);
        } catch (Exception e) {
            logger.error("failed to create bmn", e);
            return new MessageResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public MessageResponse update(Long id, BmnRequest bmnRequest) {
        try {
            Bmns bmns = getById(id);
            bmns.setNamaBmn(bmnRequest.getNamaBmn());
            bmns.setTahun(bmnRequest.getTahun());
            bmns.setDeskripsi(bmnRequest.getDeskripsi());
            bmns.setStock(bmnRequest.getStock());

            if(bmns.getPhoto() != null){
                filesService.delete(bmns.getPhoto());
                logger.info("delete old photo bmn");
            }

            bmns.setPhoto(bmnRequest.getPhoto());
            bmnsRepository.save(bmns);
            logger.info("bmn updated");
            return new MessageResponse("bmn updated", HttpStatus.OK);

        }catch (Exception e) {
            logger.error("failed to update bmn", e);
            return new MessageResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public MessageResponse delete(Long id) {
        if (bmnsRepository.existsById(id)) {
            bmnsRepository.deleteById(id);

            if(getById(id).getPhoto() != null){
                filesService.delete(getById(id).getPhoto());
                logger.info("delete photo bmn");
            }
            logger.info("bmn deleted");
            return new MessageResponse("bmn deleted", HttpStatus.OK);
        } else {
            logger.error("bmn not found");
            return new MessageResponse("bmn not found", HttpStatus.NOT_FOUND);
        }
    }
}
