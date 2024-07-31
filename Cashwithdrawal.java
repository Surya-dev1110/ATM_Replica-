package ATM_PROJECT;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public class Cashwithdrawal 
{
    protected static void withdrawal(String cardnum) throws Exception
    {
            Scanner sc = new Scanner(System.in);
            //Getting withdrawal amount from user
            System.out.print("Enter the Amount to withdraw : ");
            long amount = sc.nextLong();
            //Getting pin from user
            System.out.print("Enter your pin number : ");
            String pin = sc.next();
            //Getting pin from Database
            String dbpin = GetData.getpin(cardnum);
            //Getting today Date 
            Date date = Date.valueOf(LocalDate.now());
            java.sql.Date sqldate = new java.sql.Date(date.getTime());// changing java date to sql date

            //Validate pin 
            if(pin.contentEquals(dbpin))
            {
                //Getting user name & Mail id
                String username = GetData.getName(cardnum);
                String mailid = GetData.getMailID(cardnum);

                //Getting Avalible balance
                long bal = GetData.getbalance(cardnum);
                //Insufficient balance
                if(amount > bal)
                {

                    System.out.println("Insufficient Bank Balance\n Your Bank balance is "+bal);
                    
                    //Sending mail to the user
                    Javasendmail.sendMail(mailid,"Insufficient Bank Balance","Dear "+username+"\n\tYour Current Bank balance is RS."+bal+"\n\n\n\n To Know more : https://jdbc.tiiny.site/ \n\n\nTeam JDBC bank");

                }
                //Amount avalible
                else
                {
                    //Updating current balance into database & update transaction history
                    long currentBalance = bal-amount; 
                    String query1 = "update ATM_DB set Amount ="+currentBalance+" where cardnumber="+cardnum+";";
                    String query2 = "INSERT INTO Transaction_His VALUES('"+cardnum+"','"+sqldate+"',"+"'"+"Dr"+"',"+amount+");";
                    //if Query is executed it will return 1
                    int val = GetData.updatebalance(query1,query2);
                    
                    
                    //If Query Executed Successfully
                    if (val>0)
                    {
                        System.out.println("Take your Cash!!!");
                        //Sending mail to the user
                        Javasendmail.sendMail(mailid,"Amount Debited Rs."+amount,"Dear "+username+"\n\tyour amount of Rs."+amount+" has been successfully!!! Debited from JDBC bank ATM...\n\n\n To Know more : https://jdbc.tiiny.site/ \n\n\nCheers!\nTeam JDBC bank");
                    }
                    else 
                    {
                        System.out.println("transaction failed");//Query didn't executed successfully
                    }
                    
                }
            }
            else
            {
                System.out.println("Invalid pin number ");
            }

    }
  
}
