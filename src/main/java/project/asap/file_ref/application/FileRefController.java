package project.asap.file_ref.application;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/upload")
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
}
