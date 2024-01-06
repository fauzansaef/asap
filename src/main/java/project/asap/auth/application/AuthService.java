package project.asap.auth.application;


import project.asap.auth.domain.AuthRequest;
import project.asap.auth.domain.JwtResponse;
import project.asap.security.domain.UserDetailsImpl;

public interface AuthService {

    JwtResponse authenticateUser(AuthRequest authRequest) ;

    UserDetailsImpl whoAmI();
}
