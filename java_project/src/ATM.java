import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ATM {
    DatabaseConnector connector = new DatabaseConnector();
    // [--1-- Check Account Method ]
//    static String PASSWORD;
//    public static boolean CheckAccount(String U_Name, String Passcode) throws SQLException {
//        PASSWORD = Passcode;
//        String FetchAccountQuery = "select * from project.account where Name = (?) and PassCode = (?)";
//        PreparedStatement preparedStatement = DatabaseConnector.connection.prepareStatement(FetchAccountQuery);
//        preparedStatement.setString(1,U_Name);
//        preparedStatement.setString(2,Passcode);
//        ResultSet resultSet = preparedStatement.executeQuery();
//        if (resultSet.next()){
//            System.out.println("Log In Success");
//            return true;
//        }else {
//            System.out.println("Log In Faild");
//            return false;
//        }
//    }
    // [--2-- Deposit Method ]
    public static void Deposite(int Amount) throws SQLException {
        String UpdateBalance = "update project.account set Initial_balance = (account.Initial_balance+?) where PassCode = (?)";
        PreparedStatement preparedStatement = DatabaseConnector.connection.prepareStatement(UpdateBalance);
        preparedStatement.setInt(1,Amount);
        preparedStatement.setString(2,Account.PASSWORD);
        int NewBalance = preparedStatement.executeUpdate();
        if (NewBalance > 0) {
            System.out.println("Deposite was Completed successfully!");
            Balance_Query();
        }
    }
    // [--3-- WithDraw Method ]
    public static void WithDraw(int Amount) throws SQLException {
        String UpdateBalance2 = "update project.account set Initial_balance = (Initial_balance-?) where PassCode = (?)";
        PreparedStatement preparedStatement = DatabaseConnector.connection.prepareStatement(UpdateBalance2);
        preparedStatement.setInt(1,Amount);
        preparedStatement.setString(2,Account.PASSWORD);
        int NewBalance2 = preparedStatement.executeUpdate();
        if (NewBalance2 > 0) {
            System.out.println("WithDraw Was Completed successfully!");
            Balance_Query();
        }
    }

    // [--4-- Transfer Method ]
    public static void Transfer(String AccountName,int Amount) throws SQLException {
        String UpdateBalance =
                "update project.account set Initial_balance = (account.Initial_balance-?) where PassCode = (?);update project.account set Initial_balance = (account.Initial_balance+?) where Name = (?)";
        PreparedStatement preparedStatement = DatabaseConnector.connection.prepareStatement(UpdateBalance);
        preparedStatement.setInt(1,Amount);
        preparedStatement.setString(2,Account.PASSWORD);
        preparedStatement.setInt(1, Amount);
        preparedStatement.setString(2,Account.PASSWORD);
        int NewBalance = preparedStatement.executeUpdate();
        if (NewBalance > 0) {
            System.out.println("Transfer Was Completed successfully!");
        }
    }

    // [--5-- Balance Query Method]
    public static void Balance_Query() throws SQLException {
        String QueryBalance = "select Initial_balance from project.account where PassCode = (?)";
        PreparedStatement preparedStatement = DatabaseConnector.connection.prepareStatement(QueryBalance);
        preparedStatement.setString(1,Account.PASSWORD);
        ResultSet NewBalance = preparedStatement.executeQuery();
        if (NewBalance.next()) {
            System.out.println("Your Balance Is: " + NewBalance.getBigDecimal("Initial_balance"));
        }
    }

}
