package database;

import domain.Product;
import java.io.*;
import java.util.*;

public class ProductDatabase {
private ArrayList<Product> products = new ArrayList<>();
private int nextId = 1;
private final String FILE_NAME = "products.txt";

public ProductDatabase() {
loadProductsFromFile();
}

public void addProduct(String name, double price, int quantity) {
Product product = new Product(nextId++, name, price, quantity);
products.add(product);
saveProductsToFile();
}

public void removeProduct(String name) {
products.removeIf(p -> p.getName().equalsIgnoreCase(name));
saveProductsToFile();
}

public void updateProduct(String name, double newPrice, int newQuantity) {
for (Product p : products) {
if (p.getName().equalsIgnoreCase(name)) {
p.setPrice(newPrice);
p.setQuantity(newQuantity);
break;
}
}
saveProductsToFile();
}

public void displayProducts() {
if (products.isEmpty()) {
System.out.println("No products available.");
return;
}
System.out.println("Product List:");
for (Product p : products) {
System.out.println(" " + p.getId() + ". " + p.getName() + " - ₹" + p.getPrice() + " - Stock: " + p.getQuantity());
}
}

private void saveProductsToFile() {
try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
for (Product p : products) {
writer.write(p.getId() + "," + p.getName() + "," + p.getPrice() + "," + p.getQuantity());
writer.newLine();
}
} 
catch (IOException e) {
System.out.println("Error saving products: " + e.getMessage());
}
}

private void loadProductsFromFile() {
File file = new File(FILE_NAME);
if (!file.exists()) return;

try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
String line;
while ((line = reader.readLine()) != null) {
String[] parts = line.split(",");
int id = Integer.parseInt(parts[0]);
String name = parts[1];
double price = Double.parseDouble(parts[2]);
int quantity = Integer.parseInt(parts[3]);

products.add(new Product(id, name, price, quantity));
nextId = Math.max(nextId, id + 1);
}
} 
catch (IOException e) {
System.out.println("Error loading products: " + e.getMessage());
}
}
}