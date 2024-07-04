package project.asap.utility.common;

import ch.qos.logback.classic.Logger;
import org.apache.tika.Tika;
import org.apache.tika.config.TikaConfig;
import org.apache.tika.detect.Detector;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.TikaCoreProperties;
import org.apache.tika.mime.MediaType;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import project.asap.security.domain.UserDetailsImpl;
import project.asap.utility.common.domain.NoReqs;
import project.asap.utility.common.infrastructure.NoReqsRepository;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Service
public class CommonUtils {
    private static final Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(CommonUtils.class);

    public static final List<String> contentTypes = Arrays.asList("application/pdf", "image/jpg", "image/jpeg", "image/png","application/vnd.ms-excel","application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    @Autowired
    private static NoReqsRepository noReqsRepository;

    public static String cekFile(MultipartFile file) throws IOException {
        Detector detector = TikaConfig.getDefaultConfig().getDetector();
        Metadata metadata = new Metadata();
        metadata.add(TikaCoreProperties.RESOURCE_NAME_KEY, file.getOriginalFilename());
        MediaType mediaType = detector.detect(TikaInputStream.get(file.getInputStream()), metadata);
        return mediaType.toString();
    }


    public static String getNipPegawai() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getIp();
    }

    public static Long getIdUser() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getId();
    }

    public static String noRequest(String jenisRequest) {
        /** 2 digit tahun + bulan + tanggal + jenis request (lihat di kolom type) + sequence 3 digit **/
        String tahun2Digit = String.valueOf(LocalDate.now().getYear()).substring(2);
        String bulan = String.valueOf(LocalDate.now().getMonthValue());
        String tanggal = String.valueOf(LocalDate.now().getDayOfMonth());
        Integer sequence;
        String tahunBulanTanggal = tahun2Digit + bulan + tanggal;
        if (noReqsRepository.findFirstByTanggalAndTypeOrderByIdDesc(tahunBulanTanggal, Integer.parseInt(jenisRequest)).isPresent()) {
            sequence = noReqsRepository.findFirstByTanggalAndTypeOrderByIdDesc(tahunBulanTanggal, Integer.parseInt(jenisRequest)).get().getNoUrut();
            sequence++;
        } else {
            sequence = 1;
        }

        NoReqs noReqs = new NoReqs();
        noReqs.setTanggal(tahunBulanTanggal);
        noReqs.setType(Integer.parseInt(jenisRequest));
        noReqs.setNoUrut(sequence);
        noReqsRepository.save(noReqs);
        logger.info("No Request : " + tahun2Digit + bulan + tanggal + jenisRequest + String.format("%03d", sequence));
        return tahun2Digit + bulan + tanggal + jenisRequest + String.format("%03d", sequence);
    }
}
