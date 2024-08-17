package az.edu.turing.booking_management.exception;

public class NoSuchReservationException extends RuntimeException{
    public NoSuchReservationException(String message){
        super(message);
    }
}
