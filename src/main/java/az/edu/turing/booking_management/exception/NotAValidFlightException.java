package az.edu.turing.booking_management.exception;

public class NotAValidFlightException extends RuntimeException{
    public NotAValidFlightException(String message){
        super(message);
    }
}
