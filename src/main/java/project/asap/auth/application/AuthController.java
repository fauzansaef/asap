package project.asap.auth.application;

import ch.qos.logback.classic.Logger;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.asap.auth.domain.AuthRequest;
import project.asap.auth.domain.JwtResponse;
import project.asap.security.domain.UserDetailsImpl;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Authentication", description = "API Authenticate user")
public class AuthController {
    private static final Logger logger = (Logger) LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/auth/login")
    public ResponseEntity<JwtResponse> authenticateUser(@RequestBody @Valid AuthRequest authRequest)  {
        logger.info("login sebagai : " + authRequest.getUsername());
        return new ResponseEntity<>(authService.authenticateUser(authRequest), HttpStatus.OK);
    }

    @PostMapping("/auth/whoami")
    public ResponseEntity<UserDetailsImpl> whoAmAi() {
        logger.info("get whoami username : " + authService.whoAmI().getUsername());
        return new ResponseEntity<>(authService.whoAmI(), HttpStatus.OK);
    }
}
