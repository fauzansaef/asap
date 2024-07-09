package project.asap.arsip.application;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.asap.arsip.domain.dto.TambahArsipRequest;

import java.io.IOException;
import java.io.InputStream;

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

    @GetMapping("/penyimpanan/{id}")
    ResponseEntity<?> getPenyimpananMapping(@RequestParam Long id) {
        return ResponseEntity.ok(arsipService.getPenyimpananMapping(id));
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

//    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
//        try {
//            InputStream inputStream = file.getInputStream();
//            arsipService.saveFromExcel(inputStream);
//            return ResponseEntity.ok().body("Arsip uploaded & created successfully");
//        } catch (IOException e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file");
//        }
//    }
}
