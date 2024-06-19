package project.asap.lemari.application;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.asap.lemari.domain.dto.LemariRequest;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/lemaris")
@Tag(name = "Lemari", description = "API Transaction data Lemari (Pengarsipan)")
public class LemariController {
    private final LemariService lemariService;

    @Autowired
    public LemariController(LemariService lemariService) {
        this.lemariService = lemariService;
    }

    @GetMapping("")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(lemariService.getAll());
    }
    @GetMapping("/gudang/{idGudang}")
    public ResponseEntity<?> getByGudangId(@PathVariable Long idGudang) {
        return ResponseEntity.ok(lemariService.getByGudangId(idGudang));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return ResponseEntity.ok(lemariService.getById(id));
    }

    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody @Valid LemariRequest lemariRequest) {
        return ResponseEntity.ok(lemariService.save(lemariRequest));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid LemariRequest lemariRequest) {
        return ResponseEntity.ok(lemariService.update(id, lemariRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return ResponseEntity.ok(lemariService.delete(id));
    }
}
