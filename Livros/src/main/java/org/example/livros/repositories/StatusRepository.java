package org.example.livros.repositories;

import org.example.livros.connection.ConnectionFactory;
import org.example.livros.exceptions.DatabaseException;
import org.example.livros.exceptions.DatabaseIntegrityException;
import org.example.livros.models.Autor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StatusRepository {

    Connection connection;

    public StatusRepository(){
        connection = ConnectionFactory.getConnection();
    }

    public List<Autor> getAll(){
        List<Autor> statusArray= new ArrayList<>();
        String sql= "SELECT * FROM status";


        try {
            Statement statement =connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while (result.next()){
                Autor status = new Autor();
                status.setId(result.getInt("id"));
                status.setName(result.getString("Name"));
                statusArray.add(status);

            }
        }catch (SQLException e){
            throw  new DatabaseIntegrityException(e.getMessage());
        }
        return statusArray;
    }
    public void delete(Integer id){

        String sql = "DELETE FROM status WHERE Id = ?";

        try {

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            Integer rowsDeleted = statement.executeUpdate();

            if(rowsDeleted > 0){
                System.out.println("Rows deleted: " + rowsDeleted);
            }

        } catch (Exception e){
            throw new DatabaseIntegrityException(e.getMessage());
        }

    }



    public List<Autor> getStatus() {

        List<Autor> statusArray = new ArrayList<>();

        try {

            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM `status`");

            while (result.next()) {

                Autor status = instantiateStatus(result);

                statusArray.add(status);

            }

            result.close();


        } catch (SQLException e) {

            throw new RuntimeException(e);

        } finally {
            ConnectionFactory.closeConnection();
        }


        return statusArray;
    }


    public Autor insert(Autor status) {

        String sql = "INSERT INTO status (Name) VALUES(?)";

        try {
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, status.getName());

            Integer rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {

                ResultSet id = statement.getGeneratedKeys();

                id.next();

                Integer statusId = id.getInt(1);

                System.out.println("Rows inserted: " + rowsInserted);
                System.out.println("Id: " + statusId);

                status.setId(statusId);

            }


        } catch (Exception e) {
            throw new DatabaseException(e.getMessage());
        }

        return status;
    }

    public  void updateName(Integer id,String name){
        String sql ="UPDATE `status` SET `Name` = ? WHERE `status`.`Id` = ?";

        try {

            PreparedStatement statement = connection.prepareStatement(sql);//crl+alt+v
            statement.setString(1, name);
            statement.setInt(2, id);

            Integer rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Rows updated: " + rowsUpdated);
            }

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
    }

    public Autor instantiateStatus(ResultSet resultSet) throws SQLException {

        Autor status = new Autor();

        status.setId(resultSet.getInt("Id"));
        status.setName(resultSet.getString("Name"));
        return status;
    }

    public Autor getById(Integer id) {

        Autor status;

        String sql = "SELECT * FROM status WHERE id=?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                status = this.instantiateStatus(resultSet);

            } else {
                throw new DatabaseException("Status não encontrado");
            }

        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }

        return status;
    }
}
