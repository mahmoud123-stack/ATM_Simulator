import java.sql.*;
import java.util.*;
public class Main {
    public static void main(String[] args) throws SQLException {
        // [--1--] Connected To The DataBase.
        DatabaseConnector connector = new DatabaseConnector();
        connector.getConnection();
        //  ======================== [Connecting Done] ============================
        Scanner input = new Scanner(System.in);
        System.out.print("Start ATM ? ");
        int i = input.nextInt();
        if (i == 1) {
//            System.out.println("Welcome, PLease Sign In ! ");
            System.out.println("(1--) Log In (2--) Sign Up ");
            System.out.print("Log in To Your Account Or Create One :- ");
            int s = input.nextInt();
            if (s == 1) {
                boolean again = true;
                boolean FetchResult;
                int count = 3;
                do {
                    System.out.print("Enter Your UserName:- ");
                    String U_Name = input.next();
                    System.out.print("Enter Your Password:- ");
                    String Pass = input.next();
                    FetchResult = Account.CheckAccount(U_Name, Pass);
                    count--;
                    if (FetchResult) {
                        again = false;
                    }
                    ;
                    if (count == 0) {
                        System.out.println("Try Again Later");
                        break;
                    }
                    ;
                } while (again);
                if (FetchResult) {
                    LogIn();
                }
            } else {
                System.out.println("Create Account... ");
                System.out.print("PLease Enter Your User_Name: ");
                String NAME = input.next();
                System.out.print("PLease Create A Password ['4 Numbers']: ");
                String PASSCODE = input.next();
                System.out.print("Give Me Your Initial Balance : ");
                int INITIAL_BALANCE = input.nextInt();
//                new Account(NAME,PASSCODE,INITIAL_BALANCE);
                Account.Add(NAME, PASSCODE, INITIAL_BALANCE);
                LogIn();
            }
        }
    }

    public static void LogIn() throws SQLException {
            boolean OtherOperation;
            Scanner input  = new Scanner(System.in);
            do {
                System.out.println("What Do You Want ? ");
                System.out.println("(1) -- Deposit (2) -- withdraw \n(3) -- Transfer(4) -- Balance Query\n(5) -- Delete Account");
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
                    System.out.print("Confirm Your Password Please?  ");
                    String ConfirmPass = input.next();
                    Account.Delete(ConfirmPass);
                }
                System.out.println("------ ### ------");
                System.out.println("(true)-- New_Operation  (false)-- Exit");
                OtherOperation = input.nextBoolean();
            } while (OtherOperation);
        }

    }
