package at.qe.skeleton.configs;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.web.cors.CorsConfigurationSource;

/**
 * Spring configuration for web security.
 *
 * This class is part of the skeleton project provided for students of the
 * course "Software Architecture" offered by Innsbruck University.
 */

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Value("${app.environment}")
    private String appEnvironment;
    
    @Value("${cors.allowed-origins}")
    private String allowedOrigins;
    
    @Value("${cors.allowed-methods}")
    private String allowedMethods;
    
    @Value("${cors.allowed-headers}")
    private String allowedHeaders;
    
    @Value("${cors.allow-credentials}")
    private boolean allowCredentials;
    
    @Value("${cors.max-age}")
    private long maxAge;
    
    DataSource dataSource;
    
    @Autowired
    public WebSecurityConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        try {

            http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> {
                        if ("dev".equalsIgnoreCase(appEnvironment)) {
                            csrf.disable(); // disable CSRF in development mode (not needed on localhost)
                        }
                    }
                )
                .headers(headers -> headers.frameOptions(FrameOptionsConfig::sameOrigin)) // needed for H2 console
                .authorizeHttpRequests(authorize -> authorize
                    .requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()
                    .requestMatchers(new AntPathRequestMatcher("/error/**")).permitAll()
                    .requestMatchers(new AntPathRequestMatcher("/api/authentication")).permitAll()
                    .requestMatchers(new AntPathRequestMatcher("/api/admin/**")).hasAnyAuthority("ADMIN")
                    .requestMatchers(new AntPathRequestMatcher("/api/**")).authenticated()
                )
                    
                .formLogin(form -> form
                    .loginProcessingUrl("/api/authentication") // Custom login endpoint
                    .successHandler((request, response, authentication) -> {
                        response.setStatus(HttpStatus.OK.value()); // Send OK status on successful login
                    })
                    .failureHandler((request, response, exception) -> {
                        response.setStatus(HttpStatus.UNAUTHORIZED.value()); // Send Unauthorized status on failed login
                    })
                    .permitAll()
                )
                .logout(logout -> logout
                    .logoutUrl("/api/logout") // Custom logout endpoint
                    .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK))
                    .permitAll()
                )
                .sessionManagement(session -> session
                    .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // Use session-based authentication
                )
                    
                .exceptionHandling(exception -> exception
                    .accessDeniedHandler((request, response, accessDeniedEception) -> response.setStatus(HttpStatus.FORBIDDEN.value()))
                    .authenticationEntryPoint((request, response, authException) -> response.setStatus(HttpStatus.UNAUTHORIZED.value()))
                );

            return http.build();
        } catch (Exception ex) {
            throw new BeanCreationException("Wrong spring security configuration", ex);
        }
    }
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        //Configure roles and passwords via datasource
        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery("select username, password, enabled from userx where username=?")
                .authoritiesByUsernameQuery("select u.username, r.roles from userx u join userx_userx_role r on u.id = r.userx_id where u.username=?");
    }
    
    @Bean
    public static PasswordEncoder passwordEncoder() {
        // :TODO: use proper passwordEncoder and do not store passwords in plain text
        return NoOpPasswordEncoder.getInstance();
    }
    
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        List<String> originsList = List.of(allowedOrigins.split(","));
        List<String> methodsList = List.of(allowedMethods.split(","));
        List<String> headersList = List.of(allowedHeaders.split(","));
        
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOriginPatterns(originsList);
        config.setAllowedMethods(methodsList);
        config.setAllowedHeaders(headersList);
        config.setAllowCredentials(allowCredentials);
        config.setMaxAge(maxAge);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
   
}
