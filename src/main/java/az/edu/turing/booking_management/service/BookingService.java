package az.edu.turing.booking_management.service;

import az.edu.turing.booking_management.dao.BookingDao;
import az.edu.turing.booking_management.dao.FlightDao;
import az.edu.turing.booking_management.model.entity.BookingEntity;

import java.util.List;

public interface BookingService {
    boolean bookAReservation(String [] passengers, long flightId, BookingDao bookingDao, FlightDao flightDao);

    boolean cancelAReservation(long bookingId, BookingDao bookingDao, FlightDao flightDao);

    List<BookingEntity> getMyReservations(String userId, BookingDao bookingDao);


}

