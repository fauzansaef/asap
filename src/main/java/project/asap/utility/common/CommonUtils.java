package project.asap.utility.common;

import org.apache.tika.Tika;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.multipart.MultipartFile;
import project.asap.security.domain.UserDetailsImpl;
import project.asap.users.domain.entity.Users;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class CommonUtils {
    public static final List<String> contentTypes = Arrays.asList("application/pdf", "image/jpg", "image/jpeg", "image/png");

    public static String cekFile(MultipartFile file) throws IOException {
        Tika tika = new Tika();
        return tika.detect(file.getBytes());
    }

    public static String getNipPegawai() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getIp();
    }

    public static String noRequest(String jenisRequest) {
        /** 2 digit tahun + bulan + tanggal + jenis request (lihat di kolom type) + sequence 3 digit **/
        String tahun2Digit = String.valueOf(LocalDate.now().getYear()).substring(2);
        String bulan = String.valueOf(LocalDate.now().getMonthValue());
        String tanggal = String.valueOf(LocalDate.now().getDayOfMonth());
        String sequence = "001";

        //kekmana cara buat sequence nya?
        //buat table baru di database untuk menampung sequence dari masing2 jenis request
        //gunakan : int number = 7;
        //String numberWithZeroPrefix = String.format("%03d", number);
        //System.out.println(numberWithZeroPrefix);  // Output: 007
        return tahun2Digit + bulan + tanggal + jenisRequest + sequence;
    }
}
