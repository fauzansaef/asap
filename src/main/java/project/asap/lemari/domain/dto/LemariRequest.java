package project.asap.lemari.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class LemariRequest {
    @NotNull
    @NotEmpty
    private String nama;
    @NotNull
    @NotEmpty
    private String code;
    @NotNull
    private Long idGudang;
    private Long idUser;
}
