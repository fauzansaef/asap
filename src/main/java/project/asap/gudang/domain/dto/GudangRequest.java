package project.asap.gudang.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class GudangRequest {
    @NotNull
    @NotEmpty
    private String nama;
    @NotNull
    @NotEmpty
    private String code;
}
