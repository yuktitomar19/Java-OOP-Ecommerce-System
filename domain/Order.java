package domain;

public class Order {
private int orderId;
private String customerName;
private double totalAmount;
private String status;

public Order(int orderId, String customerName, double totalAmount, String status) {
this.orderId = orderId;
this.customerName = customerName;
this.totalAmount = totalAmount;
this.status = status;
}

public int getOrderId() {
return orderId;
}

public String getCustomerName() {
return customerName;
}

public double getTotalAmount() {
return totalAmount;
}

public String getStatus() {
return status;
}
}