package app.MyBackendApp.main.config;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import app.MyBackendApp.main.config.*;

import jakarta.servlet.Filter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)

    public class SecurityConfigs {
     
        @Autowired
        private final JwtValidationFilter jwtValidationFilter;
        @Autowired
        private final AuthExceptionHandler authExceptionHandler;

        public SecurityConfigs( JwtValidationFilter jwtValidationFilter, AuthExceptionHandler authExceptionHandler) {
            //this.authenticationProvider = authenticationProvider;
            this.jwtValidationFilter = jwtValidationFilter;
            this.authExceptionHandler = authExceptionHandler;
        }

       @Bean
        PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
        }
        /* 
        @Bean
        UserDetailsService userDetailsService() {
            UserDetails user = User.builder()
                .username("admin@hotmail.com")
                .password(passwordEncoder().encode("Pass*85-"))
                .roles("ROLE_USER")
                .build();
            return new InMemoryUserDetailsManager(user);
        }
        */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(Arrays.asList("http://localhost:8080"));
    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
        }
    @Bean
    public SecurityFilterChain defaultFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
            .cors(cors->cors.configurationSource(corsConfigurationSource()))
            .csrf(csrf->csrf.disable())
            .exceptionHandling(handle -> handle.authenticationEntryPoint(authExceptionHandler))
            .addFilterBefore((Filter)jwtValidationFilter,UsernamePasswordAuthenticationFilter.class)
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/error**","/login**","/swagger-ui/index.html").permitAll()
                    .anyRequest().authenticated())
                    .build();
    }
}