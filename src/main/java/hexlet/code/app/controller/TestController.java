package hexlet.code.app.controller;

import hexlet.code.app.model.User;
import hexlet.code.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TestController {
    @Autowired
    private UserRepository userRepository;
    @PostMapping("/users")
    public void createUser(@RequestBody User user) {
        userRepository.save(user);
    }
    @GetMapping("/users")
    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }
    @GetMapping("/")
    public String showMyENV() {
        String result = "";
        result = result + "DEV = " + System.getenv("DEV") + "\n";
        result = result + "PGHOST = " + System.getenv("PGHOST") + "\n";
        result = result + "PGPORT = " + System.getenv("PGPORT") + "\n";
        result = result + "PGUSER = " + System.getenv("PGUSER") + "\n";
        result = result + "PGPASSWORD = " + System.getenv("PGPASSWORD") + "\n";
        result = result + "PGDATABASE = " + System.getenv("PGDATABASE") + "\n";
        result = result + "DATABASE_URL = " + System.getenv("DATABASE_URL") + "\n";
        System.out.println(result);
        return result;
    }
}
