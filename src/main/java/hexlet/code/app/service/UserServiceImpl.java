package hexlet.code.app.service;

import hexlet.code.app.dto.UserDto;
import hexlet.code.app.exception.NotValidDataException;
import hexlet.code.app.model.User;
import hexlet.code.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

import static java.lang.String.format;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder encoder;

    @Override
    public User findUserById(long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("There isn't User with id = " + id));
    }

    @Override
    public User createNewUser(UserDto u) {
        final User user = new User();
        user.setFirstName(u.getFirstName());
        user.setLastName(u.getLastName());
        user.setEmail(u.getEmail());
        String password = encoder.encode(u.getPassword());
        user.setPassword(password);
        userRepository.save(user);
        return user;
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(long id, UserDto userDto) throws NotValidDataException {
        final User user = userRepository.findById(id).orElseThrow(
                () -> new NotValidDataException(""));
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        String password = encoder.encode(userDto.getPassword());
        user.setPassword(password);
        user.setCreatedAt();
        userRepository.save(user);
        return user;
    }
    @Override
    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(format("User with email = %s, not found", email)));
    }

    @Override
    public User getCurrentUser() {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(currentUserEmail).orElse(null);
    }
    @Override
    public User findUserByUserId(long id) {
        return userRepository.findById(id).orElseThrow();
    }
}
