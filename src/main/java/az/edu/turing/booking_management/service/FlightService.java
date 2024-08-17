package az.edu.turing.booking_management.service;

import az.edu.turing.booking_management.model.dto.FlightDto;

import java.util.List;
import java.util.Optional;

public interface FlightService {

   List<FlightDto> getAllFlights();

   List<FlightDto> getAllFlightIn24Hours(String location);

   Optional<FlightDto> getFlightById(long flightId);

   boolean createFlight(FlightDto flightDto );}
