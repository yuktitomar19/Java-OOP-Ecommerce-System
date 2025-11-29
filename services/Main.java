package services;

import ui.LoginSignupGUI;
import database.DatabaseConnection;
import java.sql.*;

public class Main {
public static void main(String[] args) {
new LoginSignupGUI();
}

public static void handleLogin(String username, String password) {
try (Connection conn = DatabaseConnection.getConnection()) {
String query = "SELECT role FROM users WHERE username = ? AND password = ?";
PreparedStatement stmt = conn.prepareStatement(query);
stmt.setString(1, username);
stmt.setString(2, password);
ResultSet rs = stmt.executeQuery();

if (rs.next()) {
String role = rs.getString("role");
System.out.println("Login successful! Role: " + role);
if (role.equalsIgnoreCase("admin")) {
AdminDashboard.showAdminMenu(username);
} else {
UserDashboard.showUserMenu(username);
}
} else {
System.out.println("Invalid credentials.");
}
} 
catch (SQLException e) {
System.out.println("Database error during login: " + e.getMessage());
}
}

public static void signup(String username, String password, String role) {
if (!role.equalsIgnoreCase("admin") && !role.equalsIgnoreCase("user")) {
System.out.println("Invalid role. Please enter 'admin' or 'user'.");
return;
}

try (Connection conn = DatabaseConnection.getConnection()) {
String checkQuery = "SELECT username FROM users WHERE username = ?";
PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
checkStmt.setString(1, username);
ResultSet rs = checkStmt.executeQuery();
if (rs.next()) {
System.out.println("Username already exists. Try logging in.");
return;
}
String insertQuery = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
insertStmt.setString(1, username);
insertStmt.setString(2, password);
insertStmt.setString(3, role);
insertStmt.executeUpdate();
System.out.println("Signup successful. You can now log in.");
} 
catch (SQLException e) {
System.out.println("Database error during signup: " + e.getMessage());
}
}
}
