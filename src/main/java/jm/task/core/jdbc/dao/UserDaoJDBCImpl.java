package jm.task.core.jdbc.dao;

import com.mysql.cj.jdbc.SuspendableXAConnection;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String createSQL = "CREATE TABLE IF NOT EXISTS users (\n" +
                            "id INT PRIMARY KEY AUTO_INCREMENT," +
                            "name VARCHAR(255) NOT NULL," +
                            "lastName VARCHAR(255) NOT NULL," +
                            "age INT NOT NULL" +
                            ")";
            try (Connection conn = Util.getConnection();
                 Statement statement = conn.createStatement()) {
                statement.execute(createSQL);
                System.out.println("Table created or already exists.");
            } catch (SQLException e) {
                System.out.println("Unable to create table." + e.getStackTrace());
            }

    }

    public void dropUsersTable() {
        String dropSQL = "DROP TABLE IF EXISTS users";

        try (Connection conn = Util.getConnection();
             Statement statement = conn.createStatement()) {
            statement.execute(dropSQL);
            System.out.println("Table dropped.");
        } catch (SQLException e) {
            System.out.println("Unable to drop table." + e.getStackTrace());
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        String  sqlSave = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)";
        try (Connection conn = Util.getConnection();
             PreparedStatement statement = conn.prepareStatement(sqlSave)) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);

            int rows = statement.executeUpdate();
            System.out.println("Rows affected: " + rows);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        String sqlRemove = "DELETE FROM users WHERE id = ?";

        try (Connection connection = Util.getConnection();
             PreparedStatement ps = connection.prepareStatement(sqlRemove)) {
            ps.setLong(1, id);
            int rows = ps.executeUpdate();
            System.out.println("Deleted " + rows + " user(s) with ID: " + id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        String sql = "SELECT * FROM users";
        List<User> users = new ArrayList<>();


        try (Connection conn = Util.getConnection();
             Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {


            while (resultSet.next()) {
                User user = new User(

                        resultSet.getString("name"),
                        resultSet.getString("lastName"),
                        resultSet.getByte("age")
            );
                users.add(user);
                user.setId(resultSet.getLong("id"));
            }
            System.out.println("Loaded " + users.size() + " users.");
            for (User user : users) {
                System.out.println("User: " + user.getId() + " - " +
                        user.getName() + " " + user.getLastName() +
                        ", Age: " + user.getAge());
            }


        } catch (Exception e) {
            System.err.println("Unable to load users." + e.getStackTrace());
            return new ArrayList<>();
        }
        return users;
    }
    public void cleanUsersTable() {
        String sql = "DELETE FROM users";

        try (Connection conn = Util.getConnection();
             Statement statement = conn.createStatement()) {

            int rowsDeleted = statement.executeUpdate(sql);
            System.out.println("Cleaned " + rowsDeleted + " users from the table.");

        } catch (SQLException e) {
            System.out.println("Error cleaning table: " + e.getMessage());
        }

    }
}
