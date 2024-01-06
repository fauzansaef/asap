package project.asap.auth.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {

    private String username;
    private String email;
    private Collection<? extends GrantedAuthority> roles;
    private String accessToken;
    private String tokenType;

}
