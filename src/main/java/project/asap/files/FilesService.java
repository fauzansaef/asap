package project.asap.files;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;

import java.io.FileNotFoundException;
import java.io.IOException;


public interface FilesService {
    void init();

    String save(MultipartFile file) throws IOException;

    Resource load(String filename);

    void delete(String filename);
}
