package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;

public class ConnectionPool {
    private final String url;
    private final LinkedList<Connection> pool = new LinkedList<>();

    public ConnectionPool(String url, int nThreads) throws SQLException {
        this.url = url;
        for (int i = 0; i < nThreads; i++) {
            pool.add(DriverManager.getConnection(url));
        }
    }

    public synchronized Connection getConnection() throws SQLException {
        if (pool.isEmpty()) {
            pool.add(DriverManager.getConnection(url));
        }
        return pool.pop();
    }

    public synchronized void returnConnection(Connection connection) {
        pool.push(connection);
    }
}
