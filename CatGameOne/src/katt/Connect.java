package katt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Connect {

	/**
	 * @param args
	 */
//	public static void main(String[] args) {
//		connect();
//	}
	
	public static Connection conn = null;
	
	
	//Metod för att connecta mot en mysql databas. Returns conn som man kan använda för att ställa frågor i utomstående metoder.
	public static Connection connect() {
        conn = null;
        
        	
           //JDBC driver 
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				System.out.println("Driverfel");
				e.printStackTrace();
			}
			
			
			//Adressen till mysql databasen och port(3306)
            String url = "jdbc:mysql://scrum.no-ip.org:3306/scrum";
            try {
				conn = DriverManager.getConnection(url, "scrum", "stiga123");//inlogg och lösen
			} catch (SQLException e) {
				System.out.println("Fel url, inlogg eller lösen");
				e.printStackTrace();
			}
				
			
			try {
				//gör ett statement och skickar iväg. i det här fallet en update. execute update kan även innehålla insert och delete.
				Statement stmt = conn.createStatement();
				stmt.executeUpdate("UPDATE highscore SET telefon='0701231234' WHERE namn='Yahoo'");
				
				//fråga om man vill få ut ett resultat. läggs med fördel i en resultset.
				//stmt.executeQuery("INSERT INTO highscore (namn, highscore, telefon, email) Values ('Christian', 9999999, '0735314549', 'christian.werme@gmail.com')");
			
				//Metod för att skriva ut resultset:
				//while (rs.next()) {
				//	String namn = rs.getString("namn");
				//	String hs = rs.getString("highscore");
				//	System.out.println(namn + " har lyckats få " + hs + " poäng");
				//}
				
				
			} catch (SQLException e) {
				System.out.println("SQLfel");
				e.printStackTrace();
			}
			
       
        
        
        return conn;
    }
	
	
	
	

}
