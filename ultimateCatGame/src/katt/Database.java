package katt;

import java.sql.*;

public class Database
{

    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultset = null;
    private String url = "jdbc:mysql://193.168.0.13:scrum.no-ip.org:3306/";
    private String db = "scrum";
    private String driver = "com.mysql.jdbc.Driver";
    private String user = "scrum";
    private String pass = "stiga123";
    //Vad h�nder h�r?
    public void sendHighscore(String name, int highscore, String email, String telefon)
    {   
        try
        {
            connectToDB();  //Ansluter till servern
            boolean btemp = getValue("email", email); //Kontrollerar om email-adressen finns sen tidigare
            System.out.println("V�rdet f�r mailen �r: " + btemp);
            String stemp = getUpdateString(btemp, name, highscore, email, telefon);
            System.out.println(stemp);
            
            if(stemp.equals("No"))
            {
                System.out.println("Ditt highscore �r f�r litet f�r att l�gga till");
            }
            else
            {
                System.out.println("Skickar v�rden till servern");
                statement = connect.createStatement(); //Ser till att drivern hamnar i createstatement
                int val = statement.executeUpdate(stemp); //Den faktiska anropet som uppdaterar databasen
                System.out.println("Du har nu skickat in ditt highscore!");
            }
            close();  //St�nger ner anslutningen
        }
        catch (SQLException e)
        {
            System.out.println("Det gick inte att skicka in highsore\nErrorcode: 1001\n" + e);
        }
    }
  //Vad h�nder h�r?
    private String getUpdateString(boolean update, String name, int highscore, String email, String telefon)
    {
        String s_return = null;

        if (update == false)
        {
            System.out.println("F�rs�ker l�gga till i databasen");
            s_return = "INSERT INTO highscore (namn, highscore, email, telefon) "
                    + "VALUES('" + name + "', " + highscore + ", '" + email + "', '" + telefon + "')";
        }
        else
        {
            //L�gg till funktion som kollar om highscore v�rdet �r l�gre eller h�gre �n tidigare
            boolean btemp = checkResult("email", email, "highscore", highscore);
            if(btemp == true)
            {
                System.out.println("Highscore f�rlitet");
                s_return = "No";
            }
            else
            {
                System.out.println("F�rs�ker uppdatera highscore");  //Fels�kningsstr�ng
                s_return = "UPDATE highscore SET highscore=" + highscore + " WHERE email='" + email + "'";
            }
        }

        return s_return;  //Returnerar r�tt str�ng, beroende p� om den skall uppdateras eller l�ggas till
    }
  //Vad h�nder h�r?
    private boolean checkResult(String column, String value, String scoulmn, int highscore )
    {
        int a_res = 0;
        boolean b_res = false;
        try
        {
            statement = connect.createStatement();
            preparedStatement = connect.prepareStatement("SELECT * FROM highscore WHERE " + column + "='" + value + "' AND " + scoulmn + ">=" + highscore);
            resultset = preparedStatement.executeQuery();
            while (resultset.next())
            {
                a_res = resultset.getInt(1);
            }

            if (a_res == 0)
            {
                b_res = false;
            }
            else
            {
                b_res = true;
            }
            
            //System.out.println("Kollade om highscore �r mindre eller st�rre");  //Fels�kningsstr�ng
        }
        catch (SQLException e)
        {
        }
        
        return b_res;
    }

    //Anv�nds f�r att kontrollera om v�rdet finns sen tidigare. Om det inte finns returneras false och true om v�rdet finns
    private boolean getValue(String column, String value)
    {
        int a_res = 0;
        boolean b_res = false;
        String exception = null;

        try
        {
            //System.out.println("Testar att h�mta mail");
            exception = "H�mtning av " + column;
            statement = connect.createStatement();
            preparedStatement = connect.prepareStatement("SELECT * FROM highscore WHERE " + column + "='" + value + "'");
            resultset = preparedStatement.executeQuery();
            while (resultset.next())
            {
                a_res = resultset.getInt(1);
            }

            if (a_res == 0)
            {
                b_res = false;
                //System.out.println("Mailen finns inte sen tidigare");
            }
            else
            {
                b_res = true;
                //System.out.println("Mailen finns sen tidigare");
            }
        }
        catch (SQLException e)
        {
            //Skriver ut felmeddelandet �ven n�r det inte blir fel
            //System.out.println("N�got gick fel vid kontroll av mail\n\n" + e);
        }

        return b_res;
    }
  //Vad h�nder h�r?
    public void getHighscore(int antal, boolean visaStigande)
    {
        connectToDB();

        String sort = null;
        if (visaStigande == true)
        {
            sort = "ASC";
        }
        else
        {
            sort = "DESC";
        }

        try
        {
            System.out.println("H�mtar top " + antal + " ifr�n servern");
            statement = connect.createStatement();
            resultset = statement.executeQuery("SELECT * FROM highscore ORDER BY highscore " + sort + " LIMIT " + antal);
            System.out.println("Spelarnamn: " + "\t" + "Po�ng: ");
            while (resultset.next())
            {
                String stemp = resultset.getString("namn");
                int itemp = resultset.getInt("highscore");
                System.out.println(stemp + "\t\t" + itemp);
            }
        }
        catch (SQLException e)
        {
        } 

        close();
    }
  //Vad h�nder h�r?
    public ResultSet getTopScore()
    {
        connectToDB();
        ResultSet rs = null;

        try
        {
            statement = connect.createStatement();
            rs = statement.executeQuery("SELECT namn, highscore FROM highscore ORDER BY highscore DESC LIMIT 10");
            
        }
        catch(SQLException e)
        {

        }


        return rs;
    }
  //Vad h�nder h�r?
    private void connectToDB()
    {
        try
        {
            System.out.println("Ansluter till servern");
            Class.forName(driver);
            connect = DriverManager.getConnection(url + db, user, pass);
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }
  //Vad h�nder h�r?
    public void close()
    {
        try
        {
            if (resultset != null)
            {
                resultset.close();
            }

            if (statement != null)
            {
                statement.close();
            }

            if (connect != null)
            {
                connect.close();
            }
        }
        catch (SQLException e)
        {
            System.out.println(e);
        }
    }
}
