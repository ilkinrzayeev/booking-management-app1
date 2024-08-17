package az.edu.turing.booking_management.controller.bookingServletController;

import az.edu.turing.booking_management.dao.BookingDao;
import az.edu.turing.booking_management.dao.FlightDao;
import az.edu.turing.booking_management.dao.impl.BookingPostgresDao;
import az.edu.turing.booking_management.dao.impl.FlightPostgresDao;
import az.edu.turing.booking_management.exception.NoEnoughSeatsException;
import az.edu.turing.booking_management.exception.NotAValidFlightException;
import az.edu.turing.booking_management.model.entity.BookingEntity;
import az.edu.turing.booking_management.service.BookingService;
import az.edu.turing.booking_management.service.impl.BookingServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class BookingCreateServlet extends HttpServlet {
    private ObjectMapper objectMapper;
    private BookingService bookingService;

    public BookingCreateServlet() {
    }

    public BookingCreateServlet(ObjectMapper objectMapper, BookingService bookingService) {
        this.objectMapper = objectMapper;
        this.bookingService = bookingService;
    }

    @Override
    public void init() throws ServletException {
        super.init();
        if (this.objectMapper == null) {
            this.objectMapper = new ObjectMapper();
        }
        if (this.bookingService == null) {
            this.bookingService = new BookingServiceImpl();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            BookingDao bookingDao = new BookingPostgresDao();
            FlightDao flightDao = new FlightPostgresDao();
            response.setContentType("application/json");
            BookingEntity bookingEntity = objectMapper.readValue(request.getReader(), BookingEntity.class);

            boolean bookingSuccess = bookingService.bookAReservation(bookingEntity.getPassengers(), bookingEntity.getFlightId(), bookingDao, flightDao);

            if (bookingSuccess) {
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("{\"message\": \"Booking Successful\"}");
            } else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Booking Failed");
            }
        } catch (NoEnoughSeatsException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Not enough seats available: " + e.getMessage());
        } catch (NotAValidFlightException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "It is not a valid flight: " + e.getMessage());
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Server error: " + e.getMessage());
        }
    }
}
