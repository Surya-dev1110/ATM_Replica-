package ATM_PROJECT;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Newuser {
    protected static void adduser() throws Exception
    {
        Scanner sc = new Scanner(System.in);
        InputStreamReader r = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(r);
        //Declaring User Credentials
        String nmailid = null;
        String cdnum = null;
        String pin = null;
        String name = null;
        long phone = 0;
        //Getting mail id from user
        System.out.print("Enter your Mail Id : ");
        nmailid = sc.nextLine();
        //crearing OTP using random number
        Random rand = new Random();
        int max = 9999, min = 1000;

        int Sotp = (rand.nextInt(max - min + 1) + min);//Generating 4-Digit OTP 
        //Sending OTP through mail
        Javasendmail.sendMail(nmailid, "OTP",
                "Dear New User\n\t you have requested for new ATM card registaion at JDBC bank ATM...\n\nYour OTP -"
                        + Sotp + "\nDon't share this otp for anyone..\n\n\n To Know more : https://jdbc.tiiny.site/ \n\n\nRegards \nTeam JDBC bank !");

        System.out.println("The OTP has been sent to your mail id......");
        //Getting OTP from user
        System.out.print("Enter OTP : ");
        int Rotp = sc.nextInt();
        //Validate OTP
        if (Sotp==Rotp) 
        {
            //Getting User Credentials from user
            System.out.print("Enter your New Cardnumber : ");
            cdnum = sc.next();
            System.out.println();
            System.out.print("Enter your Name : ");
            name = br.readLine();
            name = name.toUpperCase();
            System.out.print("Enter your New Pin number : ");
            pin = sc.next();
            System.out.print("Enter your Phone number : ");
            phone = sc.nextLong();
            if(cdnum!=null && name!=null && pin!=null && phone!=0)//if all entered correctly
            {
                //Adding new user to the DB
                int val=GetData.adduser(cdnum,pin,name,phone,nmailid,0);
                //If new user added successfully , sending confermation mail to the user 
                if(val>=1)
                {
                    System.out.println("new user created successfully....");
                    Javasendmail.sendMail(nmailid, "New Registration",
                    "Dear "+name+"\n\t you have successefully registered new ATM card at JDBC bank ATM..."
                           +"\n\n\n To Know more : https://jdbc.tiiny.site/ \n\n\nRegards \nTeam JDBC bank !");
    
               

                }
                    
            }
           

        }
        else
        {
            System.out.println("Invalid OTP");
        }

    }
}
