import java.sql.*;
import java.util.*;
public class Main {
    public static void main(String[] args) throws SQLException {
        // [--1--] Connected To The DataBase.
        DatabaseConnector connector = new DatabaseConnector();
        connector.getConnection();
        //  ======================== [Connecting Done] ============================
        Scanner input = new Scanner(System.in);
        System.out.println("Welcome To ATM  ");
            SignIn();
    }
    
    //{  (--- 1 ---)  ATM Methods To Implements the ATM Project
    public static void SignIn() throws SQLException {
        Scanner input = new Scanner(System.in);
        boolean valid;
        valid = false;
        int kind;
        boolean FetchCompany;
        boolean FetchCompanyAccess;
            do {
                System.out.println("(1--) Log In (2--) Sign Up ");
                System.out.print("Log in To Your Account Or Create One :- ");
                int s = input.nextInt();
                if (s == 1) {
                    valid = true;
                    boolean again = true;
                    boolean FetchResult = false;
                    int count = 3;
                    System.out.println("(1-- ) Personal_Account  (2--) Business_Account");
                    kind = input.nextInt();
                    if (kind == 1){
                        do {
                            if (count == 0) {
                                System.out.println("Try Again Later");
                                break;
                            }
                            System.out.print("Enter Your UserName:- ");
                            String U_Name = input.next();
                            System.out.print("Enter Your Password:- ");
                            String Pass = input.next();
                            FetchResult = Account.CheckAccount(U_Name, Pass);
                            count--;
                            if (FetchResult) {
                                again = false;
                            }
                        } while (again);
                        if (FetchResult) {
                            if (count >= 0) {
                                Operations();
                            }
                        }
                    }
                    else if(kind == 2) {
                        System.out.println("Enter The Company Name: ");
                        String C_name=input.next();
                        System.out.println("Enter The Password: ");
                        String C_PIN=input.next();
                        FetchCompany =  BusinessAccount.CheckBusinessAccount(C_name,C_PIN);
                        if (FetchCompany){
                            System.out.println("Who Are You ? ");
                            System.out.println("Your User_Name: ");
                            String u_name = input.next();
                            System.out.println("Your Pin Code? ");
                            String pin = input.next();
                            FetchCompanyAccess = BusinessAccount.CheckBusinessAccess(u_name,pin);
                            if (FetchCompanyAccess){
                                Business_Operations();
                            }
                        }else {
                            System.out.println("you Don't Not Have Any access");
                        }
                    }else {
                        System.out.println("rong Choose");
                    }
                } else if (s == 2) {
                    valid = true;
                    Creation();
                } else {
                    System.out.println("Invalid Option");
                    valid = false;
                }
            } while (!valid) ;
        }

    public static void Creation() throws SQLException {
        Scanner input = new Scanner(System.in);
        System.out.println("Create Account... ");
        System.out.print("PLease Enter Your User_Name: ");
        String NAME = input.next();
        System.out.print("PLease Create A Password ['4 Numbers']: ");
        String PASSCODE = input.next();
        System.out.print("Give Me Your Initial Balance : ");
        int INITIAL_BALANCE = input.nextInt();
        new Account(NAME, PASSCODE, INITIAL_BALANCE);
        if (Account.CheckAccount(NAME,PASSCODE)){
            Operations();
        }
    }
    public static void Operations() throws SQLException {
        boolean OtherOperation;
        Scanner input  = new Scanner(System.in);
        do {
            System.out.println("What Do You Want ? ");
            System.out.println("(1) -- Deposit (2) -- withdraw \n(3) -- Transfer(4) -- Balance InQuery\n(5) -- Settings");
            int Choosen = input.nextInt();
            if (Choosen == 1){
                System.out.print("Enter The Deposite Value? ");
                int DepositeValue = input.nextInt();
                ATM.Deposite(DepositeValue);
            } else if (Choosen == 2) {
                System.out.println("Enter The WithDraw Value?");
                int WithDraw_Value = input.nextInt();
                ATM.WithDraw(WithDraw_Value);
            }else if (Choosen == 3) {
                System.out.print("PLease Enter Your Transfer Target User_Name: ? ");
                String TransferTarget = input.next();
                System.out.print("PLease Enter Your Transfer Amount: ? ");
                int TransferAmount = input.nextInt();
                ATM.Transfer(TransferTarget,TransferAmount);
            }
            else if (Choosen == 4){
                try {
                    ATM.Balance_Query();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            else if (Choosen == 5) {
                System.out.println("(1)-- Delete Account  (2)-- Change Your PIN");
                int SettingOption = input.nextInt();
                if (SettingOption == 1){
                    System.out.print("Confirm Your Password Please?  ");
                    String ConfirmPass = input.next();
                    Account.Delete(ConfirmPass);
                    SignIn();
                } else if (SettingOption == 2) {
                    System.out.print("Confirm Your Password Please?  ");
                    String ConfirmPass = input.next();
                    System.out.print("Enter New Password? ");
                    String NewPass = input.next();
                    Account.ChangePassword(ConfirmPass,NewPass);
                }
            }
            else{
                System.out.println("Invalid Option!");
            }
            System.out.println("------ ### ------");
            System.out.println("(true)-- New_Operation  (false)-- Exit");
            OtherOperation = input.nextBoolean();
        } while (OtherOperation);
    }

    public static void Business_Operations() throws SQLException {
        boolean OtherOperation = false;
        Scanner input = new Scanner(System.in);
        do {
            System.out.println("What Do You Want ? ");
            System.out.println("(1) -- Deposit (2) -- withdraw \n(3) -- Balance InQuery\n(4) -- Change PassWord");
            int Choosen = input.nextInt();
            if (Choosen == 1) {
                System.out.print("Enter The Deposite Value? ");
                int DepositeValue = input.nextInt();
                BusinessAccount.Business_Deposite(DepositeValue);
            } else if (Choosen == 2) {
                System.out.println("Enter The WithDraw Value?");
                int WithDraw_Value = input.nextInt();
                BusinessAccount.Balance_WithDraw(WithDraw_Value);
            } else if (Choosen == 3) {
                try {
                    BusinessAccount.Balance_Query();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }else if (Choosen == 4) {
                System.out.print("Confirm Your Password Please?  ");
                String ConfirmPass = input.next();
                System.out.print("Enter New Password? ");
                String NewPass = input.next();
                BusinessAccount.ChangePassword(ConfirmPass, NewPass);
            }else {
                System.out.println("Invalid Option");
            }
            System.out.println("------ ### ------");
            System.out.println("(true)-- New_Operation  (false)-- Exit");
            OtherOperation = input.nextBoolean();
        } while (OtherOperation) ;
    }
    //      End Of The ATM Methods.

    //{  (--- 2 ---)  QuizApplication Methods To Implements the QUIZ Project
    //          End Of The Quiz App
}