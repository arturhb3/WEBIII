package br.edu.ifpr.foz.ifprstore.repositories;

import br.edu.ifpr.foz.ifprstore.connection.ConnectionFactory;
import br.edu.ifpr.foz.ifprstore.exceptions.DatabaseException;
import br.edu.ifpr.foz.ifprstore.exceptions.DatabaseIntegrityException;
import br.edu.ifpr.foz.ifprstore.models.Department;



import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DepartmentRepository {

    Connection connection;

    public DepartmentRepository(){
        connection = ConnectionFactory.getConnection();
    }


    public void delete(Integer id){

        String sql = "DELETE FROM department WHERE Id = ?";

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



    public List<Department> getDepartaments() {

        List<Department> departments = new ArrayList<>();

        try {

            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM `department`");

            while (result.next()) {

                Department department = instantiateDepartment(result);

                departments.add(department);

            }

            result.close();


        } catch (SQLException e) {

            throw new RuntimeException(e);

        } finally {
            ConnectionFactory.closeConnection();
        }


        return departments;
    }


    public Department insert(Department departament) {

        String sql = "INSERT INTO department (Name) VALUES(?)";

        try {
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, departament.getName());

            Integer rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {

                ResultSet id = statement.getGeneratedKeys();

                id.next();

                Integer departamentId = id.getInt(1);

                System.out.println("Rows inserted: " + rowsInserted);
                System.out.println("Id: " + departamentId);

                departament.setId(departamentId);

            }


        } catch (Exception e) {
            throw new DatabaseException(e.getMessage());
        }

        return departament;
    }

    public  void updateName(Integer id,String name){
        String sql ="UPDATE `department` SET `Name` = ? WHERE `department`.`Id` = ?";

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

    public Department instantiateDepartment(ResultSet resultSet) throws SQLException {

        Department department = new Department();

        department.setId(resultSet.getInt("Id"));
        department.setName(resultSet.getString("Name"));
        return department;
    }

    public Department getById(Integer id) {

        Department department;

        String sql = "SELECT * FROM department WHERE id=?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                department = this.instantiateDepartment(resultSet);

            } else {
                throw new DatabaseException("Departamento não encontrado");
            }

        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }

        return department;
    }
}
