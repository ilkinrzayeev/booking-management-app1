package az.edu.turing.booking_management.dao.impl;

import az.edu.turing.booking_management.dao.FlightDao;
import az.edu.turing.booking_management.model.entity.FlightEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class FlightPostgresDao extends FlightDao {

    Connection connection = new JdbcConnect().getConnection();

    @Override
    public boolean save(List<FlightEntity> flightEntities) {
        final String flightsave = "INSERT INTO flights(date_and_time,seats,destination,location) VALUES (?,?,?,?)";
        try (PreparedStatement statementflight = connection.prepareStatement(flightsave)) {
            for (FlightEntity flightsEntity : flightEntities) {
                statementflight.setTimestamp(1, Timestamp.valueOf(flightsEntity.getDepartureTime()));
                statementflight.setInt(2, flightsEntity.getSeats());
                statementflight.setString(3, flightsEntity.getDestination());
                statementflight.setString(4, flightsEntity.getLocation());
                statementflight.addBatch();
            }
            statementflight.executeBatch();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void update(long flightId, int seats) {
        String getSeatsSql = "SELECT seats FROM flights WHERE id = ?";
        String updateSql = "UPDATE flights SET seats = ? WHERE id = ?";
        try {
            connection.setAutoCommit(false);
            int currentSeats;
            try (PreparedStatement getSeatsStatement = connection.prepareStatement(getSeatsSql)) {
                getSeatsStatement.setLong(1, flightId);
                ResultSet resultSet = getSeatsStatement.executeQuery();
                if (resultSet.next()) {
                    currentSeats = resultSet.getInt("seats");
                } else {
                    throw new SQLException("Flight with ID " + flightId + " not found.");
                }
            }
            int newSeats = currentSeats + seats;

            try (PreparedStatement updateStatement = connection.prepareStatement(updateSql)) {
                updateStatement.setInt(1, newSeats);
                updateStatement.setLong(2, flightId);
                updateStatement.executeUpdate();
            }
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException rollbackException) {
                    rollbackException.printStackTrace();
                }
            }
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    @Override
    public void delete(long id) {
        String deleteSql = "DELETE FROM flights WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(deleteSql)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public List<FlightEntity> getAll() {
        final String sql = "SELECT * FROM flights";
        List<FlightEntity> flightEntities = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                FlightEntity flightEntity = new FlightEntity(
                        resultSet.getInt("id"),
                        resultSet.getInt("seats"),
                        resultSet.getTimestamp("date_and_time").toLocalDateTime(),
                        resultSet.getString("location"),
                        resultSet.getString("destination"));
                flightEntities.add(flightEntity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flightEntities;
    }
    @Override
    public Optional<FlightEntity> getOneBy(Predicate<FlightEntity> predicate) {
        String getOneBySql = "SELECT * FROM flights";
        try (PreparedStatement statement = connection.prepareStatement(getOneBySql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                FlightEntity flightEntity = new FlightEntity(
                        resultSet.getInt("id"),
                        resultSet.getInt("seats"),
                        resultSet.getTimestamp("date_and_time").toLocalDateTime(),
                        resultSet.getString("location"),
                        resultSet.getString("destination"));
                if (predicate.test(flightEntity)) {
                    return Optional.of(flightEntity);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();

    }
    @Override
    public List<FlightEntity> getAllBy(Predicate<FlightEntity> predicate) {
        List<FlightEntity> flightEntities = new ArrayList<>();
        String getAllBySql = "SELECT * FROM flights";
        try (PreparedStatement statement = connection.prepareStatement(getAllBySql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                FlightEntity flightEntity = new FlightEntity(
                        resultSet.getInt("id"),
                        resultSet.getInt("seats"),
                        resultSet.getTimestamp("date_and_time").toLocalDateTime(),
                        resultSet.getString("location"),
                        resultSet.getString("destination"));
                if (predicate.test(flightEntity)) {
                    flightEntities.add(flightEntity);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flightEntities;
    }
}
