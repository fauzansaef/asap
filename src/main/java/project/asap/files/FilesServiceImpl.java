package project.asap.files;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import project.asap.exception.CustomValidationException;
import project.asap.exception.FileUploadExceptionAdvice;
import project.asap.utility.common.CommonUtils;

import javax.annotation.PostConstruct;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.Resource;

import java.util.Optional;
import java.util.UUID;

@Service
public class FilesServiceImpl implements FilesService {
    private static final Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(FilesServiceImpl.class);

    @Value("${file.directory}")
    private String fileDirectory;

    @Override
    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(Paths.get(fileDirectory));
        } catch (IOException e) {
            throw new FileUploadExceptionAdvice(e);
        }
    }

    @Override
    public String save(MultipartFile file) {
        try {
            if (!CommonUtils.contentTypes.contains(CommonUtils.cekFile(file))) {
                logger.info("file not allowed !");
                throw new CustomValidationException(HttpStatus.NOT_ACCEPTABLE, "file not allowed !");
            }

            Path path = Paths.get(fileDirectory);
            if (!Files.exists(path)) {
                init();
            }

            String fileName;
            fileName = UUID.randomUUID() + "." + Optional.ofNullable(file.getOriginalFilename()).filter(f -> f.contains(".")).map(f -> f.substring(file.getOriginalFilename().lastIndexOf(".") + 1)).get();

            byte[] bytes = file.getBytes();
            FileOutputStream fileOutputStream = new FileOutputStream(fileDirectory + fileName);
            fileOutputStream.write(bytes);
            fileOutputStream.close();

            logger.info("file uploaded : " + fileName);
            return fileName;

        } catch (Exception e) {
            logger.error("file upload failed");
            throw new FileUploadExceptionAdvice(e);
        }

    }

    @Override
    public Resource load(String filename) {
        try {
            Path path = Paths.get(fileDirectory + filename);
            return new InputStreamResource(Files.newInputStream(new UrlResource(path.toUri()).getFile().toPath()));
        } catch (Exception e) {
            logger.error("file not found");
            throw new CustomValidationException(HttpStatus.NOT_FOUND, "file not found");
        }
    }

    @Override
    public void delete(String filename) {
        try {
            Path path = Paths.get(fileDirectory);
            Files.delete(path.resolve(filename));
            logger.info("file deleted : " + filename);
        } catch (Exception e) {
            logger.error("file delete failed");
            throw new FileUploadExceptionAdvice(e);
        }
    }
}
