package ATM_PROJECT;

import java.sql.*;
import java.util.*;

public class AccountStatement 
{
    protected static void getst(String cardnum)throws Exception
    {
        Scanner sc = new Scanner(System.in);
        //Getting pin from user
        System.out.print("Enter your pin number : ");
        String pin = sc.next();
        //Getting pin from db
        String dbpin = GetData.getpin(cardnum);

        //Getting user name & mail id
        String username = GetData.getName(cardnum);
        String mailid = GetData.getMailID(cardnum);

        //Displaying option
        System.out.println("1. Full Statement\n2. Specific Statement ");
        System.out.print("Enter your option : ");
        int opt = sc.nextInt();
        if(opt==1)//Full Statement
        {
            //Validate pin
            if(pin.contentEquals(dbpin))
            {
                //Getting Current balance from db
                long bal = GetData.getbalance(cardnum);
                //Getting Transaction history from db
                ResultSet rs=GetData.qry("select * from Transaction_His where cardnumber="+cardnum+" ORDER BY date_of_tran DESC "+";");
               
                System.out.println("Your account Statement has sent to your mail");
                //Generating Statement PDF
                StatementPDF.createPDF(rs,username,cardnum,mailid,bal);
                
            }
            else
                System.out.println("Wrong pin number");
        }
        else if(opt==2) //Specific statement between two dates
        {
            //Getting FROM date from the user
            System.out.print("Enter FROM date yyyy-mm-dd : ");
            String Fdate = sc.next();
           //Getting TO date from the user
            System.out.print("Enter TO date yyyy-mm-dd :");
            String Tdate = sc.next();
           
            //Validate pin
            if(pin.contentEquals(dbpin))
            {
                //Getting current balance from db
                long bal = GetData.getbalance(cardnum);
                //Getting transaction history from the db
                ResultSet rs=GetData.qry("select * from Transaction_His where cardnumber= '"+cardnum+"' AND "+"date_of_tran between '"+Fdate+"' AND '"+Tdate+"' ;");
                
                System.out.println("Your account Statement has sent to your mail");
                //Generating Statement PDF
                StatementPDF.createPDF(rs,username,cardnum,mailid,bal);
                
            }
            else
                System.out.println("Wrong pin number");
        }
        else
        {
            System.out.println("Enter the correct option");
        }
    
    }


    
}
