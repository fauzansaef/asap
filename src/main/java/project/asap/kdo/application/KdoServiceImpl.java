package project.asap.kdo.application;

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
import project.asap.exception.ResourceNotFoundException;
import project.asap.files.FilesService;
import project.asap.kdo.domain.dto.KdoRequest;
import project.asap.kdo.domain.dto.ReqKdoRequest;
import project.asap.kdo.domain.entity.Kdos;
import project.asap.kdo.domain.entity.ReqKdoDetails;
import project.asap.kdo.domain.entity.ReqKdos;
import project.asap.kdo.infrastructure.KdosRepository;
import project.asap.kdo.infrastructure.ReqKdoDetailsRepository;
import project.asap.kdo.infrastructure.ReqKdosRepository;
import project.asap.utility.MessageResponse;
import project.asap.utility.common.CommonUtils;
import project.asap.utility.common.domain.Reqs;
import project.asap.utility.common.infrastructure.ReqsRepository;

@Service
public class KdoServiceImpl implements KdoService {
    private static final Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(KdoServiceImpl.class);
    private final KdosRepository kdoRepository;
    private final ReqsRepository reqsRepository;
    private final ReqKdosRepository reqKdosRepository;
    private final ReqKdoDetailsRepository reqKdoDetailsRepository;
    private final FilesService filesService;

    @Autowired
    public KdoServiceImpl(KdosRepository kdoRepository, ReqsRepository reqsRepository, ReqKdosRepository reqKdosRepository, ReqKdoDetailsRepository reqKdoDetailsRepository, FilesService filesService) {
        this.kdoRepository = kdoRepository;
        this.reqsRepository = reqsRepository;
        this.reqKdosRepository = reqKdosRepository;
        this.reqKdoDetailsRepository = reqKdoDetailsRepository;
        this.filesService = filesService;
    }

    @Override
    public Page<Kdos> getAll(int page, int size, String sort, String order, String search) {
        Sort.Direction direction = Sort.Direction.fromString(order);
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sort));
        Specification<Kdos> specification = (root, query, criteriaBuilder) -> criteriaBuilder.or(
                criteriaBuilder.isTrue(criteriaBuilder.literal(search.equals(""))),
                criteriaBuilder.like(criteriaBuilder.lower(root.get("jenis")), "%" + search.toLowerCase() + "%"),
                criteriaBuilder.like(criteriaBuilder.lower(root.get("nomorPlat")), "%" + search.toLowerCase() + "%"),
                criteriaBuilder.like(criteriaBuilder.lower(root.get("tahun")), "%" + search.toLowerCase() + "%")
        );
        return kdoRepository.findAll(specification, pageable);
    }

    @Override
    public Kdos getById(Long id) {
        return kdoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Kdos.class, "id", id.toString()));
    }

    @Override
    public MessageResponse save(KdoRequest kdoRequest) {
        try {
            Kdos kdos = new Kdos();
            kdos.setJenis(kdoRequest.getJenis());
            kdos.setNomorPlat(kdoRequest.getNomorPlat());
            kdos.setTahun(kdoRequest.getTahun());
            kdos.setDeskripsi(kdoRequest.getDeskripsi());
            kdos.setPhoto(kdoRequest.getPhoto());
            kdos.setReady(1);
            kdoRepository.save(kdos);
            logger.info("kdo created");
            return new MessageResponse("kdo created", HttpStatus.OK);
        } catch (Exception e) {
            logger.error("failed to create kdo", e);
            return new MessageResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public MessageResponse update(Long id, KdoRequest kdoRequest) {
        try {
            Kdos kdos = getById(id);
            kdos.setJenis(kdoRequest.getJenis());
            kdos.setTahun(kdoRequest.getTahun());
            kdos.setDeskripsi(kdoRequest.getDeskripsi());

            if (kdos.getPhoto() != null) {
                filesService.delete(kdos.getPhoto());
                logger.info("delete old photo kdo");
            }

            kdos.setPhoto(kdoRequest.getPhoto());
            kdoRepository.save(kdos);
            logger.info("kdo updated");
            return new MessageResponse("kdo updated", HttpStatus.OK);

        } catch (Exception e) {
            logger.error("failed to update kdo", e);
            return new MessageResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public MessageResponse delete(Long id) {
        if (kdoRepository.existsById(id)) {
            if (getById(id).getPhoto() != null) {
                filesService.delete(getById(id).getPhoto());
                logger.info("delete photo kdo");
            }
            kdoRepository.deleteById(id);
            logger.info("kdo deleted");
            return new MessageResponse("kdo deleted", HttpStatus.OK);
        } else {
            logger.error("kdo not found");
            return new MessageResponse("kdo not found", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @Transactional
    public MessageResponse requestKdo(ReqKdoRequest reqKdoRequest) {
        Reqs reqs = new Reqs();
        reqs.setNoRequest(CommonUtils.noRequest(String.valueOf(2)));//2 = jenis request kdo
        reqs.setUserId(CommonUtils.getIdUser());
        reqs.setType(2);
        reqs.setStatusReq(0);//draft
        reqs.setRequestNote(reqKdoRequest.getRequestNote());
        reqsRepository.save(reqs);
        logger.info("Reqs kdo created");

        ReqKdos reqKdos = new ReqKdos();
        reqKdos.setReqId(reqs.getId());
        reqKdos.setNotaDinas(reqKdoRequest.getNotaDinas());
        reqKdos.setTujuan(reqKdoRequest.getTujuan());
        reqKdos.setTglPinjam(reqKdoRequest.getTglPinjam());
        reqKdos.setTglKembali(reqKdoRequest.getTglKembali());
        reqKdos.setPemakai(reqKdoRequest.getPemakai());
        reqKdos.setNamaPeminjam(reqKdoRequest.getNamaPeminjam());
        reqKdosRepository.save(reqKdos);
        logger.info("ReqKdos kdo created");

        ReqKdoDetails reqKdoDetails = new ReqKdoDetails();
        reqKdoDetails.setReqKdoId(reqKdos.getId());
        reqKdoDetails.setKdoId(reqKdoRequest.getIdKdo());
        reqKdoDetailsRepository.save(reqKdoDetails);
        logger.info("ReqKdoDetails kdo created");

        logger.info("request kdo created");
        return new MessageResponse("request kdo created", HttpStatus.OK);

    }
}
