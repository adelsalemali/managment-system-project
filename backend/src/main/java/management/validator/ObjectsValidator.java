package management.validator;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ObjectsValidator {

    @ExceptionHandler
    public ResponseEntity<HashMap<String, List<String>>> handleExeption(BindException bindException) {

        List<String> errors = bindException.getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        HashMap<String,List<String>> errorMap = new HashMap();
        errorMap.put("errors", errors);

        return new ResponseEntity<>(errorMap , HttpStatus.BAD_REQUEST  );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<HashMap<String, String>> handleGeneralException(Exception exception) {
        HashMap<String, String> errorMap = new HashMap<>();
        errorMap.put("error", "Internal Server Error");
        errorMap.put("message", exception.getMessage());

        return new ResponseEntity<>(errorMap, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<HashMap<String, String>> handleNullPointerException(NullPointerException exception) {
        HashMap<String, String> errorMap = new HashMap<>();
        errorMap.put("error", "Null Pointer Exception");
        errorMap.put("message", "A required object is missing or null.");

        return new ResponseEntity<>(errorMap, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<HashMap<String, String>> handleNotFound(NoHandlerFoundException exception) {
        HashMap<String, String> errorMap = new HashMap<>();
        errorMap.put("error", "Not Found");
        errorMap.put("message", "The requested resource was not found.");

        return new ResponseEntity<>(errorMap, HttpStatus.NOT_FOUND);
    }
}