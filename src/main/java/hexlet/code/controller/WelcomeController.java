package hexlet.code.controller;

import com.rollbar.notifier.Rollbar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class WelcomeController {
//    @Autowired
//    private Rollbar rollbar;
    @GetMapping(path = "/welcome")
    public String welcome() {
//        // Sends a debug message to your Spring project on Rollbar
//        rollbar.debug("Here is some debug message");
        return "Welcome to Spring";
    }
}
