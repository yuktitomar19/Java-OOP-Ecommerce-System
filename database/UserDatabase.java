package database;

import java.io.*;
import java.util.*;

public class UserDatabase {
private final String FILE_NAME = "users.txt";
private Map<String, String> userMap = new HashMap<>();
private Map<String, String> roleMap = new HashMap<>();

public UserDatabase() {
loadUsersFromFile();  
}

private void loadUsersFromFile() {
File file = new File(FILE_NAME);
if (!file.exists()) return;  

try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
String line;
while ((line = reader.readLine()) != null) {
String[] parts = line.split(",");
if (parts.length == 3) {
userMap.put(parts[0], parts[1]);  
roleMap.put(parts[0], parts[2]);  
}
}
} 
catch (IOException e) {
e.printStackTrace();
}
}

public boolean signUp(String username, String password, String role) {
if (userMap.containsKey(username)) {
System.out.println(" Username already exists! Please login.");
return false;
}

userMap.put(username, password);
roleMap.put(username, role);
saveUsersToFile();
System.out.println(" Signup successful!");
return true;
}

public String login(String username, String password) {
if (userMap.containsKey(username) && userMap.get(username).equals(password)) {
System.out.println(" Login successful!");
return roleMap.get(username);  
} else {
System.out.println(" Invalid username or password.");
return null;
}
}

private void saveUsersToFile() {
try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
for (String username : userMap.keySet()) {
writer.write(username + "," + userMap.get(username) + "," + roleMap.get(username));
writer.newLine();
}
} 
catch (IOException e) {
e.printStackTrace();
}
}
}