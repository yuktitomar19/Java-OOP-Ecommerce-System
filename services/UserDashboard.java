package services;

import java.io.*;
import java.util.*;

public class UserDashboard {
private static final String PRODUCT_FILE = "database/product.txt";
private static final String CART_FILE = "database/cart.txt";
private static final String ORDER_FILE = "database/order.txt";
private static final String USER_FILE = "database/user.txt";
private static Scanner scanner = new Scanner(System.in);

public static void showUserMenu(String username) {
updateUserFile(username, "User");
while (true) {
System.out.println("\n=== User Dashboard ===");
System.out.println("1. View Products");
System.out.println("2. Add to Cart");
System.out.println("3. View Cart");
System.out.println("4. Remove from Cart");
System.out.println("5. Checkout");
System.out.println("6. Logout");
System.out.print("Enter your choice: ");
int choice = scanner.nextInt();
scanner.nextLine();
switch (choice) {
case 1:
viewProducts();
break;
case 2:
addToCart(username);
break;
case 3:
viewCart(username);
break;
case 4:
removeFromCart(username);
break;
case 5:
checkout(username);
break;
case 6:
System.out.println("Logged out successfully.");
System.out.println("Thank you for visiting. See you again!");
System.exit(0);
default:
System.out.println("Invalid option.");
}
}
}

private static void updateUserFile(String username, String role) {
try {
File file = new File(USER_FILE);
if (!file.exists()) file.createNewFile();
BufferedReader reader = new BufferedReader(new FileReader(file));
String line;
while ((line = reader.readLine()) != null) {
if (line.startsWith(username + ",")) {
reader.close();
return;
}
}
reader.close();
BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
writer.write(username + "," + role);
writer.newLine();
writer.close();
System.out.println("User info saved to user.txt");
} 
catch (IOException e) {
System.out.println("Failed to update user.txt: " + e.getMessage());
}
}

private static void viewProducts() {
try (BufferedReader reader = new BufferedReader(new FileReader(PRODUCT_FILE))) {
String line;
System.out.println("\n=== Available Products ===");
while ((line = reader.readLine()) != null) {
String[] parts = line.split(",");
if (parts.length == 3) {
System.out.println("ID: " + parts[0] + " | Name: " + parts[1] + " | Price: Rs" + parts[2]);
}
}
} 
catch (IOException e) {
System.out.println("Error reading product list: " + e.getMessage());
}
}

private static void addToCart(String username) {
System.out.print("Enter Product ID to add: ");
String productId = scanner.nextLine();
System.out.print("Enter quantity: ");
int quantity = scanner.nextInt();
scanner.nextLine();
if (!productExists(productId)) {
System.out.println("Product ID not found.");
return;
}
try (FileWriter writer = new FileWriter(CART_FILE, true)) {
writer.write(username + "," + productId + "," + quantity + "\n");
System.out.println("Product added to cart!");
} 
catch (IOException e) {
System.out.println("Error adding to cart: " + e.getMessage());
}
}

private static void viewCart(String username) {
try (BufferedReader reader = new BufferedReader(new FileReader(CART_FILE))) {
String line;
System.out.println("\n=== Your Cart ===");
boolean found = false;
while ((line = reader.readLine()) != null) {
String[] parts = line.split(",");
if (parts.length == 3 && parts[0].equals(username)) {
String productId = parts[1];
int qty = Integer.parseInt(parts[2]);
String productName = getProductNameById(productId);
double price = getProductPriceById(productId);
System.out.println("ID: " + productId + " | Name: " + productName + " | Qty: " + qty + " | Price: Rs" + price);
found = true;
}
}
if (!found) System.out.println("Your cart is empty.");
} 
catch (IOException e) {
System.out.println("Error viewing cart: " + e.getMessage());
}
}

private static void removeFromCart(String username) {
System.out.print("Enter Product ID to remove from cart: ");
String productId = scanner.nextLine();
List<String> cartItems = new ArrayList<>();
boolean removed = false;
try (BufferedReader reader = new BufferedReader(new FileReader(CART_FILE))) {
String line;
while ((line = reader.readLine()) != null) {
String[] parts = line.split(",");
if (!(parts.length == 3 && parts[0].equals(username) && parts[1].equals(productId))) {
cartItems.add(line);
} else {
removed = true;
}
}
} 
catch (IOException e) {
System.out.println("Error reading cart: " + e.getMessage());
return;
}
if (!removed) {
System.out.println("Product not found in your cart.");
return;
}
try (FileWriter writer = new FileWriter(CART_FILE)) {
for (String item : cartItems) {
writer.write(item + "\n");
}
System.out.println("Product removed from cart.");
} 
catch (IOException e) {
System.out.println("Error updating cart: " + e.getMessage());
}
}

private static void checkout(String username) {
List<String> cartItems = new ArrayList<>();
boolean hasItems = false;
try (BufferedReader reader = new BufferedReader(new FileReader(CART_FILE))) {
String line;
while ((line = reader.readLine()) != null) {
if (line.startsWith(username + ",")) {
cartItems.add(line);
hasItems = true;
}
}
} 
catch (IOException e) {
System.out.println("Error reading cart: " + e.getMessage());
return;
}
if (!hasItems) {
System.out.println("Your cart is empty.");
return;
}
try (FileWriter orderWriter = new FileWriter(ORDER_FILE, true)) {
for (String item : cartItems) {
orderWriter.write(item + "\n");
}
} 
catch (IOException e) {
System.out.println("Error saving order: " + e.getMessage());
return;
}
try (BufferedReader reader = new BufferedReader(new FileReader(CART_FILE));
FileWriter writer = new FileWriter(CART_FILE)) {
String line;
while ((line = reader.readLine()) != null) {
if (!line.startsWith(username + ",")) {
writer.write(line + "\n");
}
}
} 
catch (IOException e) {
System.out.println("Error clearing cart: " + e.getMessage());
return;
}
System.out.println("Order placed successfully!");
}

private static boolean productExists(String productId) {
try (BufferedReader reader = new BufferedReader(new FileReader(PRODUCT_FILE))) {
String line;
while ((line = reader.readLine()) != null) {
if (line.startsWith(productId + ",")) {
return true;
}
}
} 
catch (IOException ignored) {}
return false;
}

private static String getProductNameById(String productId) {
try (BufferedReader reader = new BufferedReader(new FileReader(PRODUCT_FILE))) {
String line;
while ((line = reader.readLine()) != null) {
String[] parts = line.split(",");
if (parts.length == 3 && parts[0].equals(productId)) {
return parts[1];
}
}
} 
catch (IOException ignored) {}
return "Unknown";
}

private static double getProductPriceById(String productId) {
try (BufferedReader reader = new BufferedReader(new FileReader(PRODUCT_FILE))) {
String line;
while ((line = reader.readLine()) != null) {
String[] parts = line.split(",");
if (parts.length == 3 && parts[0].equals(productId)) {
return Double.parseDouble(parts[2]);
}
}
} 
catch (IOException | NumberFormatException ignored) {}
return 0.0;
}

public static void main(String[] args) {
Scanner scanner = new Scanner(System.in);
System.out.print("Enter user username: ");
String username = scanner.nextLine();
showUserMenu(username);
}
}