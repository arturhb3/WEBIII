package org.example.livros.repositories;



import org.example.livros.connection.ConnectionFactory;
import org.example.livros.exceptions.DatabaseException;
import org.example.livros.models.Livro;
import org.example.livros.models.Status;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LivroRepository {

    private Connection connection;

    public LivroRepository() {

        connection = ConnectionFactory.getConnection();

    }

    public void update(Livro livro){
        String sql= String.format("UPDATE livro SET Name = ?, Autor = ?, DataLancamento = ?, StatusId = ? WHERE (Id = ?)");
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,livro.getName());
            statement.setString(2,livro.getAutor());
            statement.setDate(3,Date.valueOf(livro.getDataLancamento()));
            statement.setInt(4,livro.getStatus().getId());
            statement.setInt(5,livro.getId());

            int rowAffected = statement.executeUpdate();
            System.out.println("Linhas afetadas:"+ rowAffected);
        }catch (SQLException e){
            throw new DatabaseException(e.getMessage());
        }
    }

    public List<Livro> getLivro() {

        List<Livro> livroArray = new ArrayList<>();

        try {

            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT livro.*, status.Name AS DepName" +
                                                          " FROM livro JOIN status" +
                                                          " ON livro.StatusId = status.id");

            while (result.next()) {
                Status status = instantiateStatus(result);
                Livro livro = instantiateLivro(result, status);


                livroArray.add(livro);

            }

            result.close();


        } catch (SQLException e) {

            throw new RuntimeException(e);

        } finally {
            ConnectionFactory.closeConnection();
        }


        return livroArray;
    }

    public Livro insert(Livro livro) {

        String sql = "INSERT INTO livro (Name, Autor, DataLancamento, StatusId) " +
                "VALUES(?, ?, ?, ?)";

        try {
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, livro.getName());
            statement.setString(2, livro.getAutor());
            statement.setDate(3, Date.valueOf(livro.getDataLancamento()));
            statement.setInt(4, 1);

            Integer rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {

                ResultSet id = statement.getGeneratedKeys();

                id.next();

                Integer livroId = id.getInt(1);

                System.out.println("Rows inserted: " + rowsInserted);
                System.out.println("Id: " + livroId);

                livro.setId(livroId);

            }


        } catch (Exception e) {
            throw new DatabaseException(e.getMessage());
        }

        return livro;
    }



    public void delete(Integer id) {

        String sql = "DELETE FROM livro WHERE Id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, id);
            Integer rowsDeleted = statement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Rows deleted: " + rowsDeleted);
            }

        } catch (Exception e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
    }

    public Livro getById(Integer id) {

        Livro livro;
        Status status;

        String sql = "SELECT livro.*,status.Name as DepName " +
                "FROM livro " +
                "INNER JOIN status " +
                "ON livro.StatusId = status.Id " +
                "WHERE livro.Id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                status = this.instantiateStatus(resultSet);
                livro = this.instantiateLivro(resultSet, status);

            } else {
                throw new DatabaseException("Vendedor não encontrado");
            }

        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }

        return livro;
    }

    public List<Livro> findByStatus(Integer id) {

        List<Livro> livrosArray = new ArrayList<>();

        Livro livro;
        Status status;

        String sql = "SELECT livro.*,status.Name as DepName " +
                "FROM livro INNER JOIN status " +
                "ON livro.StatusId = status.Id " +
                "WHERE StatusId = ? " +
                "ORDER BY Name";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            Map<Integer, Status> map = new HashMap<>();

            while (resultSet.next()) {

                status = map.get(resultSet.getInt("StatusId"));

                if (status == null) {
                    status = instantiateStatus(resultSet);
                    map.put(resultSet.getInt("StatusId"), status);
                }

                livro = this.instantiateLivro(resultSet, status);

                livrosArray.add(livro);
            }

        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }

        return livrosArray;

    }

    public Livro instantiateLivro(ResultSet resultSet, Status status) throws SQLException {

        Livro livro = new Livro();

        livro.setId(resultSet.getInt("Id"));
        livro.setName(resultSet.getString("Name"));
        livro.setAutor(resultSet.getString("Autor"));
        livro.setDataLancamento(resultSet.getDate("DataLancamento").toLocalDate());
        livro.setStatus(status);

        return livro;
    }

    public Status instantiateStatus(ResultSet resultSet) throws SQLException {

        Status status = new Status();

        status.setId(resultSet.getInt("StatusId"));
        status.setName(resultSet.getString("DepName"));

        return status;
    }

}
