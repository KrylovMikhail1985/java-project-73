package hexlet.code.app.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Map;
@Service
public class SecurityServiceImpl implements SecurityService {
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private PasswordEncoder encoder;
    @Override
    public String getJWT(Map<String, String> map) {
        String email = map.get("email");
        String password = map.get("password");
        String correctPassword;
        correctPassword = userService.findByEmail(email).getPassword();

        if (encoder.matches(password, correctPassword)) {
            Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
            return Jwts.builder().setSubject(email).signWith(key).compact();
        }
        throw new RuntimeException("Password is not correct!");
    }
}
