import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Arrays;

public class GUI extends JFrame {
    DatabaseConnector connector = new DatabaseConnector();
    private JTextField NameField;
    private JTextField pinField;
    private boolean signInSuccess;
    private JButton loginButton;
    private JButton signUpButton;

    public GUI() {
        setTitle("ATM Simulator - Login");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER));

        panel.add(new JLabel("User_Name:"));
        JTextField NameField = new JTextField(10);
        panel.add(NameField);

        panel.add(new JLabel("PIN:"));
        JTextField pinField = new JTextField(10);
        panel.add(pinField);

        loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                try {
                    signInSuccess = Account.CheckAccount(NameField.getText(),pinField.getText());
                    if (signInSuccess){
                        JOptionPane.showMessageDialog(null,"Success");
                    }else{
                        JOptionPane.showMessageDialog(null,"Faild");
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        panel.add(loginButton);

        signUpButton = new JButton("Sign Up");
        signUpButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Handle sign up operation
                JOptionPane.showMessageDialog(null, "Sign Up button clicked");
            }
        });
        panel.add(signUpButton);
        add(panel);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GUI().setVisible(true);
            }
        });
    }
}