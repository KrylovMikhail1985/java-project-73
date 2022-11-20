package hexlet.code.service;

import hexlet.code.exception.NotAuthorizedException;
import hexlet.code.model.User;
import hexlet.code.repository.UserRepository;
import hexlet.code.security.jwt.JwtTokenProvider;
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
