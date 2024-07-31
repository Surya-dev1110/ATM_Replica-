package ATM_PROJECT;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Atm_DBconnection
{
	//Declaring URL,UserName,Password
    private static final String url = "jdbc:mysql://localhost:3306/atm_project";
	private static final String userName = "root";
	private static final String passWord = "surya@123";
	
	//Creating connection
	public static Connection getConnection() throws SQLException{
		return  DriverManager.getConnection(url,userName,passWord);
	}
}
