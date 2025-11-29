package services;

import java.io.*;
import java.util.*;

public class AdminDashboard {
private static final String PRODUCT_FILE = "database/product.txt";
private static Scanner scanner = new Scanner(System.in);

public static void showAdminMenu(String username) {
while (true) {
System.out.println("\n=== Admin Dashboard ===");
System.out.println("1. Add Product");
System.out.println("2. View Products");
System.out.println("3. Remove Product");
System.out.println("4. Modify Product");
System.out.println("5. Logout");

System.out.print("Enter your choice: ");
int choice = scanner.nextInt();
scanner.nextLine(); 

switch (choice) {
case 1: addProduct(); 
break;
case 2: viewProducts(); 
break;
case 3: removeProduct(); 
break;
case 4: modifyProduct(); 
break;
case 5:
System.out.println("Logging out...");
postAdminLogout(username);
return;
default: System.out.println("Invalid choice. Try again.");
}
}
}

private static void addProduct() {
System.out.print("Enter product ID: ");
String id = scanner.nextLine();

System.out.print("Enter product name: ");
String name = scanner.nextLine();

System.out.print("Enter product price: ");
double price = scanner.nextDouble();
scanner.nextLine();

try (BufferedReader reader = new BufferedReader(new FileReader(PRODUCT_FILE))) {
String line;
while ((line = reader.readLine()) != null) {
if (line.startsWith(id + ",")) {
System.out.println("Product ID already exists. Try a different one.");
return;
}
}
} 
catch (IOException ignored) {}

try (FileWriter writer = new FileWriter(PRODUCT_FILE, true)) {
writer.write(id + "," + name + "," + price + "\n");
System.out.println("Product added successfully!");
} 
catch (IOException e) {
System.out.println("Error saving product: " + e.getMessage());
}
}

private static void viewProducts() {
File file = new File(PRODUCT_FILE);
if (!file.exists()) {
System.out.println("No products found.");
return;
}

try (BufferedReader reader = new BufferedReader(new FileReader(PRODUCT_FILE))) {
String line;
System.out.println("\n=== Product List ===");
while ((line = reader.readLine()) != null) {
String[] parts = line.split(",");
if (parts.length == 3) {
System.out.println("ID: " + parts[0] + " | Name: " + parts[1] + " | Price: Rs" + parts[2]);
}
}
} 
catch (IOException e) {
System.out.println("Error reading products: " + e.getMessage());
}
}

private static void removeProduct() {
System.out.print("Enter Product ID to remove: ");
String productId = scanner.nextLine();

List<String> products = new ArrayList<>();
boolean found = false;

try (BufferedReader reader = new BufferedReader(new FileReader(PRODUCT_FILE))) {
String line;
while ((line = reader.readLine()) != null) {
if (!line.startsWith(productId + ",")) {
products.add(line);
} else {
found = true;
}
}
} catch (IOException e) {
System.out.println("Error reading products: " + e.getMessage());
return;
}
if (!found) {
System.out.println("Product not found.");
return;
}

try (FileWriter writer = new FileWriter(PRODUCT_FILE)) {
for (String product : products) {
writer.write(product + "\n");
}
System.out.println("Product removed successfully!");
} 
catch (IOException e) {
System.out.println("Error updating product file: " + e.getMessage());
}
}

private static void modifyProduct() {
System.out.print("Enter Product ID to modify: ");
String productId = scanner.nextLine();

List<String> products = new ArrayList<>();
boolean found = false;

try (BufferedReader reader = new BufferedReader(new FileReader(PRODUCT_FILE))) {
String line;
while ((line = reader.readLine()) != null) {
if (line.startsWith(productId + ",")) {
System.out.print("Enter new product name: ");
String newName = scanner.nextLine();
System.out.print("Enter new product price: ");
double newPrice = scanner.nextDouble();
scanner.nextLine();
products.add(productId + "," + newName + "," + newPrice);
found = true;
} else {
products.add(line);
}
}
} 
catch (IOException e) {
System.out.println("Error reading products: " + e.getMessage());
return;
}
if (!found) {
System.out.println("Product not found.");
return;
}

try (FileWriter writer = new FileWriter(PRODUCT_FILE)) {
for (String product : products) {
writer.write(product + "\n");
}
System.out.println("Product modified successfully!");
} 
catch (IOException e) {
System.out.println("Error updating product file: " + e.getMessage());
}
}

private static void postAdminLogout(String username) {
Scanner in = new Scanner(System.in);
System.out.print("Do you want to go to User Dashboard? (yes/no): ");
String choice = in.nextLine();
if (choice.equalsIgnoreCase("yes")) {
UserDashboard.showUserMenu(username);
} else {
System.out.println("Logged out. Goodbye!");
}
}
public static void main(String[] args) {
Scanner scanner = new Scanner(System.in);
System.out.print("Enter admin username: ");
String username = scanner.nextLine();
showAdminMenu(username);
}
}
