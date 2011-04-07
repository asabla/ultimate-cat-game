package katt;

import java.sql.*;

public class Connect {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		connect();
	}
	
	public static Connection conn = null;
	
	
	//Metod för att connecta mot en mysql databas. Returns conn som man kan använda för att ställa frågor i utomstående metoder.
	public static Connection connect() {
        conn = null;
        
        	
           //JDBC driver 
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			//Adressen till mysql databasen och port(3306)
            String url = "jdbc:mysql://scrum.no-ip.org:3306/scrum";
            try {
				conn = DriverManager.getConnection(url, "scrum", "stiga123");//inlogg och lösen
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
			
			try {
				//gör ett statement och skickar iväg. i det här fallet en update. execute update kan även innehålla insert och delete.
				Statement stmt = conn.createStatement();
				stmt.executeUpdate("UPDATE highscore SET telefon='0701231234' WHERE namn='Yahoo'");
				
				//fråga om man vill få ut ett resultat. läggs med fördel i en resultset.
				//stmt.executeQuery(sql)
			
				//Metod för att skriva ut resultset:
				//while (rs.next()) {
				//	String namn = rs.getString("namn");
				//	String hs = rs.getString("highscore");
				//	System.out.println(namn + " har lyckats få " + hs + " poäng");
				//}
				
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("Fel1");
				e.printStackTrace();
			}
			
       
        
        
        return conn;
    }
	
	
	
	

}
