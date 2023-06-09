package uz.pdp.cambridgelc.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import uz.pdp.cambridgelc.exceptions.*;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = {CourseNotFoundException.class})
    public ResponseEntity<String> authException(CourseNotFoundException e) {
        return ResponseEntity.status(404).body(e.getMessage());
    }

    @ExceptionHandler(value = {GroupNotFoundException.class})
    public ResponseEntity<String> groupNotFoundException(GroupNotFoundException e){
        return ResponseEntity.status(404).body(e.getMessage());
    }

    @ExceptionHandler(value = {DataNotFoundException.class})
    public ResponseEntity<String> dataNotFoundException(DataNotFoundException e){
        return ResponseEntity.status(404).body(e.getMessage());
    }

    @ExceptionHandler(value = {FailedAuthorizeException.class})
    public ResponseEntity<String> failedAuthorize(FailedAuthorizeException e){
        return ResponseEntity.status(401).body(e.getMessage());
    }
    @ExceptionHandler(value = {NotEnoughCreditsException.class})
    public ResponseEntity<String> notEnoughCredits(NotEnoughCreditsException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}