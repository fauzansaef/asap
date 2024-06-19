package project.asap.ref.application.refjenispemeliharaan;


import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.asap.ref.domain.dto.RefJenisPemeliharaanRequest;
import project.asap.ref.domain.dto.RefTipeArsipRequest;
import project.asap.ref.domain.entity.RefJenisPemeliharaan;
import project.asap.ref.domain.entity.RefTipeArsip;
import project.asap.utility.MessageResponse;

@RestController
@RequestMapping("/api/v1/ref-jenis-pemeliharaan")
@Tag(name = "Ref Jenis Pemeliharaan", description = "API Transaction Referensi Jenis Pemeliharaan")
public class RefJenisPemeliharaanController {
    private final RefJenisPemeliharaanService refJenisPemeliharaanService;

    @Autowired
    public RefJenisPemeliharaanController(RefJenisPemeliharaanService refJenisPemeliharaanService) {
        this.refJenisPemeliharaanService = refJenisPemeliharaanService;
    }

    @GetMapping("")
    ResponseEntity<Page<RefJenisPemeliharaan>> getAll(@RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "10") int size,
                                                      @RequestParam(defaultValue = "id") String sort,
                                                      @RequestParam(defaultValue = "asc") String order,
                                                      @RequestParam(defaultValue = "") String search) {
        return new ResponseEntity<>(refJenisPemeliharaanService.getAll(page, size, sort, order, search), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    ResponseEntity<RefJenisPemeliharaan> getById(@RequestParam Long id) {
        return new ResponseEntity<>(refJenisPemeliharaanService.getById(id), HttpStatus.OK);
    }

    @PostMapping("")
    ResponseEntity<MessageResponse> save(@RequestBody RefJenisPemeliharaanRequest refJenisPemeliharaanRequest) {
        return new ResponseEntity<>(refJenisPemeliharaanService.save(refJenisPemeliharaanRequest), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    ResponseEntity<MessageResponse> update(@PathVariable Long id, @RequestBody RefJenisPemeliharaanRequest refJenisPemeliharaanRequest) {
        return new ResponseEntity<>(refJenisPemeliharaanService.update(id, refJenisPemeliharaanRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<MessageResponse> delete(@PathVariable Long id) {
        return new ResponseEntity<>(refJenisPemeliharaanService.delete(id), HttpStatus.OK);
    }
}
