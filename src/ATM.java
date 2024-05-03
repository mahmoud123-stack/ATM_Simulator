import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class ATM {
    private static  String PASSWORD;
    DatabaseConnector connector = new DatabaseConnector();

    // [--1-- Deposit Method ]
    public static void Deposite(double Amount) throws SQLException {
        String UpdateBalance = "update project.account set Initial_balance = (account.Initial_balance+?) where PassCode = (?)";
        PreparedStatement preparedStatement = DatabaseConnector.connection.prepareStatement(UpdateBalance);
        preparedStatement.setDouble(1,Amount);
        preparedStatement.setString(2,Account.PASSWORD);
        int NewBalance = preparedStatement.executeUpdate();
        if (NewBalance > 0) {
            System.out.println("Deposite was Completed successfully!");
            Balance_Query();
        }
    }
    // [--2-- WithDraw Method ]
    public static void WithDraw(int Amount) throws SQLException {
        String FetchBalance = "select Initial_balance from project.account where PassCode = (?)";
        PreparedStatement preparedStatement2 = DatabaseConnector.connection.prepareStatement(FetchBalance);
        preparedStatement2.setString(1,Account.PASSWORD);
        ResultSet resultSet = preparedStatement2.executeQuery();
        BigDecimal a ;
        if (resultSet.next()){
            a = new BigDecimal(resultSet.getDouble("Initial_balance"));
            if (Amount <= a.intValue()){
                String UpdateBalance = "update project.account set Initial_balance = (Initial_balance-?) where PassCode = (?)";
                PreparedStatement preparedStatement = DatabaseConnector.connection.prepareStatement(UpdateBalance);
                preparedStatement.setInt(1, Amount);
                preparedStatement.setString(2,Account.PASSWORD);
                int NewBalance = preparedStatement.executeUpdate();
                if (NewBalance > 0) {
                    System.out.println("WithDraw Was Completed successfully!");
                    Balance_Query();
                }
            }else{
                System.out.println("Sorry,Your Balance is Not enough!");
            }
        }
    }

    // [--3-- Transfer Method ]
    public static void Transfer(String AccountName,int Amount) throws SQLException {
        // Sender Settings
        String UpdateSenderBalance =
                "update project.account set Initial_balance = (account.Initial_balance-?) where PassCode = (?);";
        PreparedStatement preparedStatement = DatabaseConnector.connection.prepareStatement(UpdateSenderBalance);
        preparedStatement.setInt(1,Amount);
        preparedStatement.setString(2,Account.PASSWORD);
        int NewSenderBalance = preparedStatement.executeUpdate();
        // ======= =======

        // Reciever Settings
        String UpdateRecieverBalance = "update project.account set Initial_balance = (account.Initial_balance+?) where Name = (?)";

        PreparedStatement preparedStatement2 = DatabaseConnector.connection.prepareStatement(UpdateRecieverBalance);
        preparedStatement2.setInt(1, Amount);
        preparedStatement2.setString(2,AccountName);
        int NewRecieverBalance = preparedStatement2.executeUpdate();
        if (NewSenderBalance > 0) {
            if (NewRecieverBalance>0){
            System.out.println("Transfer Was Completed successfully!");
                Balance_Query();
            }else {
                System.out.println("Receiver Account is Not Found!");
            }
        }else{
            System.out.println("Wrong Transfer!!");
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
