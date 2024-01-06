package project.asap.security.jwt;

import ch.qos.logback.classic.Logger;
import io.jsonwebtoken.*;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import project.asap.security.domain.UserDetailsImpl;
import project.asap.security.domain.UserDetailsResponse;
import java.util.Date;
@Component
public class JwtUtils {
    private static final Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(JwtUtils.class);

    @Value("${relawan.app.jwtSecret}")
    private String jwtSecret;
    @Value("${relawan.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    public String generateJwtToken(Authentication authentication) {
        UserDetailsImpl userPrincipal;

        if (authentication.getDetails() != null) {
            userPrincipal = (UserDetailsImpl) authentication.getDetails();
        } else {
            userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        }

        logger.info("[generate jwt token]");
        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .claim("userDetails", new UserDetailsResponse(
                        userPrincipal.getId(),
                        userPrincipal.getUsername(),
                        userPrincipal.getEmail(),
                        userPrincipal.getAuthorities()))
                .compact();
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) throws MalformedJwtException, SignatureException, ExpiredJwtException, UnsupportedJwtException, IllegalArgumentException {
        Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
        logger.info("[validate jwt token]");
        return true;
    }

}
