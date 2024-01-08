package project.asap.security.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import project.asap.users.domain.entity.Users;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String username;
    private String email;
    private String ip;
    private String phoneNumber;
    private String section;
    private String subsecstion;
    @JsonIgnore
    private String password;
    private Collection<? extends GrantedAuthority> authorities;


    public UserDetailsImpl(Long id, Collection<? extends GrantedAuthority> authorities, String username, String password,
                           String email, String ip, String phoneNumber, String section, String subsecstion) {
        this.id = id;
        this.authorities = authorities;
        this.username = username;
        this.password = password;
        this.email = email;
        this.ip = ip;
        this.phoneNumber = phoneNumber;
        this.section = section;
        this.subsecstion = subsecstion;
    }

    public static UserDetailsImpl build(Users users) {
        GrantedAuthority role = new SimpleGrantedAuthority(users.getRole().toString());
        Collection<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(role);

        return new UserDetailsImpl(users.getId(), authorities, users.getEmail(), users.getPassword(), users.getEmail(),
                users.getIp(), users.getPhoneNumber(), users.getSections().getSectionName(), users.getSubSections().getSubSectionName());
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(id, user.id);
    }
}
