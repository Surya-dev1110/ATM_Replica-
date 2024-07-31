package ATM_PROJECT;

import java.sql.*;
import java.io.*;
import java.util.Scanner;

public class ATM_Main {
    public static void main(String args[]) throws Exception {
    outer:    
    while(true)
    {  
        String cardnum=null;
        boolean flag=false;
        Scanner sc = new Scanner(System.in);
        InputStreamReader r = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(r);
        System.out.println("\n\n\n");
        System.out.println("\t\t\t\t-----JDBC BANK-----");
        System.out.println("1. Existing ATM card \n2. New Card ");
        System.out.print("Enter your option : ");
        int option = sc.nextInt();
        //Existing user
        if(option==1)
        {
            System.out.print("Enter your card No : ");
            cardnum = br.readLine();

           
            try{ 
                 //Searching user
                String query = "select cardnumber from ATM_DB where cardnumber = '"+cardnum+"' ;";
                ResultSet rs1 = GetData.qry(query);
                rs1.next();
                String cdnum = rs1.getString(1);
                //verifying cardnumber from Database
                if (cdnum.equals(cardnum))
                    flag = true;
            }catch(SQLException e){
                e.printStackTrace();
                System.out.println("Invalid Cardnumber");
            }
           
        }
        //new user
        else if(option==2)
        {
            Newuser.adduser();
           
        }
        else 
        {
            
            System.out.println("Enter correct option");
            continue outer;//goto starting
        }
           
       
        //Displaying Options
        if (flag == true) 
        {
            //Getting user name
            String username = GetData.getName(cardnum);
            System.out.println("Hello " + username);

            System.out.println("\t\t Welcome to JDBC BANK ");
            

            System.out.println("1 - Cash Withdrawal\n2 - Cash Deposit\n3 - Balance Inquiry\n4 - Change Pin\n5 - Statement \n6 - Exit");
            System.out.print("Enter your option : ");
            int opt = sc.nextInt();

            //Switching Operations
            switch (opt) 
            {
                case 1:
                    Cashwithdrawal.withdrawal(cardnum);
                    break;
                case 2:
                    Cashdeposit.deposit(cardnum);
                    break;
                case 3:
                    Balance.inquiry(cardnum);
                    break;
                case 4:
                    Password.change(cardnum);
                    break;
                case 5:
                    AccountStatement.getst(cardnum);
                    break;
                case 6:
                    break;

                default:
                    System.out.println("Enter Correct Option");
            }

        }
       
        
    }
    
    }

}
