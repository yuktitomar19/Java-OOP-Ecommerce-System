package ui;

import javax.swing.*;
import java.awt.event.*;
import services.Main;

public class LoginSignupGUI {
private JFrame frame;
private JTextField usernameField;
private JPasswordField passwordField;
private JButton loginButton, signupButton;

public LoginSignupGUI() {
frame = new JFrame("Login/Signup");
frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
frame.setSize(400, 300);
frame.setLayout(null);

JLabel usernameLabel = new JLabel("Username:");
usernameLabel.setBounds(50, 50, 100, 25);
usernameField = new JTextField();
usernameField.setBounds(150, 50, 150, 25);

JLabel passwordLabel = new JLabel("Password:");
passwordLabel.setBounds(50, 100, 100, 25);
passwordField = new JPasswordField();
passwordField.setBounds(150, 100, 150, 25);

loginButton = new JButton("Login");
loginButton.setBounds(50, 150, 100, 25);
loginButton.addActionListener(new ActionListener() {
public void actionPerformed(ActionEvent e) {
String username = usernameField.getText();
String password = new String(passwordField.getPassword());
                
Main.handleLogin(username, password);
frame.dispose(); 
}
});

signupButton = new JButton("Signup");
signupButton.setBounds(200, 150, 100, 25);
signupButton.addActionListener(new ActionListener() {
public void actionPerformed(ActionEvent e) {
String username = usernameField.getText();
String password = new String(passwordField.getPassword());

String role = JOptionPane.showInputDialog(frame, "Enter role (admin/user):");
Main.signup(username, password, role);
}
});

frame.add(usernameLabel);
frame.add(usernameField);
frame.add(passwordLabel);
frame.add(passwordField);
frame.add(loginButton);
frame.add(signupButton);


frame.setVisible(true);
}
}
