import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class GUI{
    DatabaseConnector connector = new DatabaseConnector();
    static JFrame Frame1;
    static JFrame Frame2;
    private static JTextField NameField;
    private static JTextField pinField;
    private static boolean signInSuccess;
    private static JButton loginButton;
    private static JButton signUpButton;

    static ActionListener Action = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == loginButton){
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

            if (e.getSource() == signUpButton){
                Frame1.setVisible(false);
                Frame2.setVisible(true);
            }
        }
    };

    public static void Frame1(){
        JFrame Frame1 = new JFrame();
        Frame1.setTitle("ATM Simulator - Login");
        Frame1.setSize(400, 200);
        Frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Frame1.setLocationRelativeTo(null);
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER));

        panel.add(new JLabel("User_Name:"));
        JTextField NameField = new JTextField(10);
        panel.add(NameField);

        panel.add(new JLabel("PIN:"));
        JTextField pinField = new JTextField(10);
        panel.add(pinField);

        loginButton = new JButton("Login");
        loginButton.addActionListener(Action);
        panel.add(loginButton);

        signUpButton = new JButton("Sign Up");
        signUpButton.addActionListener(Action);
        panel.add(signUpButton);
        Frame1.add(panel);
        Frame1.setVisible(true);
    }








    public static void GUI() {
        Frame1 = new JFrame();
        Frame1.setTitle("ATM Simulator - Login");
        Frame1.setSize(400, 200);
        Frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Frame1.setLocationRelativeTo(null);
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER));

        panel.add(new JLabel("User_Name:"));
        JTextField NameField = new JTextField(10);
        panel.add(NameField);

        panel.add(new JLabel("PIN:"));
        JTextField pinField = new JTextField(10);
        panel.add(pinField);

        loginButton = new JButton("Login");
        loginButton.addActionListener(Action);
        panel.add(loginButton);

        signUpButton = new JButton("Sign Up");
        signUpButton.addActionListener(Action);
        panel.add(signUpButton);
        Frame1.add(panel);
        Frame1.setVisible(true);
    }
    public static void main(String[] args) {
        GUI();
    }
}