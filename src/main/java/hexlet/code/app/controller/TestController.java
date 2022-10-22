package hexlet.code.app.controller;

import hexlet.code.app.model.User;
import hexlet.code.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TestController {
    @Value("myUrl")
    String str;
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
        String HOST = System.getenv("PGHOST");
        String PORT = System.getenv("PGPORT");
        String DATABASE = System.getenv("PGDATABASE");
        System.out.println(str);
        System.out.println("jdbc:postgresql://" + HOST + ":"  + PORT + "/" + DATABASE);
        System.out.println("jdbc:postgresql://containers-us-west-29.railway.app:7337/railway");
        return str;
    }
}
