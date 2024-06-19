package project.asap.users.domain.dto;

import lombok.Data;

@Data
public class UserRequest {
    private String name;
    private String email;
    private String ipPegawai;
    private String unitKerja;
    private String Jabatan;
    private Integer role;
    private String phoneNumber;
    private String photo;
    private Long sectionId;
    private Long subSectionId;

}
