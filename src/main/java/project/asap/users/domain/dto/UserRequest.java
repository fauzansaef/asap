package project.asap.users.domain.dto;

import lombok.Data;

@Data
public class UserRequest {
    private String name;
    private String email;
    private String ipPegawai;
    private Integer role;
    private String phoneNumber;
    private String photo;
}
