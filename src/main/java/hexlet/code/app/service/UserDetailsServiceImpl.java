package hexlet.code.app.service;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
//    @Autowired
//    private UserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("anyUser"));
        String password = "password";
        UserDetails userDetails = User.withUsername(username)
                .password(password)
                .authorities(authorities)
                .build();
        return userDetails;
    }
}
