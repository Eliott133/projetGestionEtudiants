package util;

import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionBDD {

    private static java.sql.Connection connection;

    public static java.sql.Connection getConnection() {
        return connection;
    }

    public ConnectionBDD() {
        try {
            Class.forName(DAO.DRIVER);
            java.sql.Connection con = DriverManager.getConnection(DAO.DB_URL, DAO.USER, DAO.PASS);
            this.connection = con;
        } catch (ClassNotFoundException | SQLException ex) {
        }
    }

}
