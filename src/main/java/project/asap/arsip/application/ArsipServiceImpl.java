package project.asap.arsip.application;

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
import project.asap.arsip.domain.dto.TambahArsipRequest;
import project.asap.arsip.domain.entity.Arsip;
import project.asap.arsip.infrastructure.ArsipRepository;
import project.asap.exception.ResourceNotFoundException;
import project.asap.utility.MessageResponse;
import project.asap.utility.common.CommonUtils;

@Service
@Transactional
public class ArsipServiceImpl implements ArsipService {
    private static final Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(ArsipServiceImpl.class);
    private final ArsipRepository arsipRepository;

    @Autowired
    public ArsipServiceImpl(ArsipRepository arsipRepository) {
        this.arsipRepository = arsipRepository;
    }

    @Override
    public Page<Arsip> getAll(int page, int size, String sort, String order, String search) {
        Sort.Direction direction = Sort.Direction.fromString(order);
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sort));
        Specification<Arsip> specification = (root, query, criteriaBuilder) -> criteriaBuilder.or(
                criteriaBuilder.isTrue(criteriaBuilder.literal(search.equals(""))),
                criteriaBuilder.like(criteriaBuilder.lower(root.get("kode")), "%" + search.toLowerCase() + "%"),
                criteriaBuilder.like(criteriaBuilder.lower(root.get("nama")), "%" + search.toLowerCase() + "%"),
                criteriaBuilder.like(criteriaBuilder.lower(root.get("tahun")), "%" + search.toLowerCase() + "%")
        );
        return arsipRepository.findAll(specification, pageable);
    }

    @Override
    public Arsip getById(Long id) {
        return arsipRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Arsip.class, "id", id.toString()));
    }

    @Override
    public MessageResponse save(TambahArsipRequest request) {
        Arsip arsip = new Arsip();
        arsip.setIdTipeArsip(request.getIdTipeArsip());
        arsip.setKode(request.getKode());
        arsip.setNama(request.getNama());
        arsip.setTahun(request.getTahun());
        arsip.setDeskripsi(request.getDeskripsi());
        arsip.setJumlahLembar(request.getJumlahLembar());
        arsip.setFile(request.getFile());
        arsip.setIdGudang(request.getIdGudang());
        arsip.setIdLemari(request.getIdLemari());
        arsip.setIdRak(request.getIdRak());
        arsip.setIdBox(request.getIdBox());
        arsip.setStatus(1);//1=disimpan, 0=dipinjam
        arsip.setNipPetugas(CommonUtils.getNipPegawai());
        arsipRepository.save(arsip);
        logger.info("arsip created");
        return new MessageResponse("arsip created", HttpStatus.OK);
    }

    @Override
    public MessageResponse update(Long id, TambahArsipRequest request) {
        Arsip arsip = getById(id);
        arsip.setIdTipeArsip(request.getIdTipeArsip());
        arsip.setKode(request.getKode());
        arsip.setNama(request.getNama());
        arsip.setTahun(request.getTahun());
        arsip.setDeskripsi(request.getDeskripsi());
        arsip.setJumlahLembar(request.getJumlahLembar());
        arsip.setFile(request.getFile());
        arsip.setIdGudang(request.getIdGudang());
        arsip.setIdLemari(request.getIdLemari());
        arsip.setIdRak(request.getIdRak());
        arsip.setIdBox(request.getIdBox());
        arsip.setStatus(1);//1=disimpan, 0=dipinjam
        arsip.setNipPetugas(CommonUtils.getNipPegawai());
        arsipRepository.save(arsip);
        logger.info("arsip updated");
        return new MessageResponse("arsip updated", HttpStatus.OK);

    }

    @Override
    public MessageResponse delete(Long id) {
        if (arsipRepository.existsById(id)) {
            arsipRepository.deleteById(id);
            logger.info("arsip deleted");
            return new MessageResponse("arsip deleted", HttpStatus.OK);
        } else {
            logger.info("arsip not found");
            return new MessageResponse("arsip not found", HttpStatus.NOT_FOUND);
        }
    }
}
