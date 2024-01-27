package by.aston.exception;

public class InvalidLoginDataException extends RuntimeException{

    public InvalidLoginDataException() {
        super("Данные введены не верно или пользователь не существует!");
    }
}
