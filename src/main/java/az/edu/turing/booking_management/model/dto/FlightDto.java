package az.edu.turing.booking_management.model.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public class FlightDto {
    private int flight_id;
    private int freeSpaces;
    private String location;
    private String destination;
    private LocalDateTime departure_time;

    public FlightDto(int flight_id, int freeSpaces, String location, String destination, LocalDateTime departure_time) {
        this.flight_id = flight_id;
        this.freeSpaces = freeSpaces;
        this.location = location;
        this.destination = destination;
        this.departure_time = departure_time;
    }


    public FlightDto(LocalDateTime departure_time, String location, String destination, int freeSpaces) {
        this.departure_time = departure_time;
        this.location = location;
        this.destination = destination;
        this.freeSpaces = freeSpaces;
    }

    public FlightDto() {
    }


    public int getFlight_id() {
        return flight_id;
    }

    public void setFlight_id(int flight_id) {
        this.flight_id = flight_id;
    }

    public int getFreeSpaces() {
        return freeSpaces;
    }

    public void setFreeSpaces(int freeSpaces) {
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

    public LocalDateTime getDeparture_time() {
        return departure_time;
    }

    public void setDeparture_time(LocalDateTime departure_time) {
        this.departure_time = departure_time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlightDto flightDto = (FlightDto) o;
        return flight_id == flightDto.flight_id && freeSpaces == flightDto.freeSpaces && Objects.equals(location, flightDto.location)
                && Objects.equals(destination, flightDto.destination) && Objects.equals(departure_time, flightDto.departure_time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flight_id, freeSpaces, location, destination, departure_time);
    }

    @Override
    public String toString() {
        return "FlightDto{" +
                "flight_id=" + flight_id +
                ", freeSpaces=" + freeSpaces +
                ", location='" + location + '\'' +
                ", destination='" + destination + '\'' +
                ", departure_time=" + departure_time +
                '}';
    }
}
