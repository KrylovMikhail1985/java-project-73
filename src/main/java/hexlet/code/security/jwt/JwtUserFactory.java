package hexlet.code.security.jwt;

import hexlet.code.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

public class JwtUserFactory {
    public static JwtUser create(User user) {
        System.out.println("JwtUserFactory started");
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("anyUser"));
        return new JwtUser(
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getPassword(),
                authorities);
    }
}
