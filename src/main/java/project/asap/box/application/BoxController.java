package project.asap.box.application;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.asap.box.domain.dto.BoxRequest;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/boxs")
@Tag(name = "Box", description = "API Transaction data Box (Pengarsipan)")
public class BoxController {
    private final BoxService boxService;

    @Autowired
    public BoxController(BoxService boxService) {
        this.boxService = boxService;
    }

    @GetMapping("")
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(boxService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/rak/{idRak}")
    public ResponseEntity<?> getByRakId(@PathVariable Long idRak) {
        return new ResponseEntity<>(boxService.getByRakId(idRak), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return new ResponseEntity<>(boxService.getById(id), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody @Valid BoxRequest boxRequest) {
        return new ResponseEntity<>(boxService.save(boxRequest), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid BoxRequest boxRequest) {
        return new ResponseEntity<>(boxService.update(id, boxRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return new ResponseEntity<>(boxService.delete(id), HttpStatus.OK);
    }
}
