package project.asap.auth.domain;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class AuthRequest {
    @NotEmpty
    @NotNull
    private String username;
    @NotEmpty
    @NotNull
    private String password;
}
