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
	
	
	//Metod f�r att connecta mot en mysql databas. Returns conn som man kan anv�nda f�r att st�lla fr�gor i utomst�ende metoder.
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
				conn = DriverManager.getConnection(url, "scrum", "stiga123");//inlogg och l�sen
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
			
			try {
				//g�r ett statement och skickar iv�g. i det h�r fallet en update. execute update kan �ven inneh�lla insert och delete.
				Statement stmt = conn.createStatement();
				stmt.executeUpdate("UPDATE highscore SET telefon='0701231234' WHERE namn='Yahoo'");
				
				//fr�ga om man vill f� ut ett resultat. l�ggs med f�rdel i en resultset.
				//stmt.executeQuery(sql)
			
				//Metod f�r att skriva ut resultset:
				//while (rs.next()) {
				//	String namn = rs.getString("namn");
				//	String hs = rs.getString("highscore");
				//	System.out.println(namn + " har lyckats f� " + hs + " po�ng");
				//}
				
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("Fel1");
				e.printStackTrace();
			}
			
       
        
        
        return conn;
    }
	
	
	
	

}
