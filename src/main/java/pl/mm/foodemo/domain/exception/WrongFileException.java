package pl.mm.foodemo.domain.exception;

public class WrongFileException extends RuntimeException  {

    public WrongFileException(String message) {
        super(message);
    }
}
