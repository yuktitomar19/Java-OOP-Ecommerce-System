package domain;
import java.io.FileWriter;
import java.io.IOException;

public class Customer extends User
{
private String address;
private String email;

public Customer(String username,String password, String address, String email)
{
super(username,password);
this.address=address;
this.email=email;
}

public String getAddress()
{return address;}

public void setAddress(String address)
{this.address= address;}

public String getEmail()
{return email;}

public void setEmail(String email)
{this.email= email;}

public void saveToFile()
{
try(FileWriter writer= new FileWriter("CustomerData.txt",true))
{writer.write("Username: "+getUsername()+",Password: "+getPassword()+",Address: "+address+",Email: "+email+"\n");
System.out.println("Customer data saved!");}
catch(IOException e)
{System.out.println("Error! please ennter correct data");}
}

public void displayInfo()
{super.displayInfo();
System.out.println("Address: "+address);
System.out.println("Email: "+email);}

}