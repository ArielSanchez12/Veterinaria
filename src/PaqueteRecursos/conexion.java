package PaqueteRecursos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class conexion {

    String URL = "jdbc:mysql://uxaryiotv2i6dr7y:wcqK3cQnqmlDWAnyyqDt@bapfqibxooesuydolxyr-mysql.services.clever-cloud.com:3306/bapfqibxooesuydolxyr";
    String USER = "uxaryiotv2i6dr7y";
    String PASSWORD = "wcqK3cQnqmlDWAnyyqDt";

    public Connection connect() throws SQLException{
        return DriverManager.getConnection(URL,USER,PASSWORD);
    }
}