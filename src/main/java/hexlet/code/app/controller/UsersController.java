package hexlet.code.app.controller;

import hexlet.code.app.dto.UserDto;
import hexlet.code.app.exception.NotValidDataException;
import hexlet.code.app.model.User;
import hexlet.code.app.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;



import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("api/users")
public class UsersController {
    @Autowired
    private UserServiceImpl userService;
    @GetMapping("")
    public List<UserDto> getAllUsers() {
        List<User> listOfUsers = userService.findAllUsers();
        return userService.convertListOfUsersToListOfUsersDto(listOfUsers);
    }
    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable(name = "id") long id) {
        User user = userService.findUserById(id);
        return userService.convertUserToUserDto(user);
    }

    @PostMapping("")
    public UserDto createUser(@Valid @RequestBody User user, BindingResult bindingResult) throws NotValidDataException {
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult);
            throw new NotValidDataException("User is not valid");
        }
        User u = userService.createNewUser(user);
        return userService.convertUserToUserDto(u);
    }
    @PutMapping("/{id}")
    public UserDto updateUser(@Valid @RequestBody User user, BindingResult bindingResult,
                              @PathVariable(name = "id") long id) throws NotValidDataException {
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult);
            throw new NotValidDataException("User is not valid");
        }
        User u = userService.updateUser(id, user);
        return userService.convertUserToUserDto(u);
    }
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable(name = "id") long id) {
        userService.deleteUser(id);
    }
//    @PatchMapping("/{email}")
//    public User getUserByName(@PathVariable(name = "email") String email) {
//        return userService.findByEmail(email);
//    }
}
