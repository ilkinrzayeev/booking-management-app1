package az.edu.turing.booking_management.service.impl;

import az.edu.turing.booking_management.dao.FlightDao;
import az.edu.turing.booking_management.dao.impl.FlightPostgresDao;
import az.edu.turing.booking_management.model.dto.FlightDto;
import az.edu.turing.booking_management.model.entity.FlightEntity;
import az.edu.turing.booking_management.service.FlightService;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FlightServiceImpl implements FlightService {
    private  FlightDao flightDao=new FlightPostgresDao();

    public FlightServiceImpl(FlightDao flightDao) {
        this.flightDao = flightDao;
    }
    public FlightServiceImpl() {

    }
    @Override
    public List<FlightDto> getAllFlights() {
        List<FlightEntity> allFlights = flightDao.getAll();
        return Optional.ofNullable(allFlights)
                .orElse(Collections.emptyList())
                .stream()
                .map(flight -> new FlightDto(
                        flight.getFlightId(),
                        flight.getSeats(),
                        flight.getLocation(),
                        flight.getDestination(),
                        flight.getDepartureTime()))
                .collect(Collectors.toList());
    }
    @Override
    public List<FlightDto> getAllFlightIn24Hours(String location) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime twentyFourHoursLater = now.plusHours(24);
        List<FlightEntity> flights = flightDao.getAll();

        return flights.stream()
                .filter(flight ->
                        flight.getLocation().equalsIgnoreCase(location) &&
                                flight.getDepartureTime().isAfter(now) &&
                                flight.getDepartureTime().isBefore(twentyFourHoursLater))
                .map(flight -> new FlightDto(
                        flight.getFlightId(),
                        flight.getSeats(),
                        flight.getLocation(),
                        flight.getDestination(),
                        flight.getDepartureTime()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<FlightDto> getFlightById(long flightId) {
        try {
            Predicate<FlightEntity> predicateById = flight -> flight.getFlightId() == flightId;
            Optional<FlightEntity> flightById = flightDao.getOneBy(predicateById);
            return flightById.map(flightEntity ->
                    new FlightDto(flightEntity.getFlightId(), flightEntity.getSeats(), flightEntity.getLocation(),
                            flightEntity.getDestination(), flightEntity.getDepartureTime()));
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
    @Override
    public boolean createFlight(FlightDto flightDto) {
        Predicate<FlightEntity> flightPredicate = flightEntity ->
                flightEntity.getDepartureTime().equals(flightDto.getDeparture_time()) &&
                        flightEntity.getLocation().equals(flightDto.getLocation()) &&
                        flightEntity.getDestination().equals(flightDto.getDestination());
        Optional<FlightEntity> existingFlight = flightDao.getOneBy(flightPredicate);
        if (existingFlight.isPresent()) {
            return false;
        }
        FlightEntity flightEntity = new FlightEntity(
                flightDto.getDeparture_time(),
                flightDto.getLocation(),
                flightDto.getDestination(),
                flightDto.getFreeSpaces()
        );
        return flightDao.save(List.of(flightEntity));
    }

}


