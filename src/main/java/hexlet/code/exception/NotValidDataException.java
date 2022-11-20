package hexlet.code.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
public class NotValidDataException extends Exception {
    public NotValidDataException(String message) {
        super(message);
    }
}
