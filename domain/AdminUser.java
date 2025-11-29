package domain;
public class AdminUser extends User 
{
public AdminUser(String username, String password) 
{super(username, password);}
public void manageProducts() 
{System.out.println("Admin can manage products.");}
public void displayInfo() {
super.displayInfo();
System.out.println("Admin Access: Yes");
}
}