package hexlet.code.security;


import hexlet.code.model.User;
import hexlet.code.repository.UserRepository;
import hexlet.code.security.jwt.JwtUserFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElse(null);

        if (user == null) {
            System.out.println("User with email = " + email + " not found!");
            throw new UsernameNotFoundException("User with email = " + email + " not found!");
        }
        System.out.println("User with email = " + email + " found");
        return JwtUserFactory.create(user);
    }
}
