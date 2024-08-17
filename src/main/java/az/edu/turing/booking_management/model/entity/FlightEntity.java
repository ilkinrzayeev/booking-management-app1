package az.edu.turing.booking_management.model.entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class FlightEntity {
    private int flight_id;
    private int freeSpaces;
    private String location;
    private String destination;
    private LocalDateTime departure_time;
    private static long nextId = 1;

    public FlightEntity() {

    }
    public FlightEntity(int flight_id, int freeSpaces, String location,String destination, LocalDateTime departure_time) {
        this.flight_id = flight_id;
        this.freeSpaces = freeSpaces;
        this.location = location;
        this.destination = destination;
        this.departure_time = departure_time;
        nextId++;
    }

    public FlightEntity(LocalDateTime departureTime, String location, String destination, int freeSpaces) {
        this.departure_time = departureTime;
        this.location = location;
        this.destination = destination;
        this.freeSpaces = freeSpaces;
    }

    public FlightEntity(int id, int freeSeats, LocalDateTime departureTime, String location, String destination) {
        this.flight_id = id;
        this.freeSpaces = freeSeats;
        this.departure_time = departureTime;
        this.destination = destination;
        this.location = location;
    }


    public int getFlightId() {
        return flight_id;
    }

    public void setFlightId(int flight_id) {
        this.flight_id = flight_id;
    }

    public int getSeats() {
        return freeSpaces;
    }

    public void setSeats(int freeSpaces) {
        this.freeSpaces = freeSpaces;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public LocalDateTime getDepartureTime() {
        return departure_time;
    }

    public void setDepartureTime(LocalDateTime departure_time) {
        this.departure_time = departure_time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlightEntity that = (FlightEntity) o;
        return flight_id == that.flight_id && freeSpaces == that.freeSpaces && Objects.equals(location, that.location) &&
                Objects.equals(destination, that.destination) && Objects.equals(departure_time, that.departure_time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flight_id, freeSpaces, location, destination, departure_time);
    }

    @Override
    public String toString() {
        return "FlightEntity{" +
                "flight_id=" + flight_id +
                ", freeSpaces=" + freeSpaces +
                ", location='" + location + '\'' +
                ", destination='" + destination + '\'' +
                ", departure_time=" + departure_time +
                '}';
    }
}
