package project.asap.atk.service;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.asap.atk.controller.AtksController;
import project.asap.atk.model.Atks;
import project.asap.atk.repository.AtksRepository;
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
    @Autowired
    private AtksRepository atkRepository;

    @Autowired
    private CopyEntity copyEntity;

    private static final Logger logger = (Logger) LoggerFactory.getLogger(AtksController.class);

    public String generateCode() {
        LocalDate today = LocalDate.now();
        Integer count = atkRepository.countByCreatedAt(today);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String formattedDate = today.format(formatter);
        return formattedDate + String.format("%03d", count+1);
    }

    public Optional<Atks> getAtkById(Integer id){
        return atkRepository.findById(id);
    }

    public List<Atks> listAllAtk(String kode, String nama){
        kode = ("".equals(kode)) ? null : kode;
        nama = ("".equals(nama)) ? null : nama;

        Specification<Atks> spec =
                AtksRepository.AtksSpecifications.combinedSpecificationAndNotDeleted(kode, nama);
        return atkRepository.findAll(spec);
    }

    public MessageResponse addAtk(Atks atk,Boolean auto){
        if (auto){
            atk.setKode(generateCode());
        }
        LocalDateTime today = LocalDateTime.now();
        atk.setCreatedAt(today);
        atk.setUpdatedAt(today);
        try{
            atkRepository.save(atk);
        }catch (Exception e){
            logger.info("ERROR addatk: "+e);
            return new MessageResponse("data gagal ditambah : "+e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new MessageResponse("data berhasil ditambah", HttpStatus.OK);
    }

    public MessageResponse editAtk(Atks atk, Integer id){
        Optional<Atks> oldAtkOptional = atkRepository.findById(id);
        if (!oldAtkOptional.isPresent()) {
            return new MessageResponse("Data atk tidak ditemukan dengan ID: " + id, HttpStatus.NOT_FOUND);
        }
        Atks oldAtk = oldAtkOptional.get();
        LocalDateTime today = LocalDateTime.now();
        oldAtk.setUpdatedAt(today);
        copyEntity.copyNonNullProperties(atk,oldAtk);
        try{
            atkRepository.save(oldAtk);
        }catch (Exception e){
            logger.info("ERROR editatk: "+e);
            return new MessageResponse("data gagal diedit : "+e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new MessageResponse("data berhasil diedit", HttpStatus.OK);
    }

    public MessageResponse hapusAtk(Integer id){
        Optional<Atks> oldAtkOptional = atkRepository.findById(id);
        if (!oldAtkOptional.isPresent()) {
            return new MessageResponse("Data atk tidak ditemukan dengan ID: " + id, HttpStatus.NOT_FOUND);
        }
        Atks oldAtk = oldAtkOptional.get();
        LocalDateTime today = LocalDateTime.now();
        oldAtk.setUpdatedAt(today);
        oldAtk.setDeletedAt(today);
        try{
            atkRepository.save(oldAtk);
        }catch (Exception e){
            logger.info("ERROR delete atk: "+e);
            return new MessageResponse("data gagal dihapus : "+e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new MessageResponse("data berhasil dihapus", HttpStatus.OK);
    }


}