package app.MyBackendApp.main.config;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import javax.crypto.SecretKey;
import org.springframework.expression.ParseException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTServiceImpl implements JWTService {

    private final String key = "jxs9mHkJQXGz8jv1rW2y4u7x!A%D*G-KaPdSgVkYp3s6v9y$B&E)H@McQfTjWnZq";
    private final SecretKey secretkey = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));

    @Override
    public String generateJwt(String username) throws ParseException {
        Date now = new Date();
        return Jwts.builder()
                .setIssuer("MyBackendApp")
                .setAudience("MyBackendAppUsers")
                .claim("username", username)
                .setSubject("JWT Authentication")
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + 60000)) // 1 minute
                .signWith(secretkey, SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public Authentication validateJwt(String jwt) {
        JwtParser jwtParser = Jwts.parser()
                .setSigningKey(secretkey)
                .build();
        Claims claims = jwtParser.parseClaimsJws(jwt).getBody();
        String username = (String) claims.getOrDefault("username",null);
        if(Objects.nonNull(username)){
            return new UsernamePasswordAuthenticationToken(username,null, new ArrayList<>());
        }
        return null;
    }
}