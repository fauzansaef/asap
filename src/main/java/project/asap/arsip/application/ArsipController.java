package project.asap.arsip.application;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.asap.arsip.domain.dto.TambahArsipRequest;

@RestController
@RequestMapping("/api/v1/arsip")
@Tag(name = "Arsip", description = "API Transaction Arsip")
public class ArsipController {
    private final ArsipService arsipService;

    @Autowired
    public ArsipController(ArsipService arsipService) {
        this.arsipService = arsipService;
    }

    @GetMapping("")
    ResponseEntity<?> getAll(@RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "10") int size,
                             @RequestParam(defaultValue = "id") String sort,
                             @RequestParam(defaultValue = "asc") String order,
                             @RequestParam(defaultValue = "") String search) {
        return ResponseEntity.ok(arsipService.getAll(page, size, sort, order, search));
    }

    @GetMapping("/{id}")
    ResponseEntity<?> getById(@RequestParam Long id) {
        return ResponseEntity.ok(arsipService.getById(id));
    }

    @PostMapping("")
    ResponseEntity<?> save(@RequestBody TambahArsipRequest request) {
        return ResponseEntity.ok(arsipService.save(request));
    }

    @PatchMapping("/{id}")
    ResponseEntity<?> update(@PathVariable Long id, @RequestBody TambahArsipRequest request) {
        return ResponseEntity.ok(arsipService.update(id, request));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(@PathVariable Long id) {
        return ResponseEntity.ok(arsipService.delete(id));
    }
}
