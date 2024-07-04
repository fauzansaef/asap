package project.asap.file_ref.application;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import project.asap.file_ref.domain.FileRef;
import project.asap.file_ref.infrsatructure.FileRefRepository;
import project.asap.files.FilesService;
import project.asap.utility.MessageResponse;
import project.asap.utility.common.CommonUtils;

import java.io.IOException;

@Service
@Transactional
public class FileRefServiceImpl implements FileRefService {
    private static final Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(FileRefServiceImpl.class);
    private final FileRefRepository fileRefRepository;
    private final FilesService filesService;

    @Autowired
    public FileRefServiceImpl(FileRefRepository fileRefRepository, FilesService filesService) {
        this.fileRefRepository = fileRefRepository;
        this.filesService = filesService;
    }

    @Override
    public MessageResponse uploadExcel(MultipartFile file) throws IOException {
        String fileName = filesService.save(file);
        FileRef fileRef = new FileRef();
        fileRef.setFileName(fileName);
        fileRef.setFlagLoader(0);
        fileRef.setNipPetugas(CommonUtils.getNipPegawai());
        fileRefRepository.save(fileRef);
        logger.info("file ref uploaded");
        return new MessageResponse("file ref berhasil diupload, menunggu proses load data", HttpStatus.OK);
    }
}
