package hexlet.code.app.service;

import hexlet.code.app.exception.NotAuthorizedException;
import hexlet.code.app.model.User;
import hexlet.code.app.repository.UserRepository;
import hexlet.code.app.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Map;
@Service
public class SecurityServiceImpl implements SecurityService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private PasswordEncoder encoder;

    @Override
    public String getJWT(Map<String, String> map) throws NotAuthorizedException {
        String email = map.get("email");
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            throw new NotAuthorizedException();
        }

        String password = map.get("password");
        String correctPassword = user.getPassword();

        if (encoder.matches(password, correctPassword)) {
            return jwtTokenProvider.createToken(email);
        }
        System.out.println("Password is not correct!");
        throw new NotAuthorizedException();
    }
}
