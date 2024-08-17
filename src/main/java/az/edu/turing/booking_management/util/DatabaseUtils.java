package az.edu.turing.booking_management.util;

import az.edu.turing.booking_management.dao.impl.JdbcConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class DatabaseUtils {

    JdbcConnect jdbcConnect = new JdbcConnect();

    static Connection conn = null;

    public void resetAll() {
        List<String> tables = Arrays.asList("bookings", "flights", "passengers");

        if (!tables.isEmpty()) {
            try {
                for (String tableName : tables) {
                    long maxId = getMaxIdFromTable(getConnection(), tableName);
                    resetSequence(getConnection(), tableName + "_id_seq", maxId);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("The list of tables is empty. No sequences to reset.");
        }
    }

    private long getMaxIdFromTable(Connection conn, String tableName) throws SQLException {
        String getMaxIdSql = "SELECT MAX(id) FROM " + tableName;
        try (PreparedStatement statement = conn.prepareStatement(getMaxIdSql)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getLong(1);
            }
        }
        return 0;
    }


    public void resetSequence(Connection conn, String sequenceName, long maxValue) throws SQLException {
        String resetSequenceSql = "ALTER SEQUENCE " + sequenceName + " RESTART WITH " + (maxValue + 1);
        try (PreparedStatement statement = conn.prepareStatement(resetSequenceSql)) {
            statement.executeUpdate();
        }
    }

    public Connection getConnection() {
        return jdbcConnect.getConnection();
    }
}

