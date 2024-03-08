package project.asap.gudang.application;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.asap.gudang.domain.dto.GudangRequest;
import project.asap.gudang.domain.entity.Gudang;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/gudangs")
@Tag(name = "Gudang", description = "API Transaction data Gudang (Pengarsipan)")
public class GudangController {
    private final GudangService gudangService;

    @Autowired
    public GudangController(GudangService gudangService) {
        this.gudangService = gudangService;
    }

    @GetMapping("")
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(gudangService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return new ResponseEntity<>(gudangService.getById(id), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody @Valid GudangRequest gudangRequest) {
        return new ResponseEntity<>(gudangService.save(gudangRequest), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,@RequestBody @Valid GudangRequest gudangRequest) {
        return new ResponseEntity<>(gudangService.update(id, gudangRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return new ResponseEntity<>(gudangService.delete(id), HttpStatus.OK);
    }

}
