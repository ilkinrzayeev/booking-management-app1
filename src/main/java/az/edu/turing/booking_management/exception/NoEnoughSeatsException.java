package az.edu.turing.booking_management.exception;

public class NoEnoughSeatsException extends RuntimeException {
    public NoEnoughSeatsException(String message){
        super(message);
    }
}
