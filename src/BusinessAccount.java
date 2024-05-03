import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class BusinessAccount extends Account{
    private static String Business_PASSWORD;
    private int Tax_Number;
    private int Employee_ID;
    private static String Employee_Type;
    public BusinessAccount(String name, String passcode, int Blnc,int TaXNumber) throws SQLException {
        super(name, passcode, Blnc);
        this.Tax_Number = TaXNumber;
    }

    public static boolean CheckBusinessAccount(String C_NAME, String C_PIN) throws SQLException {
        Business_PASSWORD = C_PIN;
        String FetchAccountQuery = "select * from project.businessaccounts where Company_Name = (?) and PIN = (?)";
        PreparedStatement preparedStatement = DatabaseConnector.connection.prepareStatement(FetchAccountQuery);
        preparedStatement.setString(1,C_NAME);
        preparedStatement.setString(2,C_PIN);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()){
            System.out.println("Welcome To " + resultSet.getString("Company_Name") + " Company");
            return true;
        }else {
            System.out.println("Log In Faild");
            return false;
        }
    }
    public static boolean CheckBusinessAccess(String u_name, String u_pin) throws SQLException {
        PASSWORD = u_pin;
        int Acc_Id;
        String FetchAccountQuery = "select Account_Id from project.account where Name = (?) and PassCode = (?)";
        PreparedStatement preparedStatement = DatabaseConnector.connection.prepareStatement(FetchAccountQuery);
        preparedStatement.setString(1,u_name);
        preparedStatement.setString(2,u_pin);
        ResultSet resultSet = preparedStatement.executeQuery();

        String FetchAccessFound = "select * from project.company_employees where account_id = (?)";
        PreparedStatement preparedStatement1 = DatabaseConnector.connection.prepareStatement(FetchAccessFound);
        ResultSet resultSet1;
        if (resultSet.next()){
            Acc_Id = resultSet.getInt("Account_Id");
            preparedStatement1.setInt(1,Acc_Id);
            resultSet1 = preparedStatement1.executeQuery();
            if (resultSet1.next()){
                Employee_Type = resultSet1.getString("AccessType");
                System.out.println("Hello "+u_name +" You are a " + resultSet1.getString("AccessType"));
                return true;
            }

            }else {
            System.out.println("Your Account Is Not Found!");
            return false;
        }

        if (resultSet1.next()) {
            return true;
        }
        else {
            return false;
        }
    }

    public static void Business_Deposite(double Amount) throws SQLException {
            String UpdateBalance = "update project.businessaccounts set Balance = (businessaccounts.Balance+?) where PIN = (?)";
            PreparedStatement preparedStatement = DatabaseConnector.connection.prepareStatement(UpdateBalance);
            preparedStatement.setDouble(1, Amount);
            preparedStatement.setString(2, BusinessAccount.Business_PASSWORD);
            int NewBalance = preparedStatement.executeUpdate();
            if (NewBalance > 0) {
                System.out.println("Deposite was Completed successfully!");
                Balance_Query();
            }
    }

    public static void Balance_WithDraw(int Amount) throws SQLException {
        if ((Objects.equals(Employee_Type, "Manager")) || (Objects.equals(Employee_Type, "Accountant"))) {
            String FetchBalance = "select Balance from project.businessaccounts where PIN = (?)";
            PreparedStatement preparedStatement2 = DatabaseConnector.connection.prepareStatement(FetchBalance);
            preparedStatement2.setString(1, BusinessAccount.Business_PASSWORD);
            ResultSet resultSet = preparedStatement2.executeQuery();
            BigDecimal a;
            if (resultSet.next()) {
                a = new BigDecimal(resultSet.getInt("Balance"));
                if (Amount <= a.intValue()) {
                    String UpdateBalance = "update project.businessaccounts set  Balance = (businessaccounts.Balance-?) where PIN = (?)";
                    PreparedStatement preparedStatement = DatabaseConnector.connection.prepareStatement(UpdateBalance);
                    preparedStatement.setInt(1, Amount);
                    preparedStatement.setString(2, BusinessAccount.Business_PASSWORD);
                    int NewBalance = preparedStatement.executeUpdate();
                    if (NewBalance > 0) {
                        System.out.println("WithDraw Was Completed successfully!");
                        Balance_Query();
                    }
                } else {
                    System.out.println("Sorry,Your Balance is Not enough!");
                }
            }
        }
        else {
            System.out.println("sorry, You Don't Have Permissions To Do WithDraw From Account!");
        }
    }
    public static void Balance_Query() throws SQLException {
        if ((Objects.equals(Employee_Type, "Manager")) || (Objects.equals(Employee_Type, "Accountant"))) {

            String QueryBalance = "select Balance from project.businessaccounts where PIN = (?)";
            PreparedStatement preparedStatement = DatabaseConnector.connection.prepareStatement(QueryBalance);
            preparedStatement.setString(1, BusinessAccount.Business_PASSWORD);
            ResultSet NewBalance = preparedStatement.executeQuery();
            if (NewBalance.next()) {
                System.out.println("Company Account Balance Is: " + NewBalance.getBigDecimal("Balance"));
            }
        }
        else {
            System.out.println("sorry, You Don't Have Permissions To Display The Balance!");
        }
    }

    public static void ChangePassword(String Passcode,String NewPasscode) throws SQLException {
        if ((Objects.equals(Employee_Type, "Manager")) || (Objects.equals(Employee_Type, "Accountant"))) {
            if (Objects.equals(Passcode, Business_PASSWORD)) {
                String ChangePINQuery = "update project.account set PassCode = (?)";
                PreparedStatement preparedStatement = DatabaseConnector.connection.prepareStatement(ChangePINQuery);
                preparedStatement.setString(1, NewPasscode);
                int rowsDeleted = preparedStatement.executeUpdate();
                if (rowsDeleted > 0) {
                    System.out.println(" Success! ,Passcode Was Changed ");
                } else {
                    System.out.println(" Faild!, Password Wasn't Changed");
                }
            } else {
                System.out.println("Sorry, CompanyPasscode Is Un correct");
            }
        }else {
            System.out.println("sorry, You Don't Have Permissions for Change Company Password!");
        }
    }
}