package az.edu.turing.booking_management.model.entity;

public class BookingEntity {
    private String[] passangers;
    private long flightId;
    private int amount;
    private long bookingId;

    public BookingEntity() {
    }


    public BookingEntity(long bookingId, String[] passangers, long flightId) {
        this.passangers = passangers;
        this.flightId = flightId;
        this.bookingId = bookingId;
    }

    public BookingEntity(long bookingId, long flightId) {
        this.flightId = flightId;
        this.bookingId = bookingId;
    }


    public BookingEntity(long id, String[] passangers, long flightId, int amount) {
        this.passangers = passangers;
        this.flightId = flightId;
        this.amount = amount;
        this.bookingId = id;
    }

    public BookingEntity(String[] passangers, int amount, long flightId) {
        this.passangers = passangers;
        this.flightId = flightId;
        this.amount = amount;
    }


    public String[] getPassengers() {
        return passangers;
    }

    public long getFlightId() {
        return flightId;
    }

    public int getAmount() {
        return amount;
    }

    public long getBookingId() {
        return bookingId;
    }

    public void setPassengers(String[] passangers) {
        this.passangers = passangers;
    }

    @Override
    public String toString() {
        return "passangers=" + passangers.toString() +
                ", flightId=" + flightId +
                ", amount=" + amount +
                ", bookingId=" + bookingId +
                '}';
    }
}

