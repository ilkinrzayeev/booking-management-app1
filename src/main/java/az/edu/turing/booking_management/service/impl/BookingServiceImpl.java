package az.edu.turing.booking_management.service.impl;

import az.edu.turing.booking_management.dao.BookingDao;
import az.edu.turing.booking_management.dao.FlightDao;
import az.edu.turing.booking_management.dao.impl.BookingPostgresDao;
import az.edu.turing.booking_management.exception.NoEnoughSeatsException;
import az.edu.turing.booking_management.exception.NoSuchReservationException;
import az.edu.turing.booking_management.exception.NotAValidFlightException;
import az.edu.turing.booking_management.model.entity.BookingEntity;
import az.edu.turing.booking_management.model.entity.FlightEntity;
import az.edu.turing.booking_management.service.BookingService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BookingServiceImpl implements BookingService {
    public BookingDao bookingDao;
    public FlightDao flightDao;

    public BookingServiceImpl(BookingDao bookingDao, FlightDao flightDao) {
        this.bookingDao = bookingDao;
        this.flightDao = flightDao;
    }


    public BookingServiceImpl() {
    }

    @Override
    public boolean bookAReservation(String[] passengers, long flightId, BookingDao bookingDao, FlightDao flightDao) throws NotAValidFlightException, NoEnoughSeatsException {
        List<BookingEntity> list = new ArrayList<>();
        List<FlightEntity> flightsList = flightDao.getAll();
        int amount = passengers.length;
        Optional<FlightEntity> optionalFlight = flightsList.stream()
                .filter(flightEntity -> flightEntity.getFlightId() == flightId)
                .findFirst();
        if (optionalFlight.isEmpty()) {
            throw new NotAValidFlightException("It is not a valid flight!");
        }
        FlightEntity flight = optionalFlight.get();
        int seats = flight.getSeats();
        if (amount > seats) {
            throw new NoEnoughSeatsException("No enough available seats!");
        }

        seats -= amount;
        flight.setSeats(seats);
        flightDao.update(flightId, -amount);

        long bookingId;
        try {
            bookingId = ((BookingPostgresDao) bookingDao).getNextBookingId();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        BookingEntity bookingEntity = new BookingEntity(bookingId, passengers, flightId, amount);
        list.add(bookingEntity);
        return bookingDao.save(list);
    }

    @Override
    public boolean cancelAReservation(long bookingId, BookingDao bookingDao, FlightDao flightDao) throws NoSuchReservationException {
        try {
            BookingEntity reservationEntity = bookingDao.getOneBy(bookingEntity -> bookingEntity.getBookingId() == bookingId).get();
            int amount = reservationEntity.getPassengers().length;
            long flightId = reservationEntity.getFlightId();
            bookingDao.delete(bookingId);
            flightDao.update(flightId, amount);
            return true;
        } catch (NoSuchReservationException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<BookingEntity> getMyReservations(String userName, BookingDao bookingDao) {
        List<BookingEntity> allReservations = bookingDao.getAll();
        System.out.println(userName);
        return allReservations.stream()
                .filter(booking -> Arrays.asList(booking.getPassengers()).contains(userName))
                .collect(Collectors.toList());
    }
}

