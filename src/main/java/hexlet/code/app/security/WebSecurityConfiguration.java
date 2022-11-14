package hexlet.code.app.security;

import hexlet.code.app.security.jwt.JwtTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private JwtTokenFilter jwtTokenFilter;

    // указываем что для сравнения хешей будет использоваться BCrypt
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }

    // указываем что будет использоваться указанный нами диспатчер вместо дефолтного
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Enable CORS and disable CSRF
        http = http.cors().and().csrf().disable();
//
        // Set session management to stateless
        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and();

        // Set unauthorized requests exception handler
        http
                .exceptionHandling()
                .authenticationEntryPoint(
                        (request, response, ex) -> {
                            response.sendError(
                                    HttpServletResponse.SC_UNAUTHORIZED,
                                    ex.getMessage()
                            );
                        }
                )
                .and();

        // Set permissions on endpoints
        http.authorizeRequests()
                .antMatchers("/h2console").permitAll()
//                .antMatchers("/test").permitAll()
                .antMatchers(HttpMethod.POST, "/api/login").permitAll()
                .antMatchers(HttpMethod.DELETE, "/**").authenticated()


                .antMatchers(HttpMethod.GET, "/api/users").permitAll()
                .antMatchers(HttpMethod.POST, "/api/users").permitAll()
                .antMatchers("/api/users/{id}").authenticated()

                .antMatchers(HttpMethod.POST, "/api/statuses/**").authenticated()
                .antMatchers(HttpMethod.PUT, "/api/statuses/**").authenticated()

                .antMatchers(HttpMethod.POST, "/api/labels/**").authenticated()
                .antMatchers(HttpMethod.PUT, "/api/labels/**").authenticated()

                .antMatchers("/api/tasks/**").authenticated()
                .and();

                // Our private endpoints
//                .anyRequest().authenticated();

        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
//        http.headers().frameOptions().disable();
    }
}
