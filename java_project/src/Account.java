import java.sql.*;
import java.util.Scanner;

public class Account {
//    static boolean FetchResult;
    public static String PASSWORD;
    static int  INitialID = 0;
    DatabaseConnector connector = new DatabaseConnector();
    // [--1-- Create Check Account Method]
    public static boolean CheckAccount(String U_Name, String Passcode) throws SQLException {
        PASSWORD = Passcode;
        String FetchAccountQuery = "select * from project.account where Name = (?) and PassCode = (?)";
        PreparedStatement preparedStatement = DatabaseConnector.connection.prepareStatement(FetchAccountQuery);
        preparedStatement.setString(1,U_Name);
        preparedStatement.setString(2,Passcode);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()){
            System.out.println("Log In Success");
            return true;
        }else {
            System.out.println("Log In Faild");
            return false;
        }
    }

    // [--1-- Create Add Account Method]
    public static void Add(String Name, String Pass, int Balance) throws SQLException {
        String insertQuery = "insert into project.account values (?,?,?,?)";
        PreparedStatement preparedStatement = DatabaseConnector.connection.prepareStatement(insertQuery);
        preparedStatement.setInt(1,INitialID);
        preparedStatement.setString(2,Name);
        preparedStatement.setString(3,Pass);
        preparedStatement.setInt(4,Balance);
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
        String DeleteQuery = "select * from project.account where PassCode =(?)";
        PreparedStatement preparedStatement = DatabaseConnector.connection.prepareStatement(DeleteQuery);
        preparedStatement.setString(1,Passcode);
        int rowsDeleted = preparedStatement.executeUpdate();
        if (rowsDeleted > 0) {
            System.out.println("Account was Deleted successfully!");
        }
    }


}