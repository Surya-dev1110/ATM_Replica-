package ATM_PROJECT;
import java.util.*;
public class Balance 
{
    protected static void inquiry(String cardnum) throws Exception
    {
        Scanner sc = new Scanner(System.in);
        //getting pin from user
        System.out.print("Enter your pin number : ");
        String pin = sc.next();
        sc.close();
        //getting pin from Database
        String dbpin = GetData.getpin(cardnum);
        
        //Validate pin
        if(pin.contentEquals(dbpin))
        {
            //Displaying Current Balance
            long bal = GetData.getbalance(cardnum);
            System.out.println("Your Current Balance : "+bal);
            //fetching user name from db
            String username = GetData.getName(cardnum);
            //fetching mail_id from db
            String mailid = GetData.getMailID(cardnum);
            //Sending mail to the user
            Javasendmail.sendMail(mailid,"Balance Inquiry","Dear "+username+"\n\tyour Current Balance Rs."+bal+" \n\tHave a nice day!!!\n\nTo Know more : https://jdbc.tiiny.site/ \n\n\n\n\n\nCheers!\nTeam JDBC bank");
        }
        else
        {
            System.out.println("Invalid pin Number");
        }
    }
}
