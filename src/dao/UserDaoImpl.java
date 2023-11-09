package dao;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.User;

public class UserDaoImpl implements UserDao {
    private final String TABLE_NAME = "users";

    public UserDaoImpl() {
    }

    /**
     * Creates the "users" table if it doesn't exist.
     *
     * @throws SQLException if a database access error occurs
     */
    @Override
    public void setup() throws SQLException {
        try (Connection connection = Database.getConnection();
             Statement stmt = connection.createStatement();) {
            String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (username VARCHAR(10) NOT NULL,"
                    + "password VARCHAR(8) NOT NULL," + "firstname VARCHAR(15) NOT NULL," + "lastname VARCHAR(15) NOT NULL,"
                    + "id NUMBER(15) NOT NULL," + "PRIMARY KEY (username))";
            stmt.executeUpdate(sql);
        }
    }

    /**
     * Retrieves a user from the database based on the provided username and password.
     * 
     * @return the User object if found, null otherwise
     * @throws SQLException if a database access error occurs
     */
    @Override
    public User getUser(String username, String password) throws SQLException {
        String hashPassword = "";
        try {
            hashPassword = generateHashPassword(password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE username = ? AND password = ?";
        try (Connection connection = Database.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);) {
            stmt.setString(1, username);
            stmt.setString(2, hashPassword);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                    user.setFirstName(rs.getString("firstname"));
                    user.setLastName(rs.getString("lastname"));
                    user.setID(rs.getInt("id"));
                    return user;
                }
                return null;
            }
        }
    }

    /**
     * Creates a new user in the database.
     *
     * @return the created User object
     * @throws SQLException if a database access error occurs
     */
    @Override
    public User createUser(String username, String password, String firstname, String lastname, Integer id) throws SQLException {
        String hashPassword = "";
        try {
            hashPassword = generateHashPassword(password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        String sql = "INSERT INTO " + TABLE_NAME + " VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = Database.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);) {
            stmt.setString(1, username);
            stmt.setString(2, hashPassword);
            stmt.setString(3, firstname);
            stmt.setString(4, lastname);
            stmt.setInt(5, id);
            stmt.executeUpdate();
            return new User(username, password, firstname, lastname, id);
        }
    }

    /**
     * Updates the user's information in the database.
     *
     * @return the updated User object
     * @throws SQLException if a database access error occurs
     */
    @Override
    public User editUser(String password, String firstname, String lastname, String username, Integer id) throws SQLException {
        String hashPassword = "";
        try {
            hashPassword = generateHashPassword(password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        String sql = "UPDATE users SET password = ?, firstname = ?, lastname = ? WHERE username = ?";
        try (Connection connection = Database.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);) {
            stmt.setString(1, hashPassword);
            stmt.setString(2, firstname);
            stmt.setString(3, lastname);
            stmt.setString(4, username);
            stmt.executeUpdate();
            return new User(username, password, firstname, lastname, id);
        }
    }

    /**
     * Generates a hash value of a password using the SHA-256 algorithm.
     * 
     * @return the hashed password as a hexadecimal string
     * @throws NoSuchAlgorithmException if the specified algorithm does not exist
     */
    public String generateHashPassword(String input) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedHash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
        StringBuilder hexString = new StringBuilder();
        for (byte b : encodedHash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

}

