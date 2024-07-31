package ATM_PROJECT;

import java.util.*;

import java.sql.*;

public class Password {
    protected static void change(String cardnum) throws Exception {
        Scanner sc = new Scanner(System.in);
        //Creating OTP using Random number
        Random rand = new Random();
        int max = 9999, min = 1000;

        int Sotp = (rand.nextInt(max - min + 1) + min);//Generating 4-Digit OTP 
        //Getting user name & mail id from DB
        String username = GetData.getName(cardnum);
        String mailid = GetData.getMailID(cardnum);
        //Sending OTP through mail
        Javasendmail.sendMail(mailid, "OTP",
                "Dear " + username
                        + "\n\tyour account pin number change requested at the JDBC bank ATM...\n\nYour OTP -" + Sotp
                        + "\nDon't share this otp for anyone..\n\n\n To Know more : https://jdbc.tiiny.site/ \n\n\nRegards \nTeam JDBC bank !");

        System.out.println("The OTP has been sent to your mail id......");
        //Getting OTP from user
        System.out.print("Enter OTP : ");
        int Rotp = sc.nextInt();
        //Validate OTP
        if (Sotp == Rotp) 
        {
            //Getting new pin
            System.out.print("Enter your new pin : ");
            String Npin = sc.next();
            //ReEnter new pin
            System.out.print("Confirm your new pin : ");
            String Cpin = sc.next();
            if (Npin.contentEquals(Cpin)) //if both are correct
            {
                //Update new pin to the Database
                Connection con = Atm_DBconnection.getConnection();
                Statement st = con.createStatement();
                String qry = "update ATM_DB set pinnumber ='" + Cpin + "' where cardnumber=" + cardnum + ";";
                int val = st.executeUpdate(qry);
                if (val > 0) 
                {
                    //Sending confermation mail to the user
                    System.out.println("New Pin updated Successfully....");
                    Javasendmail.sendMail(mailid, "Pin Changed",
                            "Dear " + username + "\n\tyour account pin number has been changed \n\nYour New Pin -"
                                    + Cpin
                                    + "\nDon't share this Pin number for anyone..\n\n\n To Know more : https://jdbc.tiiny.site/"+"\n\n\nRegards \nTeam JDBC bank !");
                } 
                else 
                {
                    System.out.println("Pin Doesn't changed \nTry again later...");
                }
            } 
            else
                System.out.println("Pin Doesn't match \nTry Again....");

        } 
        else 
        {
            System.out.println("Wrong OTP \nCollect your card");
        }

    }
}
