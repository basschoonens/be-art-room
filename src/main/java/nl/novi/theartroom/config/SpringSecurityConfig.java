package nl.novi.theartroom.config;

import nl.novi.theartroom.filter.JwtRequestFilter;
import nl.novi.theartroom.services.CustomUserDetailsService;
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

/*  Deze security is niet de enige manier om het te doen.
    In de andere branch van deze github repo staat een ander voorbeeld
 */
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

    // TODO RequestMatchers goed instellen

    @Bean
    protected SecurityFilterChain filter (HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .httpBasic(basic -> basic.disable())
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth ->
                        auth
//                .requestMatchers("/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/artworks/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/users/*").permitAll()
                                // Ratings toegankelijkheid goed instellen.
                .requestMatchers(HttpMethod.GET, "/ratings").hasAnyRole("ARTIST", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/ratings/{artworkId}/ratings").hasAnyRole("ARTIST", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/ratings/{artworkId}/ratings").hasAnyRole("USER", "ADMIN")
                .requestMatchers("/ratings/**").hasAnyRole("USER", "ARTIST", "ADMIN")
                .requestMatchers("/order").hasAnyRole("USER", "ARTIST", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/artworks/**").hasAnyRole("ARTIST", "ADMIN")
                .requestMatchers(HttpMethod.PUT, "/artworks/**").hasAnyRole("ARTIST", "ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/artworks/**").hasAnyRole("ARTIST", "ADMIN")
                .requestMatchers("/authenticated").authenticated()
                .requestMatchers("/authenticate").permitAll()
                .anyRequest().denyAll()
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}