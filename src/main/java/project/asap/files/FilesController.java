package project.asap.files;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.asap.exception.FileUploadExceptionAdvice;
import project.asap.utility.MessageResponse;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/files")
@Tag(name = "Files", description = "API Transaction file")
public class FilesController {
    private final FilesService filesService;

    @Autowired
    public FilesController(FilesService filesService) {
        this.filesService = filesService;
    }


    @GetMapping(value = "/{filename:.+}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ResponseBody
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {
        Resource resource = filesService.load(filename);
        String extension = filename.substring(filename.lastIndexOf(".") + 1);
        ResponseEntity<Resource> response = null;
        switch (extension) {
            case "pdf":
                response = ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                        .contentType(MediaType.parseMediaType("application/pdf"))
                        .body(resource);
                break;

            case "jpeg":
            case "jpg":
                response = ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(resource);
                break;
        }
        return response;
    }

    @PostMapping("")
    public ResponseEntity<String> uploadFile(@RequestPart("file") MultipartFile file) {
        String fileName = filesService.save(file);
        return ResponseEntity.status(HttpStatus.OK).body(fileName);
    }

    @DeleteMapping("/{filename:.+}")
    public ResponseEntity<MessageResponse> deleteFile(@PathVariable String filename) {
        try {
            filesService.delete(filename);
            return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("Berhasil menghapus file", HttpStatus.OK));
        } catch (Exception e) {
            throw new FileUploadExceptionAdvice(e);
        }
    }
}
