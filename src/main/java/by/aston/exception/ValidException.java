package by.aston.exception;

public class ValidException extends RuntimeException{


    public ValidException(String message) {
        super(message);
    }

    public static ValidException of(Class<?> clazz) {
        return new ValidException("Данные для " + clazz.getSimpleName() + " введены неверно!");
    }

    public static ValidException of() {
        return new ValidException("Данные введены неверно!");
    }
}
