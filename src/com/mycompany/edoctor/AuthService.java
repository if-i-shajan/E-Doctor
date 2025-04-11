package com.mycompany.edoctor;

import java.sql.*;
import java.util.Arrays;

public class AuthService {
    private static final String DB_URL = "jdbc:derby://localhost:1527/E_Doctor_DB";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "root";
    
    // Authenticate regular user
    public static boolean authenticateUser(String username, String password) {
        String sql = "SELECT password_hash, salt FROM users WHERE username = ?";
        
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                String storedHash = rs.getString("password_hash");
                byte[] salt = rs.getBytes("salt");
                String inputHash = SecurityUtils.hashPassword(password, salt);
                return storedHash.equals(inputHash);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    // Authenticate admin user
    public static boolean authenticateAdmin(String username, String password) {
        String sql = "SELECT password_hash, salt FROM admins WHERE username = ?";
        
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                String storedHash = rs.getString("password_hash");
                byte[] salt = rs.getBytes("salt");
                String inputHash = SecurityUtils.hashPassword(password, salt);
                return storedHash.equals(inputHash);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    // Create new user account
    public static boolean createUser(String username, String password, String firstName, 
                                   String lastName, String dob, String contact) {
        if (!SecurityUtils.isValidUsername(username) || !SecurityUtils.isStrongPassword(password)) {
            return false;
        }
        
        byte[] salt = SecurityUtils.generateSalt();
        String hashedPassword = SecurityUtils.hashPassword(password, salt);
        
        String sql = "INSERT INTO users (username, password_hash, salt, first_name, last_name, dob, contact) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, SecurityUtils.sanitizeInput(username));
            stmt.setString(2, hashedPassword);
            stmt.setBytes(3, salt);
            stmt.setString(4, SecurityUtils.sanitizeInput(firstName));
            stmt.setString(5, SecurityUtils.sanitizeInput(lastName));
            stmt.setString(6, dob);
            stmt.setString(7, SecurityUtils.sanitizeInput(contact));
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}