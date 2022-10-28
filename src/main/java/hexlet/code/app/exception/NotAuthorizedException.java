package hexlet.code.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
public class NotAuthorizedException extends Exception {
    public NotAuthorizedException() {
        super();
    }
    public NotAuthorizedException(String message) {
        super(message);
    }
}
