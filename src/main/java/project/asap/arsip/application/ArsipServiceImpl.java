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
import project.asap.penyimpanan.domain.entity.PenyimpananMapping;
import project.asap.penyimpanan.infrastructure.PenyimpananMappingRepository;
import project.asap.utility.MessageResponse;
import project.asap.utility.common.CommonUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

@Service
@Transactional
public class ArsipServiceImpl implements ArsipService {
    private static final Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(ArsipServiceImpl.class);
    private final ArsipRepository arsipRepository;
    private final PenyimpananMappingRepository penyimpananMappingRepository;

    @Autowired
    public ArsipServiceImpl(ArsipRepository arsipRepository, PenyimpananMappingRepository penyimpananMappingRepository) {
        this.arsipRepository = arsipRepository;
        this.penyimpananMappingRepository = penyimpananMappingRepository;
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

    @Override
    public MessageResponse saveFromExcel(InputStream inputStream) {
        try {
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();

            while (rows.hasNext()) {
                Row currentRow = rows.next();

                if (currentRow.getRowNum() == 0) { // Skip header row
                    continue;
                }

                Arsip arsip = new Arsip();
                arsip.setIdTipeArsip(getCellValueAsLong(currentRow.getCell(0)));
                arsip.setKode(getCellValueAsString(currentRow.getCell(1)));
                arsip.setNama(getCellValueAsString(currentRow.getCell(2)));
                arsip.setTahun(getCellValueAsString(currentRow.getCell(3)));
                arsip.setDeskripsi(getCellValueAsString(currentRow.getCell(4)));
                arsip.setJumlahLembar(Integer.parseInt(getCellValueAsString(currentRow.getCell(5))));
                arsip.setStatus(1); // 1=disimpan, 0=dipinjam
                arsip.setNipPetugas(CommonUtils.getNipPegawai());

                arsipRepository.save(arsip);
            }

            workbook.close();
            return new MessageResponse("arsip from excel created", HttpStatus.OK);
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse Excel file", e);
        }
    }

    @Override
    public PenyimpananMapping getPenyimpananMapping(Long idArsip) {
        return penyimpananMappingRepository.findByIdArsip(idArsip)
                .orElseThrow(() -> new ResourceNotFoundException(PenyimpananMapping.class, "idArsip", idArsip.toString()));
    }

    private String getCellValueAsString(Cell cell) {
        String cellValue;
        if (cell.getCellType() == CellType.STRING) {
            cellValue = cell.getStringCellValue();
        } else if (cell.getCellType() == CellType.NUMERIC) {
            cellValue = String.valueOf((int) cell.getNumericCellValue());
        } else {
            throw new IllegalArgumentException("Invalid cell type");
        }
        return cellValue;
    }

    private Long getCellValueAsLong(Cell cell) {
        Long cellValue;
        if (cell.getCellType() == CellType.NUMERIC) {
            cellValue = (long) cell.getNumericCellValue();
        } else {
            throw new IllegalArgumentException("Invalid cell type");
        }
        return cellValue;
    }

}
