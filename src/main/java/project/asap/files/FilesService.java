package project.asap.files;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;



public interface FilesService {
    void init();

    String save(MultipartFile file);

    Resource load(String filename);

    void delete(String filename);
}
