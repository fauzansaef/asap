package project.asap.file_ref.application;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/file_refs")
@Tag(name = "Upload", description = "API Upload file")
public class FileRefController {
    private final FileRefService fileRefService;

    @Autowired
    public FileRefController(FileRefService fileRefService) {
        this.fileRefService = fileRefService;
    }

    @PostMapping("/excel")
    ResponseEntity<?> uploadExcel(@RequestPart("file") MultipartFile file) throws IOException {
        return ResponseEntity.ok().body(fileRefService.uploadExcel(file));
    }

    @GetMapping("/")
    ResponseEntity<?> listFileRef() {
        return ResponseEntity.ok().body(fileRefService.listFileRef());
    }

    @GetMapping("/job/log_error/{id_file_ref}")
    ResponseEntity<?> listFailedJobsExcel(@PathVariable(value = "id_file_ref") Long idFileRef) {
        return ResponseEntity.ok().body(fileRefService.listFailedJobsExcel(idFileRef));
    }
}
