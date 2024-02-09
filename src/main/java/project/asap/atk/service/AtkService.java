package project.asap.atk.service;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.asap.atk.controller.AtksController;
import project.asap.atk.dto.DetailPembelianAtkRequest;
import project.asap.atk.model.AtkOpnames;
import project.asap.atk.model.AtkPurchaseDetails;
import project.asap.atk.model.AtkPurchases;
import project.asap.atk.model.Atks;
import project.asap.atk.repository.AtkOpnamesRepository;
import project.asap.atk.repository.AtkPurchaseDetailsRepository;
import project.asap.atk.repository.AtkPurchasesRepository;
import project.asap.atk.repository.AtksRepository;
import project.asap.exception.CustomValidationException;
import project.asap.users.application.UsersService;
import project.asap.utility.CopyEntity;
import project.asap.utility.MessageResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AtkService {

    private final UsersService usersService;
    private final AtksRepository atkRepository;
    private final CopyEntity copyEntity;
    private final AtkPurchasesRepository atkPurchasesRepository;
    private final AtkPurchaseDetailsRepository atkPurchaseDetailsRepository;
    private final AtkOpnamesRepository atkOpnamesRepository;

    private static final Logger logger = (Logger) LoggerFactory.getLogger(AtksController.class);

    @Autowired
    public AtkService(UsersService usersService, AtksRepository atkRepository, CopyEntity copyEntity, AtkPurchasesRepository atkPurchasesRepository, AtkPurchaseDetailsRepository atkPurchaseDetailsRepository, AtkOpnamesRepository atkOpnamesRepository) {
        this.usersService = usersService;
        this.atkRepository = atkRepository;
        this.copyEntity = copyEntity;
        this.atkPurchasesRepository = atkPurchasesRepository;
        this.atkPurchaseDetailsRepository = atkPurchaseDetailsRepository;
        this.atkOpnamesRepository = atkOpnamesRepository;
    }

    public String generateCode() {
        LocalDate today = LocalDate.now();
        Integer count = atkRepository.countByCreatedAt(today);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String formattedDate = today.format(formatter);
        return formattedDate + String.format("%03d", count + 1);
    }

    public Optional<Atks> getAtkById(Integer id) {
        return atkRepository.findById(id);
    }

    public List<Atks> listAllAtk(String kode, String nama) {
        kode = ("".equals(kode)) ? null : kode;
        nama = ("".equals(nama)) ? null : nama;

        Specification<Atks> spec =
                AtksRepository.AtksSpecifications.combinedSpecificationAndNotDeleted(kode, nama);
        return atkRepository.findAll(spec);
    }

    public MessageResponse addAtk(Atks atk, Boolean auto) {
        if (auto) {
            atk.setKode(generateCode());
        }
        LocalDateTime today = LocalDateTime.now();
        atk.setCreatedAt(today);
        atk.setUpdatedAt(today);
        try {
            atkRepository.save(atk);
        } catch (Exception e) {
            logger.info("ERROR addatk: " + e);
            return new MessageResponse("data gagal ditambah : " + e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new MessageResponse("data berhasil ditambah", HttpStatus.OK);
    }

    public MessageResponse editAtk(Atks atk, Integer id) {
        Optional<Atks> oldAtkOptional = atkRepository.findById(id);
        if (!oldAtkOptional.isPresent()) {
            return new MessageResponse("Data atk tidak ditemukan dengan ID: " + id, HttpStatus.NOT_FOUND);
        }
        Atks oldAtk = oldAtkOptional.get();
        LocalDateTime today = LocalDateTime.now();
        oldAtk.setUpdatedAt(today);
        copyEntity.copyNonNullProperties(atk, oldAtk);
        try {
            atkRepository.save(oldAtk);
        } catch (Exception e) {
            logger.info("ERROR editatk: " + e);
            return new MessageResponse("data gagal diedit : " + e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new MessageResponse("data berhasil diedit", HttpStatus.OK);
    }

    public MessageResponse hapusAtk(Integer id) {
        Optional<Atks> oldAtkOptional = atkRepository.findById(id);
        if (!oldAtkOptional.isPresent()) {
            return new MessageResponse("Data atk tidak ditemukan dengan ID: " + id, HttpStatus.NOT_FOUND);
        }
        Atks oldAtk = oldAtkOptional.get();
        LocalDateTime today = LocalDateTime.now();
        oldAtk.setUpdatedAt(today);
        oldAtk.setDeletedAt(today);
        try {
            atkRepository.save(oldAtk);
        } catch (Exception e) {
            logger.info("ERROR delete atk: " + e);
            return new MessageResponse("data gagal dihapus : " + e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new MessageResponse("data berhasil dihapus", HttpStatus.OK);
    }

    public AtkPurchases pembelianBaruAtk(LocalDate tglPembelian, String nomorPo) {

        if (atkPurchasesRepository.findByNomorPoAndStatus(nomorPo, 3).isPresent()) {
            throw new CustomValidationException(HttpStatus.BAD_REQUEST, "nomor PO : " + nomorPo + " Telah tersedia");
        }

        AtkPurchases atkPurchases = new AtkPurchases();
        atkPurchases.setUserId(usersService.getUserId().intValue());
        atkPurchases.setNomorPo(nomorPo);
        atkPurchases.setTglPembelian(tglPembelian);
        atkPurchases.setTotal("0");
        atkPurchases.setStatus(1); // 1=draft , 2=cancel, 3=purchase
        atkPurchasesRepository.save(atkPurchases);
        logger.info("pembelian baru atk");
        return atkPurchases;
    }

    public MessageResponse batalkanPembelianAtk(Integer atkPurchaseId) {
        try {
            Optional<AtkPurchases> atkPurchasesOptional = atkPurchasesRepository.findById(atkPurchaseId);
            if (!atkPurchasesOptional.isPresent()) {
                return new MessageResponse("Data pembelian atk tidak ditemukan dengan ID: " + atkPurchaseId, HttpStatus.NOT_FOUND);
            }
            AtkPurchases atkPurchases = atkPurchasesOptional.get();
            atkPurchases.setStatus(2); // 1=draft , 2=cancel, 3=purchase
            atkPurchasesRepository.save(atkPurchases);
            logger.info("batalkan pembelian atk");
            return new MessageResponse("pembelian atk berhasil dibatalkan", HttpStatus.OK);
        } catch (Exception e) {
            logger.info("ERROR batalkan pembelian atk: " + e);
            return new MessageResponse("pembelian atk gagal dibatalkan : " + e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public AtkPurchaseDetails detailPembelianAtk(DetailPembelianAtkRequest detailPembelianAtkRequest) {
        try {
            AtkPurchaseDetails atkPurchaseDetails = new AtkPurchaseDetails();
            atkPurchaseDetails.setAtkPurchaseId(detailPembelianAtkRequest.getAtkPurchaseId());
            atkPurchaseDetails.setAtkId(detailPembelianAtkRequest.getAtkId());
            atkPurchaseDetails.setJumlah(detailPembelianAtkRequest.getJumlah());
            atkPurchaseDetails.setHarga(detailPembelianAtkRequest.getHarga());
            atkPurchaseDetails.setSubTotal(detailPembelianAtkRequest.getSubTotal());
            atkPurchaseDetailsRepository.save(atkPurchaseDetails);
            logger.info("detail pembelian atk");
            return atkPurchaseDetails;
        } catch (Exception e) {
            logger.info("ERROR detail pembelian atk: " + e);
            throw new CustomValidationException(HttpStatus.INTERNAL_SERVER_ERROR, "data detail pembelian atk gagal ditambah : " + e);
        }
    }

    public MessageResponse hapusDetailPembelianAtk(Integer id) {
        Optional<AtkPurchaseDetails> atkPurchaseDetail = atkPurchaseDetailsRepository.findById(id);
        if (!atkPurchaseDetail.isPresent()) {
            return new MessageResponse("Data detail pembelian atk tidak ditemukan dengan ID: " + id, HttpStatus.NOT_FOUND);
        }

        try {
            atkPurchaseDetailsRepository.deleteById(atkPurchaseDetail.get().getId());
            logger.info("hapus detail pembelian atk");
            return new MessageResponse("detail pembelian atk berhasil dihapus", HttpStatus.OK);
        } catch (Exception e) {
            logger.info("ERROR hapus detail pembelian atk: " + e);
            return new MessageResponse("detail pembelian atk gagal dihapus : " + e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public MessageResponse selesaiPembelianAtk(Integer atkPurchaseId) {
        try {
            Optional<AtkPurchases> atkPurchase = atkPurchasesRepository.findById(atkPurchaseId);
            if (!atkPurchase.isPresent()) {
                return new MessageResponse("Data pembelian atk tidak ditemukan dengan ID: " + atkPurchaseId, HttpStatus.NOT_FOUND);
            }
            List<AtkPurchaseDetails> atkPurchaseDetails = atkPurchaseDetailsRepository.findByAtkPurchaseId(atkPurchaseId);
            if (atkPurchaseDetails.size() == 0) {
                return new MessageResponse("Detail pembelian atk tidak ditemukan dengan ID: " + atkPurchaseId, HttpStatus.NOT_FOUND);
            }

            int total = 0;

            for (AtkPurchaseDetails atkPurchaseDetail : atkPurchaseDetails) {
                total = total + Integer.parseInt(atkPurchaseDetail.getSubTotal());
            }

            AtkPurchases atkPurchases = atkPurchase.get();
            atkPurchases.setTotal(String.valueOf(total));
            atkPurchases.setStatus(3); // 1=draft , 2=cancel, 3=purchase
            atkPurchasesRepository.save(atkPurchases);
            logger.info("selesai pembelian atk");
            return new MessageResponse("pembelian atk berhasil diselesaikan", HttpStatus.OK);
        } catch (Exception e) {
            logger.info("ERROR selesai pembelian atk: " + e);
            return new MessageResponse("pembelian atk gagal diselesaikan : " + e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public AtkPurchases getAtkPurchasesByNomorPo(String nomorPo) {
        Optional<AtkPurchases> atkPurchases = atkPurchasesRepository.findByNomorPoAndStatus(nomorPo, 3);
        if (!atkPurchases.isPresent()) {
            throw new CustomValidationException(HttpStatus.NOT_FOUND, "Data pembelian atk tidak ditemukan dengan nomor po: " + nomorPo);
        }
        return atkPurchases.get();
    }

    public List<AtkPurchases> listDraftPembelian() {
        return atkPurchasesRepository.findByUserIdAndStatus(usersService.getUserId().intValue(), 1);
    }


    public MessageResponse opnameStockAtk(Integer id, Integer jumlah) {
        Optional<Atks> atk = atkRepository.findById(id);
        if (!atk.isPresent()) {
            return new MessageResponse("Data atk tidak ditemukan dengan ID: " + id, HttpStatus.NOT_FOUND);
        }

        AtkOpnames atkOpnames = new AtkOpnames();
        atkOpnames.setAtkId(id);
        atkOpnames.setUserId(usersService.getUserId().intValue());
        if (jumlah - atk.get().getStock() >= 0) {
            atkOpnames.setType(1);//1=add, 2=less
        } else {
            atkOpnames.setType(2);//1=add, 2=less
        }

        atkOpnames.setJumlah(Math.abs(jumlah - atk.get().getStock()));
        atkOpnamesRepository.save(atkOpnames);
        logger.info("opname stock atk");

        Atks atkData = atk.get();
        atkData.setStock(jumlah);
        atkRepository.save(atkData);
        logger.info("update stock atk");
        return new MessageResponse("Stock atk berhasil diopname", HttpStatus.OK);
    }

}