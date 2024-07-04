package project.asap.file_ref.application;

import org.springframework.web.multipart.MultipartFile;
import project.asap.utility.MessageResponse;

import java.io.IOException;

public interface FileRefService {
    MessageResponse uploadExcel(MultipartFile file) throws IOException;
}
