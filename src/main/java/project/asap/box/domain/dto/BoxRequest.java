package project.asap.box.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class BoxRequest {
    @NotNull
    @NotEmpty
    private String nama;
    @NotNull
    @NotEmpty
    private String code;
    @NotNull
    private Long idRak;
}
