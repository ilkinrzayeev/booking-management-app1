package az.edu.turing.booking_management.controller.flightServletController;

import az.edu.turing.booking_management.dao.impl.FlightPostgresDao;
import az.edu.turing.booking_management.model.dto.FlightDto;
import az.edu.turing.booking_management.service.FlightService;
import az.edu.turing.booking_management.service.impl.FlightServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class FlightCreateServlet extends HttpServlet {
    private FlightService flightService;
    private ObjectMapper objectMapper;

    @Override
    public void init() throws ServletException {
        super.init();
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.flightService = new FlightServiceImpl(new FlightPostgresDao());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            response.setContentType("application/json");
            FlightDto flightDto = objectMapper.readValue(request.getReader(), FlightDto.class);
            boolean creationSuccess = flightService.createFlight(flightDto);
            if (creationSuccess) {
                response.setStatus(HttpServletResponse.SC_CREATED);
                response.getWriter().write("Flight creation successful");
            } else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Flight creation failed");
            }
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Server error: " + e.getMessage());
        }
    }
}
