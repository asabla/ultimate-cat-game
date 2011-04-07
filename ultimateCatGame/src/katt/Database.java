package katt;

import java.sql.*;

public class Database
{

    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultset = null;
    private String url = "jdbc:mysql://scrum.no-ip.org:3306/";
    private String db = "scrum";
    private String driver = "com.mysql.jdbc.Driver";
    private String user = "scrum";
    private String pass = "stiga123";

    public void sendHighscore(String name, int highscore, String email, String telefon)
    {
        try
        {
            connectToDB();  //Ansluter till servern
            boolean btemp = getValue("email", email); //Kontrollerar om email-adressen finns sen tidigare
            
            System.out.println("Skickar v�rden till servern");
            statement = connect.createStatement(); //Ser till att drivern hamnar i createstatement
            int val = statement.executeUpdate(getUpdateString(btemp, name, highscore, email, telefon)); //Den faktiska anropet som uppdaterar databasen
            System.out.println("Du har nu skickat in ditt highscore!");

            close();  //St�nger ner anslutningen
        }
        catch (SQLException e)
        {
            System.out.println("Det gick inte att skicka in highsore\nErrorcode: 1001\n" + e);
        }
    }

    private String getUpdateString(boolean update,String name, int highscore, String email, String telefon)
    {
        String s_return = null;

        if(update == false)
        {
            s_return = "INSERT INTO highscore (namn, highscore, email, telefon) "
                    + "VALUES('" + name + "', " + highscore + ", '" + email + "', '" + telefon + "')";
        }
        else
        {
            //L�gg till funktion som kollar om highscore v�rdet �r l�gre eller h�gre �n tidigare
            s_return = "UPDATE highscore SET highscore=" + highscore + " WHERE email='" + email + "'";
        }

        return s_return;  //Returnerar r�tt str�ng, beroende p� om den skall uppdateras eller l�ggas till
    }

    //Anv�nds f�r att kontrollera om v�rdet finns sen tidigare. Om det inte finns returneras false och true om v�rdet finns
    private boolean getValue(String column, String value)
    {
        int a_res = 0;
        boolean b_res = false;
        String exception = null;

        //connectToDB(); //Beh�vs inte d� anslutningen inte �r nerst�ngd

        try
        {
            exception = "H�mtning av " + column;
            statement = connect.createStatement();
            preparedStatement = connect.prepareStatement("SELECT * FROM highscore WHERE " + column + "=" + value);
            resultset = preparedStatement.executeQuery();
            while(resultset.next())
            {
                a_res = resultset.getInt(1);
            }

            if(a_res == 0)
            {
                b_res = false;
            }
            else
            {
                b_res = true;
            }
        }
        catch(SQLException e)
        {
            //Skriver ut felmeddelandet �ven n�r det inte blir fel
            //System.out.println("Operationen: " + exception + " kunde inte genomf�ras. Kontakta support f�r mer info");
        }

        //close(); //Kan inte anv�ndas d� anslutningen redan �r �ppen

        return b_res;
    }

    public void getHighscore(int antal, boolean visaStigande)
    {
        connectToDB();

        String sort = null;
        if(visaStigande == true)
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
            while(resultset.next())
            {
                String stemp = resultset.getString("namn");
                int itemp = resultset.getInt("highscore");
                System.out.println(stemp + "\t\t" + itemp);
            }
        }
        catch(SQLException e)
        {

        }

        close();
    }

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

    private void close()
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
