package az.edu.turing.booking_management.model.dto;

import java.util.Objects;

public class BookingDto {
    private final String firstName;
    private final String secondName;
    private long flightId;
    private int amount;
    private long bookingId;

    public BookingDto(String firstName, String secondName, long flightId, int amount, long bookingId) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.amount = amount;
        this.bookingId = bookingId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public long getFlightId() {
        return flightId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookingDto that = (BookingDto) o;
        return flightId == that.flightId && Objects.equals(firstName, that.firstName) && Objects.equals(secondName, that.secondName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, secondName, flightId);
    }

    @Override
    public String toString() {
        return String.format("First name: '%s' || Second name: '%s' || ",
                firstName, secondName);
    }
}

