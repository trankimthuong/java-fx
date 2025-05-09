package sample.dao;

import sample.model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    private Connection connection;

    public UserDao() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_management", "root", "123456789");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                users.add(new User(rs.getInt("id"), rs.getString("name"), rs.getString("address"),
                        rs.getString("numberPhone"), rs.getString("email")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void createUser(User user) {
        String query = "INSERT INTO users (name, address, numberPhone, email) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getAddress());
            pstmt.setString(3, user.getNumberPhone());
            pstmt.setString(4, user.getEmail());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateUser(User user) {
        String query = "UPDATE users SET name = ?, address = ?, numberPhone = ?, email = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getAddress());
            pstmt.setString(3, user.getNumberPhone());
            pstmt.setString(4, user.getEmail());
            pstmt.setInt(5, user.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(int id) {
        String query = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
