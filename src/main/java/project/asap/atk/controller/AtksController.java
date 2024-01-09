package project.asap.atk.controller;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import project.asap.atk.model.Atks;
import project.asap.atk.service.AtkService;
import project.asap.utility.MessageResponse;
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
    public Optional<Atks> findAtksById(
            @RequestParam(name="id", required=false) Integer id) {
        return atkService.getAtkById(id);
    }

    @GetMapping("/listallatk")
    public List<Atks> listAllAtks(
            @RequestParam(name="kode", required=false) String kode,
            @RequestParam(name="nama", required=false) String nama) {
        return atkService.listAllAtk(kode,nama);
    }

    @PostMapping("/tambahatk")
    public MessageResponse tambahAtk(@RequestBody Atks atk,
                                     @RequestParam(defaultValue = "true") Boolean autoGenerateCode){
        return atkService.addAtk(atk,autoGenerateCode);
    }

    @PatchMapping("/editatk")
    public MessageResponse editAtk(@RequestBody Atks atk,@RequestParam Integer id){
        return atkService.editAtk(atk,id);
    }

    @DeleteMapping("/hapusatk")
    public MessageResponse hapusAtk(@RequestParam Integer id){
        return atkService.hapusAtk(id);
    }


}
