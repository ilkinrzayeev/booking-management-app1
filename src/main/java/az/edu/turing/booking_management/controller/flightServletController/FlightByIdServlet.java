package az.edu.turing.booking_management.controller.flightServletController;

import az.edu.turing.booking_management.model.dto.FlightDto;
import az.edu.turing.booking_management.service.FlightService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

public class FlightByIdServlet extends HttpServlet {
    private final FlightService flightService;
    private final ObjectMapper objectMapper;

    public FlightByIdServlet(FlightService flightService, ObjectMapper objectMapper) {
        this.flightService = flightService;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            long id = Long.parseLong(request.getParameter("id"));
            Optional<FlightDto> flight = flightService.getFlightById(id);
            if (flight.isPresent()) {
                response.getWriter().write(objectMapper.writeValueAsString(flight.get()));
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Flight not found");
            }
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
    }
}
