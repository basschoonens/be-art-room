package nl.novi.theartroom.config;

import nl.novi.theartroom.filter.JwtRequestFilter;
import nl.novi.theartroom.service.userservice.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    public final CustomUserDetailsService customUserDetailsService;

    private final JwtRequestFilter jwtRequestFilter;

    public SpringSecurityConfig(CustomUserDetailsService customUserDetailsService, JwtRequestFilter jwtRequestFilter) {
        this.customUserDetailsService = customUserDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    // Authenticatie met customUserDetailsService en passwordEncoder
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder passwordEncoder) throws Exception {
        var auth = new DaoAuthenticationProvider();
        auth.setPasswordEncoder(passwordEncoder);
        auth.setUserDetailsService(customUserDetailsService);
        return new ProviderManager(auth);
    }

    @Bean
    protected SecurityFilterChain filter(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .httpBasic(basic -> basic.disable())
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth ->
                                auth
//                                .requestMatchers("/**").permitAll()
                                        .requestMatchers(HttpMethod.GET, "/artworks", "/artworks/{artworkId}", "/artworks/{artworkId}/image").permitAll()
                                        .requestMatchers(HttpMethod.POST, "/artworks/artist/{artworkId}/image").hasAnyRole("ARTIST", "ADMIN")
                                        .requestMatchers("/artworks/artist/**").hasAnyRole("ARTIST", "ADMIN")
                                        .requestMatchers("/artworks/admin/**").hasRole("ADMIN")
                                        .requestMatchers("/ratings/artwork/{artworkId}").permitAll()
                                        .requestMatchers("/ratings/user/**").hasAnyRole("USER", "ARTIST", "ADMIN")
                                        .requestMatchers("/ratings/artist/**").hasAnyRole("ARTIST", "ADMIN")
                                        .requestMatchers("/ratings/admin/**").hasRole("ADMIN")
                                        .requestMatchers(HttpMethod.POST, "/orders/user").hasAnyRole("USER", "ARTIST", "ADMIN")
                                        .requestMatchers(HttpMethod.GET, "/orders/user").hasAnyRole("USER", "ARTIST", "ADMIN")
                                        .requestMatchers("/orders/user/**").hasAnyRole("USER", "ARTIST", "ADMIN")
                                        .requestMatchers(HttpMethod.GET, "/orders/admin").hasRole("ADMIN")
                                        .requestMatchers("/orders/admin/**").hasRole("ADMIN")
                                        .requestMatchers(HttpMethod.GET, "/users/*").permitAll()
                                        .requestMatchers(HttpMethod.POST, "/users/*").permitAll()
                                        .requestMatchers(HttpMethod.PUT, "/users/*").authenticated()
                                        .requestMatchers(HttpMethod.DELETE, "/users/*").hasRole("ADMIN")
                                        .requestMatchers(HttpMethod.GET, "/users/{username}", "/users/{username}/authorities").hasRole("ADMIN")
                                        .requestMatchers(HttpMethod.POST, "/users/{username}/authorities").hasRole("ADMIN")
                                        .requestMatchers(HttpMethod.DELETE, "/users/{username}/authorities").hasRole("ADMIN")
                                        .requestMatchers("/authenticated").authenticated()
                                        .requestMatchers("/authenticate").permitAll()
                                        .anyRequest().denyAll()
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}