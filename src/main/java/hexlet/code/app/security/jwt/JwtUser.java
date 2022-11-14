package hexlet.code.app.security.jwt;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class JwtUser implements UserDetails {
    private final Long id;
    private final String loginEmail;
    private final String firstName;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;

    public JwtUser(
            Long id,
            String loginEmail,
            String firstName,
            String password,
            Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.loginEmail = loginEmail;
        this.firstName = firstName;
        this.password = password;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return loginEmail;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return firstName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

}
