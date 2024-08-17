package az.edu.turing.booking_management.controller.bookingServletController;

import az.edu.turing.booking_management.dao.BookingDao;
import az.edu.turing.booking_management.dao.impl.BookingPostgresDao;
import az.edu.turing.booking_management.model.entity.BookingEntity;
import az.edu.turing.booking_management.service.BookingService;
import az.edu.turing.booking_management.service.impl.BookingServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class BookingetReservationServlet extends HttpServlet {
    private ObjectMapper objectMapper;
    private BookingService bookingService;

    public BookingetReservationServlet() {
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            response.setContentType("application/json");
            String userId = request.getParameter("userName");

            if (userId == null || userId.isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "User ID is required");
                return;
            }
            BookingDao bookingDao = new BookingPostgresDao();
            List<BookingEntity> reservations = bookingService.getMyReservations(userId, bookingDao);

            ObjectMapper objectMapper = new ObjectMapper();
            String reservationsJson = objectMapper.writeValueAsString(reservations);

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(reservationsJson);

        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Server error: " + e.getMessage());
        }
    }
}

