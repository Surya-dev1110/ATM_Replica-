package ATM_PROJECT;
import java.time.LocalDate;
import java.sql.Date;
import java.util.*;

public class Cashdeposit 
{
    protected static void deposit(String cardnum) throws Exception
    {
            Scanner sc = new Scanner(System.in);
            //Getting Deposit Amount from user
            System.out.print("Enter the amount to Deposit : ");
            long amount = sc.nextLong();
            //Getting ATM pin from user
            System.out.print("Enter your Pin Number : ");
            String pin = sc.next();
            //Getting pin from db
            String dbpin = GetData.getpin(cardnum);
            //Getting today Date 
            Date date = Date.valueOf(LocalDate.now());//This date also work in sql
            java.sql.Date sqldate = new java.sql.Date(date.getTime()); // changing java date to sql date

            //validate pin
            if(pin.contentEquals(dbpin))
            {
                //Getting Avalible balance
                long bal = GetData.getbalance(cardnum);
                long currentBalance = bal+amount;//Adding old balance and deposited amount
                //Update Current balance into DB
                String query1 = "update ATM_DB set Amount ="+currentBalance+" where cardnumber="+cardnum+";";
                //Updating Transaction History into DB
                String query2 = "INSERT INTO Transaction_His VALUES('"+cardnum+"','"+sqldate+"',"+"'"+"Cr "+"',"+amount+");";
                //if Query is executed it will return 1
                int val = GetData.updatebalance(query1,query2);
                    //If Query Executed Successfully
                    if (val>0)
                    {
                        
                        System.out.println("Your Amount Rs."+amount+" Deposited Successfully !!!");
                        //Getting user name & Mail id
                        String username = GetData.getName(cardnum);
                        String mailid = GetData.getMailID(cardnum);
                        //Sending mail to the user
                        Javasendmail.sendMail(mailid,"Amount Deposited Rs."+amount,"Dear "+username+"\n\tyour Deposit of  Rs."+amount+" has been successfully!!! made at the JDBC bank ATM...\n\n\n\n" + 
                                                        "\n" + 
                                                        "To Know more : https://jdbc.tiiny.site/ \n" + 
                                                        "\n" + 
                                                        "\n\nCheers!\nTeam JDBC bank");
                    }
                    else
                    { 
                        System.out.println("Transaction failed");
                    }
            }
            else
            {
                System.out.println("Invalid pin number ");
            }
    }
}
