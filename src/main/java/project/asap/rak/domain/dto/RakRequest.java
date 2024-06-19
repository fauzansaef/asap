package project.asap.rak.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class RakRequest {
    @NotNull
    @NotEmpty
    private String nama;
    @NotNull
    @NotEmpty
    private String code;
    @NotNull
    private Long idLemari;
}
