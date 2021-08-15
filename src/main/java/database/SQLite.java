package database;

import model.Address;
import model.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class SQLite {
    private final Logger logger = LoggerFactory.getLogger(SQLite.class);
    protected final ConnectionPool connectionPool;

    public SQLite(String url) throws SQLException {
        this(url, 1);
    }

    public SQLite(String url, int nThreads) throws SQLException {
        connectionPool = new ConnectionPool(url, nThreads);
    }

    public void createLocationTable() throws SQLException {
        createTable(
                "CREATE TABLE IF NOT EXISTS location (" +
                "location_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "lat FLOAT, " +
                "lon FLOAT, " +
                "address_id INTEGER" +
                ")"
        );
    }

    public void createAddressTable() throws SQLException {
        createTable(
                "CREATE TABLE IF NOT EXISTS address (" +
                "address_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "country VARCHAR(100), " +
                "city VARCHAR(100)," +
                "road VARCHAR(100)," +
                "house_number VARCHAR(100)" +
                ")"
        );
    }

    public void createTable(String create) throws SQLException {
        Connection connection = connectionPool.getConnection();
        try (Statement statement = connection.createStatement()) {
            statement.execute(create);
        }
        connectionPool.returnConnection(connection);
    }

    public Location insertLocation(Location location) throws SQLException {
        Connection connection = connectionPool.getConnection();
        location.setLocationId(insertLocation(location, connection));
        connectionPool.returnConnection(connection);
        return location;
    }

    protected long insertLocation(Location location, Connection connection) {
        String insert = "insert into location values (NULL, ?, ?, ?);";
        if(location.getAddress() != null) {
            location.getAddress().setAddressId(insertAddress(location.getAddress(), connection));
        }
        try (PreparedStatement prepStmt = connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)) {
            prepStmt.setDouble(1, location.getLat());
            prepStmt.setDouble(2, location.getLon());
            if(location.getAddress() != null) {
                prepStmt.setLong(3, location.getAddress().getAddressId());
            }
            prepStmt.execute();
            try (ResultSet generatedKeys = prepStmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                } else {
                    throw new SQLException("Creating location failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return 0;
    }

    public Address insertAddress(Address address) throws SQLException {
        Connection connection = connectionPool.getConnection();
        address.setAddressId(insertAddress(address, connection));
        connectionPool.returnConnection(connection);
        return address;
    }

    protected long insertAddress(Address address, Connection connection) {
        String insert = "insert into address values (NULL, ?, ?, ?, ?);";
        try (PreparedStatement prepStmt = connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)) {
            prepStmt.setString(1, address.getCountry());
            prepStmt.setString(2, address.getCity());
            prepStmt.setString(3, address.getRoad());
            prepStmt.setString(4, address.getHouseNumber());
            prepStmt.execute();
            try (ResultSet generatedKeys = prepStmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                } else {
                    throw new SQLException("Creating address failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return 0;
    }
}
