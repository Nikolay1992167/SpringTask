package by.aston.exception.handler;

import by.aston.exception.InvalidDataException;
import by.aston.exception.InvalidLoginDataException;
import by.aston.exception.NotFoundException;
import by.aston.exception.ValidException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ControllerAdvice {

    @ExceptionHandler
    public ResponseEntity<IncorrectData> handleNotFoundException(NotFoundException exception) {

        IncorrectData incorrectData = new IncorrectData();
        incorrectData.setErrorMessage(exception.getMessage());
        incorrectData.setErrorCode(HttpStatus.NOT_FOUND.toString());

        log.error(incorrectData.toString());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(incorrectData);
    }

    @ExceptionHandler
    public ResponseEntity<IncorrectData> handleThrowable(Throwable exception) {

        IncorrectData incorrectData = new IncorrectData();
        incorrectData.setErrorMessage(exception.getMessage());
        incorrectData.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());

        log.error(incorrectData.toString());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(incorrectData);
    }

    @ExceptionHandler
    public ResponseEntity<IncorrectData> handleValidException(ValidException exception){

        IncorrectData incorrectData = new IncorrectData();
        incorrectData.setErrorMessage(exception.getMessage());
        incorrectData.setErrorCode(HttpStatus.CONFLICT.toString());

        log.error(incorrectData.toString());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(incorrectData);
    }

    @ExceptionHandler
    public ResponseEntity<IncorrectData> handleInvalidDataException(InvalidDataException exception){

        IncorrectData incorrectData = new IncorrectData();
        incorrectData.setErrorMessage(exception.getMessage());
        incorrectData.setErrorCode(HttpStatus.BAD_REQUEST.toString());

        log.error(incorrectData.toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(incorrectData);
    }

    @ExceptionHandler
    public ResponseEntity<IncorrectData> handleInvalidLoginDataException(InvalidLoginDataException exception){

        IncorrectData incorrectData = new IncorrectData();
        incorrectData.setErrorMessage(exception.getMessage());
        incorrectData.setErrorCode(HttpStatus.UNAUTHORIZED.toString());

        log.error(incorrectData.toString());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(incorrectData);
    }
}