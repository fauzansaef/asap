package project.asap.utility.common;

import org.apache.tika.Tika;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class CommonUtils {
    public static final List<String> contentTypes = Arrays.asList("application/pdf", "image/jpg", "image/jpeg", "image/png");

    public static String cekFile(MultipartFile file) throws IOException {
        Tika tika = new Tika();
        return tika.detect(file.getBytes());
    }
}
