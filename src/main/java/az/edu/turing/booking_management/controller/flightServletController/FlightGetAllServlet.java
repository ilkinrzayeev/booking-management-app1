package az.edu.turing.booking_management.controller.flightServletController;

import az.edu.turing.booking_management.model.dto.FlightDto;
import az.edu.turing.booking_management.service.FlightService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class FlightGetAllServlet extends HttpServlet {
    private final FlightService flightService;
    private final ObjectMapper objectMapper;

    public FlightGetAllServlet(FlightService flightService, ObjectMapper objectMapper) {
        this.flightService = flightService;
        this.objectMapper = objectMapper;
    }

    @Override
    public void init() {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        try {
            List<FlightDto> flights = flightService.getAllFlights();

            String jsonFlights = objectMapper.writeValueAsString(flights);
            out.println(jsonFlights);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.println("Error retrieving flights: " + e.getMessage());
        } finally {
            out.flush();
        }
    }
}
