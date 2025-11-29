package domain;

public class Cart {
private String customerName;
private String productName;
private int quantity;

public Cart(String customerName, String productName, int quantity) {
this.customerName = customerName;
this.productName = productName;
this.quantity = quantity;
}

public String getCustomerName() {
return customerName;
}
public String getProductName() {
return productName;
}
public int getQuantity() {
return quantity;
}
}