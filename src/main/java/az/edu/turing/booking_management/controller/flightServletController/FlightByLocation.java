package az.edu.turing.booking_management.controller.flightServletController;

import az.edu.turing.booking_management.model.dto.FlightDto;
import az.edu.turing.booking_management.service.FlightService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class FlightByLocation extends HttpServlet {
    private final FlightService flightService;
    private final ObjectMapper objectMapper;

    public FlightByLocation(FlightService flightService, ObjectMapper objectMapper) {
        this.flightService = flightService;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            response.setContentType("application/json");
            String location = request.getParameter("location");
            List<FlightDto> flights = flightService.getAllFlightIn24Hours(location);
            response.getWriter().write(objectMapper.writeValueAsString(flights));
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Something went wrong! Try again!");
        }
    }
}
