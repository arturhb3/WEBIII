package org.example.livros.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    private static Connection connection;

    public static Connection getConnection(){

        String url = "jdbc:mysql://localhost/biblioteca";
        String user = "root";
        String pass = "bancodedados";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, pass);

        } catch (SQLException e){
            System.out.println(e.getMessage());
        }catch (ClassNotFoundException e){
            throw new RuntimeException(e);
        }

        return connection;
    }

    public static void  closeConnection(){



    }

}
