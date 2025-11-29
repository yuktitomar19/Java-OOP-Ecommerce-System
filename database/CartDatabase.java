package database;

import domain.Cart;

public class CartDatabase {
private Cart[] carts = new Cart[100];
private int cartCount = 0;

public void addToCart(String customerName, String productName, int quantity) {
if (cartCount < carts.length) {
carts[cartCount++] = new Cart(customerName, productName, quantity);
System.out.println("Added to cart: " + productName + " (x" + quantity + ") for " + customerName);
} else {
System.out.println("Cart is full.");
}
}

public void viewCart(String customerName) {
System.out.println(customerName + "'s Cart:");
for (int i = 0; i < cartCount; i++) {
if (carts[i] != null && carts[i].getCustomerName().equals(customerName)) {
System.out.println(carts[i].getProductName() + " - Quantity: " + carts[i].getQuantity());
}
}
}
}
