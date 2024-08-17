package az.edu.turing.booking_management.controller.bookingServletController;

import az.edu.turing.booking_management.dao.BookingDao;
import az.edu.turing.booking_management.dao.FlightDao;
import az.edu.turing.booking_management.dao.impl.BookingPostgresDao;
import az.edu.turing.booking_management.dao.impl.FlightPostgresDao;
import az.edu.turing.booking_management.exception.NoSuchReservationException;
import az.edu.turing.booking_management.service.BookingService;
import az.edu.turing.booking_management.service.impl.BookingServiceImpl;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class CancelByIdServlet extends HttpServlet {
    private final BookingService bookingService=new BookingServiceImpl();
    public CancelByIdServlet() {
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            response.setContentType("application/json");
            BookingDao bookingDao = new BookingPostgresDao();
            FlightDao flightDao = new FlightPostgresDao();

            String bookingIdParam = request.getParameter("bookingId");
            if (bookingIdParam == null || bookingIdParam.isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "{\"message\": \"bookingId parameter is required\"}");
                return;
            }
            long bookingId = Long.parseLong(bookingIdParam);
            boolean cancellingSuccess = bookingService.cancelAReservation(bookingId, bookingDao, flightDao);
            if (cancellingSuccess) {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                response.getWriter().write("{\"message\": \"Booking successfully canceled\"}");
            } else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } catch (NoSuchReservationException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Reservation not found: " + e.getMessage());
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid bookingId format: " + e.getMessage());
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Server error: " + e.getMessage());
        }
    }
}
