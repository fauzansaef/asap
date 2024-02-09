package project.asap.atk.controller;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import project.asap.atk.dto.DetailPembelianAtkRequest;
import project.asap.atk.model.AtkPurchaseDetails;
import project.asap.atk.model.AtkPurchases;
import project.asap.atk.model.Atks;
import project.asap.atk.service.AtkService;
import project.asap.utility.MessageResponse;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/atk")
public class AtksController {
    private static final Logger logger = (Logger) LoggerFactory.getLogger(AtksController.class);

    @Autowired
    AtkService atkService;

    @GetMapping("/generatecode")
    public String generateCode() {
        return atkService.generateCode();
    }

    @GetMapping("/atkbyid")
    public Optional<Atks> findAtksById(@RequestParam(name = "id", required = false) Integer id) {
        return atkService.getAtkById(id);
    }

    @GetMapping("/listallatk")
    public List<Atks> listAllAtks(
            @RequestParam(name = "kode", required = false) String kode,
            @RequestParam(name = "nama", required = false) String nama) {
        return atkService.listAllAtk(kode, nama);
    }

    @PostMapping("/tambahatk")
    public MessageResponse tambahAtk(@RequestBody Atks atk,
                                     @RequestParam(defaultValue = "true") Boolean autoGenerateCode) {
        return atkService.addAtk(atk, autoGenerateCode);
    }

    @PatchMapping("/editatk")
    public MessageResponse editAtk(@RequestBody Atks atk, @RequestParam Integer id) {
        return atkService.editAtk(atk, id);
    }

    @DeleteMapping("/hapusatk")
    public MessageResponse hapusAtk(@RequestParam Integer id) {
        return atkService.hapusAtk(id);
    }

    @PostMapping("/pembelian")
    public AtkPurchases pembelian(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate tanggalPembelian,
                                  @RequestParam String nomorPo) {
        return atkService.pembelianBaruAtk(tanggalPembelian, nomorPo);
    }

    @PatchMapping("/pembelian/{id}/batalkan")
    public MessageResponse batalkanPembelian(@PathVariable Integer id) {
        return atkService.batalkanPembelianAtk(id);
    }

    @PostMapping("/pembelian/detail")
    public AtkPurchaseDetails tambahDetailPembelian(@RequestBody @Valid DetailPembelianAtkRequest detailPembelianAtkRequest) {
        return atkService.detailPembelianAtk(detailPembelianAtkRequest);
    }

    @DeleteMapping("/pembelian/detail/{id}")
    public MessageResponse hapusDetailPembelian(@RequestParam Integer id) {
        return atkService.hapusDetailPembelianAtk(id);
    }

    @PatchMapping("/pembelian/selesai/{id}")
    public MessageResponse selesaiPembelian(@PathVariable Integer id) {
        return atkService.selesaiPembelianAtk(id);
    }

    @GetMapping("/pembelian/by_no_po/{no_po}")
    public AtkPurchases detailPembelian(@PathVariable(value = "no_po") String noPo) {
        return atkService.getAtkPurchasesByNomorPo(noPo);
    }

    @GetMapping("/pembelian/draft")
    public List<AtkPurchases> listDraftPembelian() {
        return atkService.listDraftPembelian();
    }

    @PatchMapping("/{id}/opname")
    public MessageResponse opnameAtk(@PathVariable Integer id, @RequestParam Integer jumlah) {
        return atkService.opnameStockAtk(id, jumlah);
    }


}
