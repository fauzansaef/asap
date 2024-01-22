package project.asap.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import project.asap.security.application.CustomAuthentication;
import project.asap.security.application.UserDetailsServiceImpl;
import project.asap.security.jwt.JwtAccessDenied;
import project.asap.security.jwt.JwtAuthEntryPoint;
import project.asap.security.jwt.JwtTokenFilter;
import project.asap.security.jwt.JwtUtils;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final JwtAuthEntryPoint jwtAuthEntryPoint;
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtUtils jwtUtils;
    private final CustomAuthentication customAuthentication;
    @Autowired
    public SecurityConfig(JwtAuthEntryPoint jwtAuthEntryPoint, UserDetailsServiceImpl userDetailsService, JwtUtils jwtUtils, CustomAuthentication customAuthentication) {

        this.jwtAuthEntryPoint = jwtAuthEntryPoint;
        this.userDetailsService = userDetailsService;
        this.jwtUtils = jwtUtils;
        this.customAuthentication = customAuthentication;
    }
    @Bean
    public JwtTokenFilter authenticationJwtTokenFilter() {
        return new JwtTokenFilter(jwtUtils, userDetailsService);
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new JwtAccessDenied();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        authenticationManagerBuilder.authenticationProvider(customAuthentication);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthEntryPoint)
                .accessDeniedHandler(accessDeniedHandler())
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers(
                        "/api/v1/auth/login",
                        "/api/v1/files/**",
                        "/swagger-ui-custom.html",
                        "/swagger-ui.html",
                        "/swagger-ui/**",
                        "/v3/api-docs/**",
                        "/webjars/**",
                        "/swagger-ui/index.html",
                        "/api-docs/**",
                        "/assets/**")
                .permitAll()
                .anyRequest().authenticated();

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    // digunakan jika CORS is enabled.
//    @Bean
//    public CorsFilter corsFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = new CorsConfiguration();
//        config.setAllowCredentials(true); // you USUALLY want this
//        config.addAllowedOrigin("*");
//        config.addAllowedHeader("*");
//        config.addAllowedMethod("OPTIONS");
//        config.addAllowedMethod("HEAD");
//        config.addAllowedMethod("GET");
//        config.addAllowedMethod("PUT");
//        config.addAllowedMethod("POST");
//        config.addAllowedMethod("DELETE");
//        config.addAllowedMethod("PATCH");
//        source.registerCorsConfiguration("/**", config);
//        return new CorsFilter(source);
//    }
}
