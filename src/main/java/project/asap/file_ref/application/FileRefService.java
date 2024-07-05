package project.asap.file_ref.application;

import org.springframework.web.multipart.MultipartFile;
import project.asap.file_ref.domain.FileRef;
import project.asap.utility.MessageResponse;

import java.io.IOException;
import java.util.List;

public interface FileRefService {
    MessageResponse uploadExcel(MultipartFile file) throws IOException;
    List<FileRef> listStatusFileRef();
}
