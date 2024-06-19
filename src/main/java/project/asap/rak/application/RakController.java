package project.asap.rak.application;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.asap.rak.domain.dto.RakRequest;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/raks")
@Tag(name = "Rak", description = "API Transaction data Rak (Pengarsipan)")
public class RakController {
    private final RakService rakService;

    @Autowired
    public RakController(RakService rakService) {
        this.rakService = rakService;
    }

    @GetMapping("")
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(rakService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return new ResponseEntity<>(rakService.getById(id), HttpStatus.OK);
    }
    @GetMapping("/lemari/{idLemari}")
    public ResponseEntity<?> getByLemariId(@PathVariable Long idLemari) {
        return new ResponseEntity<>(rakService.getByLemariId(idLemari), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody @Valid RakRequest rakRequest) {
        return new ResponseEntity<>(rakService.save(rakRequest), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,@RequestBody @Valid RakRequest rakRequest) {
        return new ResponseEntity<>(rakService.update(id, rakRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return new ResponseEntity<>(rakService.delete(id), HttpStatus.OK);
    }

}
