package hexlet.code.app.service;

import hexlet.code.app.dto.UserDto;
import hexlet.code.app.exception.NotValidDataException;
import hexlet.code.app.model.User;
import hexlet.code.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    public User createNewUser(User u) {
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
    public User updateUser(long id, User userDto) throws NotValidDataException {
        final User user = userRepository.findById(id).orElseThrow(
                () -> new NotValidDataException("!!!!---UserService updateUser---!!!!"));
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
//        String password = encoder.encode(userDto.getPassword());
//        user.setPassword(password);
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
        return userRepository.findByEmail(email);
    }

    @Override
    public UserDto convertUserToUserDto(User user) {
            UserDto userDto = new UserDto();
            userDto.setId(user.getId());
            userDto.setFirstName(user.getFirstName());
            userDto.setLastName(user.getLastName());
            userDto.setEmail(user.getEmail());
            userDto.setCreatedAt(user.getCreatedAt());
            return userDto;
    }

    @Override
    public List<UserDto> convertListOfUsersToListOfUsersDto(List<User> listOfUsers) {
        List<UserDto> userDtoList = new ArrayList<>();
        for (User user: listOfUsers) {
            userDtoList.add(convertUserToUserDto(user));
        }
        return userDtoList;
    }
}
