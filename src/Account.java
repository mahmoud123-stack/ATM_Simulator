import java.sql.*;
import java.util.Objects;

public class Account {
    public static String PASSWORD;
    DatabaseConnector connector = new DatabaseConnector();
    // Main Properites;
    static int  INitialID = 0;
    protected String PIN;
    protected String U_NAME;
    protected static double Balance;
    // == == == == ==

    public Account (String name,String passcode,double Blnc) throws SQLException {
        U_NAME = name;
        PIN = passcode;
        Balance = Blnc;
        Add(name,passcode,Blnc);
    }

    // [--1-- Create Check Account Method]
    public static boolean CheckAccount(String U_Name, String Passcode) throws SQLException {
        PASSWORD = Passcode;
        String FetchAccountQuery = "select * from project.account where Name = (?) and PassCode = (?)";
        PreparedStatement preparedStatement = DatabaseConnector.connection.prepareStatement(FetchAccountQuery);
        preparedStatement.setString(1,U_Name);
        preparedStatement.setString(2,Passcode);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()){
            System.out.println("Welcome " + resultSet.getString("Name"));
            return true;
        }else {
            System.out.println("Log In Faild");
            return false;
        }
    }

    // [--1-- Create Add Account Method]
    public static void Add(String Name, String Pass, double Balance) throws SQLException {
        String insertQuery = "insert into project.account values (?,?,?,?)";
        PreparedStatement preparedStatement = DatabaseConnector.connection.prepareStatement(insertQuery);
        preparedStatement.setInt(1,INitialID);
        preparedStatement.setString(2,Name);
        preparedStatement.setString(3,Pass);
        preparedStatement.setDouble(4,Balance);
        int rowsInserted = preparedStatement.executeUpdate();
        if (rowsInserted > 0) {
            INitialID++;
            System.out.println("Account was Created successfully!");
        }
    }


    // [--2-- Create Delete Account Method]
    public static void Delete(String Passcode) throws SQLException {
        String DeleteQuery = "Delete from project.Account where PassCode =(?)";
        PreparedStatement preparedStatement = DatabaseConnector.connection.prepareStatement(DeleteQuery);
        preparedStatement.setString(1,Passcode);
        int rowsDeleted = preparedStatement.executeUpdate();
        if (rowsDeleted > 0) {
            System.out.println("Account was Deleted successfully!");
        }
    }


    // [--3-- Create Change Passcode Method]
    public static void ChangePassword(String Passcode,String NewPasscode) throws SQLException {
        if (Objects.equals(Passcode, PASSWORD)){
            String ChangePINQuery = "update project.account set PassCode = (?)";
            PreparedStatement preparedStatement = DatabaseConnector.connection.prepareStatement(ChangePINQuery);
            preparedStatement.setString(1,NewPasscode);
            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println(" Success! ,Passcode Was Changed ");
            }
            else {
                System.out.println(" Faild!, Password Wasn't Changed");
            }
        }else {
            System.out.println("Sorry, Your Password Is Uncorrect");
        }
    }



}