package hexlet.code.security.jwt;

import hexlet.code.model.User;
import hexlet.code.repository.UserRepository;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value("${jwt-token-secret}")
    private String secret;
    @Value("${jwt-token-expired}")
    private long livingTime;
    @Autowired
    private UserRepository userRepository;

    public String createToken(String email) {
        Claims claims = Jwts.claims().setSubject(email);
//        claims.put("roles", some_role)
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + livingTime);

        SecretKey signingKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(signingKey)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        String email = getUserEmail(token);
        User user = userRepository.findByEmail(email).orElse(null);

        assert user != null;
        UserDetails userDetails = JwtUserFactory.create(user);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUserEmail(String token) {
        SecretKey signingKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        return Jwts.parserBuilder().setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean tokenIsValid(String token) throws JwtAuthenticationException {
        try {
            SecretKey signingKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
            Claims claimsJwt =
                    Jwts.parserBuilder().setSigningKey(signingKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            if (claimsJwt.getExpiration().before(new Date())) {
                System.out.println("Token NOT valid anymore!");
                return false;
            }
            System.out.println("Token is valid.");
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            throw new JwtAuthenticationException("JwtTokenProvider tokenIsValid error");
        }
    }
}
