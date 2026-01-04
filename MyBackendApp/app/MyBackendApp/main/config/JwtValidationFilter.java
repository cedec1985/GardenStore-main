package app.MyBackendApp.main.config;

import java.io.IOException;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import app.MyBackendApp.main.config.JWTServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtValidationFilter {
    private static final Logger log = LoggerFactory.getLogger(JwtValidationFilter.class);
    private final JWTServiceImpl jwtService;

    public JwtValidationFilter(JWTServiceImpl jwtService) {
        this.jwtService = jwtService;
    }

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try{
            String jwt = getJWT(request);
            if(Objects.nonNull(jwt)){
                UsernamePasswordAuthenticationToken auth =(UsernamePasswordAuthenticationToken) jwtService.validateJwt(jwt);
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }catch (Exception e){
            log.error("JWT validation failed: " + e.getMessage());
        }
        filterChain.doFilter(request,response);
    }
    private String getJWT(HttpServletRequest request){
        String jwt = request.getHeader("authorization");
        if(Objects.nonNull(jwt) && jwt.startsWith("Bearer") && jwt.length() > 7){
            return jwt.substring(7);
        }
        return null;
    }
}