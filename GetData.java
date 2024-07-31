package ATM_PROJECT;

import java.sql.*;

public class GetData 
{

    //Execute SQL query & Returns ResultSet
    protected static ResultSet qry(String query) throws Exception
    {
        Connection con = Atm_DBconnection.getConnection();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(query);
                
        return rs;
    }
    //Returs current Balance
    protected static long getbalance(String cardnum ) throws Exception
    {
        String query = "select Amount from atm_db where cardnumber="+cardnum+";";
        
        ResultSet rs =GetData.qry(query);
        rs.next();
        long amount = rs.getLong(1);
        return amount;
    }
    //Returns ATM Pin
    protected static String getpin(String cardnum ) throws Exception
    {
        String query = "select pinnumber from atm_db where cardnumber="+cardnum+";";
        
        ResultSet rs = GetData.qry(query);
        rs.next();
        String pin = rs.getString(1);
        return pin;
    }
    //Returns User Name
    protected static String getName(String cardnum )throws Exception
    {
        ResultSet rs2 = GetData.qry("SELECT usrname from ATM_DB where cardnumber=" + cardnum + ";");
        rs2.next();
        String username = rs2.getString(1);
        return username;
    }
    //Returns user Mail_id
    protected static String getMailID(String cardnum )throws Exception
    {
        ResultSet rs2 = GetData.qry("SELECT mail_id from ATM_DB where cardnumber=" + cardnum + ";");
        rs2.next();
        String mail_id = rs2.getString(1);
        return mail_id;
    }
    //Update Current Balance
    protected static int updatebalance (String qry1,String qry2) throws Exception
    { 
        
        Connection con = Atm_DBconnection.getConnection();
        Statement st = con.createStatement();
        con.setAutoCommit(false);
        st.addBatch(qry1);
        st.addBatch(qry2);
       
        int[] res = st.executeBatch();

        // using roll back
        for (int i : res) {
            if (i > 0)
                continue;
            else{
                con.rollback();
                return 0;
            }
                
        }
        con.commit();
        con.close();
        return 1;
    }
    //Adding new user to the Database
    protected static int adduser(String cdnum,String pin,String name,long phone,String mailid,long amt ) throws Exception
    {
        
        String query = "insert into ATM_DB values(?,?,?,?,?,?);";
        Connection con = Atm_DBconnection.getConnection();
       
        PreparedStatement pst = con.prepareStatement(query);

        pst.setString(1, cdnum);
        pst.setString(2, pin);
        pst.setString(3, name);
        pst.setLong(4,phone);
        pst.setString(5, mailid);
        pst.setLong(6, amt);
        int val = pst.executeUpdate();

        
        con.close();
        return val;

    }
    
    

}
