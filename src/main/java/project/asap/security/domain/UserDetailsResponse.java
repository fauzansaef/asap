package project.asap.security.domain;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
public class UserDetailsResponse {
    private Long id;
    private String ip;
    private String email;
    private Collection<? extends GrantedAuthority> role;

    public UserDetailsResponse(Long id, String ip, String email, Collection<? extends GrantedAuthority> role) {
        this.id = id;
        this.ip = ip;
        this.email = email;
        this.role = role;
    }
}
