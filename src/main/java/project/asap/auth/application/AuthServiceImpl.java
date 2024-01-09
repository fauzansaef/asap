package project.asap.auth.application;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.asap.auth.domain.AuthRequest;
import project.asap.auth.domain.JwtResponse;
import project.asap.security.domain.UserDetailsImpl;
import project.asap.security.jwt.JwtUtils;
import project.asap.users.domain.entity.Users;
import project.asap.users.infrastructure.UsersRepository;

import java.time.LocalDateTime;


@Service
@Transactional
public class AuthServiceImpl implements AuthService {
    private static final Logger logger = (Logger) LoggerFactory.getLogger(AuthServiceImpl.class);

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UsersRepository usersRepository;

    @Autowired
    public AuthServiceImpl(JwtUtils jwtUtils, AuthenticationManager authenticationManager, UsersRepository usersRepository) {
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
        this.usersRepository = usersRepository;

    }

    @Override
    public JwtResponse authenticateUser(AuthRequest authRequest) {
        logger.info("authenticate : " + authRequest.getUsername());

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails;

        if (authentication.getDetails() != null) {
            userDetails = (UserDetailsImpl) authentication.getDetails();
        } else {
            userDetails = (UserDetailsImpl) authentication.getPrincipal();
        }

        logger.info("login success : " + authRequest.getUsername());
        return new JwtResponse(userDetails.getUsername(), userDetails.getEmail(), userDetails.getAuthorities(), jwt, "Bearer");

    }



    @Override
    public UserDetailsImpl whoAmI() {
        return (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
