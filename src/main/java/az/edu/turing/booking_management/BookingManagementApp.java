package az.edu.turing.booking_management;

import az.edu.turing.booking_management.controller.bookingServletController.BookingCreateServlet;
import az.edu.turing.booking_management.controller.bookingServletController.BookingetReservationServlet;
import az.edu.turing.booking_management.controller.bookingServletController.CancelByIdServlet;
import az.edu.turing.booking_management.controller.flightServletController.FlightByIdServlet;
import az.edu.turing.booking_management.controller.flightServletController.FlightByLocation;
import az.edu.turing.booking_management.controller.flightServletController.FlightCreateServlet;
import az.edu.turing.booking_management.controller.flightServletController.FlightGetAllServlet;
import az.edu.turing.booking_management.service.FlightService;
import az.edu.turing.booking_management.service.impl.FlightServiceImpl;
import az.edu.turing.booking_management.util.DatabaseUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class BookingManagementApp {
    private static final DatabaseUtils databaseUtils = new DatabaseUtils();

    public static void main(String[] args) throws Exception {
        databaseUtils.resetAll();
        Server server = new Server(8080);
        ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        handler.setContextPath("/");
        server.setHandler(handler);

        FlightService flightService = new FlightServiceImpl();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());


        handler.addServlet(new ServletHolder(new BookingCreateServlet()), "/booking/create");
        handler.addServlet(new ServletHolder(new BookingetReservationServlet()), "/booking/reservation");
        handler.addServlet(new ServletHolder(new CancelByIdServlet()), "/booking/cancel-id");

        handler.addServlet(new ServletHolder(new FlightCreateServlet()), "/flight/create");
        handler.addServlet(new ServletHolder(new FlightByLocation(flightService,objectMapper)), "/flight/by-location");
        handler.addServlet(new ServletHolder(new FlightByIdServlet(flightService, objectMapper)), "/flight/by-id");
        handler.addServlet(new ServletHolder(new FlightGetAllServlet(flightService,objectMapper)),"/flight/get-All");
        server.start();
        server.join();
    }
}
