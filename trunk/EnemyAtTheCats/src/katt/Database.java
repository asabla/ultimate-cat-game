package katt;

import java.sql.*;
import java.util.ArrayList;

public class Database
{

    private boolean connected = false;
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

            if (connected == false)
            {
                return; //Om det inte går att ansluta till servern så avbryt
            }

            boolean btemp = getValue("email", email); //Kontrollerar om email-adressen finns sen tidigare
            //System.out.println("Värdet för mailen är: " + btemp);
            String stemp = getUpdateString(btemp, name, highscore, email, telefon);
            //System.out.println(stemp);


            if (stemp.equals("No"))
            {
                System.out.println("Ditt highscore är för litet för att lägga till");
            }
            else
            {
                System.out.println("Skickar värden till servern");
                statement = connect.createStatement(); //Ser till att drivern hamnar i createstatement
                int val = statement.executeUpdate(stemp); //Den faktiska anropet som uppdaterar databasen
                System.out.println("Du har nu skickat in ditt highscore!");
            }
            close();  //Stänger ner anslutningen
        }
        catch (SQLException e)
        {
            System.out.println("Det gick inte att skicka in highsore\nErrorcode: 1001\n" + e);
        }
    }

    public void sendUserValues(String email, String password)
    {
        try
        {
            connectToDB(); //Ansluter till databasen

            if (connected == false)
            {
                return; //Om det inte går att ansluta till servern så avbryt
            }

            boolean btemp = getValue("email", email);
            if (btemp == false)
            {
                //om inte mailadressen finns sen tidigare så kommer inte något värde att skickas
                System.out.println("Kontrollera mailadressen igen");
                return;
            }

            String stemp = getUserValues(email, password);
            System.out.println(stemp);

            System.out.println("Skickar värden till servern");
            statement = connect.createStatement(); //Ser till att drivern hamnar i createstatement
            int val = statement.executeUpdate(stemp); //Den faktiska anropet som uppdaterar databasen
            System.out.println("Värden om användaren är inskickad");

            close(); //Stänger ner anslutningen
        }
        catch(SQLException e)
        {
            System.out.println("Det vart något fel: \n" + e); //Om det skulle bli något fel så skrivs det ut
        }
    }

    private String getUserValues(String email, String password)
    {
        String s_return = "INSERT INTO users VALUES("
                + "(SELECT id FROM highscore WHERE email='" + email + "'), '"
                + email + "', '" + password + "', 0)";

        return s_return;
    }

    private String getUpdateString(boolean update, String name, int highscore, String email, String telefon)
    {
        String s_return = null;

        if (update == false)
        {
            System.out.println("Försöker lägga till i databasen");
            s_return = "INSERT INTO highscore (namn, highscore, email, telefon) "
                    + "VALUES('" + name + "', " + highscore + ", '" + email + "', '" + telefon + "')";
        }
        else
        {
            //Lägg till funktion som kollar om highscore värdet är lägre eller högre än tidigare
            boolean btemp = checkResult("email", email, "highscore", highscore);
            if (btemp == true)
            {
                System.out.println("Highscore förlitet");
                s_return = "No";
            }
            else
            {
                System.out.println("Försöker uppdatera highscore");  //Felsökningssträng
                s_return = "UPDATE highscore SET highscore=" + highscore + " WHERE email='" + email + "'";
            }
        }

        return s_return;  //Returnerar rätt sträng, beroende på om den skall uppdateras eller läggas till
    }

    private boolean checkResult(String column, String value, String scoulmn, int highscore)
    {
        int a_res = 0;
        boolean b_res = false;

        if (connected == false)
        {
            return false; //Om det inte går att ansluta till servern så avbryt
        }

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

            //System.out.println("Kollade om highscore är mindre eller större");  //Felsökningssträng
        }
        catch (SQLException e)
        {
        }

        return b_res;
    }

    //Används för att kontrollera om värdet finns sen tidigare. Om det inte finns returneras false och true om värdet finns
    private boolean getValue(String column, String value)
    {
        int a_res = 0;
        boolean b_res = false;
        String exception = null;

        if (connected == false)
        {
            return false; //Om det inte går att ansluta till servern så avbryt
        }

        try
        {
            //System.out.println("Testar att hämta mail");
            exception = "Hämtning av " + column;
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
            //Skriver ut felmeddelandet även när det inte blir fel
            //System.out.println("Något gick fel vid kontroll av mail\n\n" + e);
        }

        return b_res;
    }

    public String getSingleHighscoreResult()
    {
        connectToDB();

        if (connected == false)
        {
            return null; //Om det inte går att ansluta till servern så avbryt
        }

        String s = "";

        try
        {
            System.out.println("Hämtar topresultatet ifrån servern");
            statement = connect.createStatement();
            statement.setMaxRows(1);
            resultset = statement.executeQuery("SELECT * FROM highscore ORDER BY highscore DESC");
            while (resultset.next())
            {
                String stemp = resultset.getString("namn");
                int itemp = resultset.getInt("highscore");
                s += "Poäng: " + itemp + " Namn: " + stemp;
            }
        }
        catch (SQLException e)
        {
        }

        return s;
    }

    public void getHighscore(int antal, boolean visaStigande)
    {
        connectToDB();

        if (connected == false)
        {
            return; //Om det inte går att ansluta till servern så avbryt
        }

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
            System.out.println("Hämtar top " + antal + " ifrån servern");
            statement = connect.createStatement();
            resultset = statement.executeQuery("SELECT * FROM highscore ORDER BY highscore " + sort + " LIMIT " + antal);
            System.out.println("Spelarnamn: " + "\t" + "Poäng: ");
            while (resultset.next())
            {
                String stemp = resultset.getString("namn");
                int itemp = resultset.getInt("highscore");
                System.out.println(stemp + "\t" + itemp);
            }
        }
        catch (SQLException e)
        {
        }

        close();
    }

    public ArrayList<String> getHighscoreToArrayList(int antal, boolean visaStigande, boolean visaEfternamn)
    {
        connectToDB();

        if (connected == false)
        {
            return null; //Om det inte går att ansluta till servern så avbryt
        }

        ArrayList<String> arrList = new ArrayList<String>();

        String sort = null;
        if (visaStigande == false)
        {
            sort = "DESC";
        }
        else
        {
            sort = "ASC";
        }

        try
        {
            System.out.println("Hämtar top " + antal + " ifrån servern");
            statement = connect.createStatement();
            resultset = statement.executeQuery("SELECT * FROM highscore ORDER BY highscore " + sort + " LIMIT " + antal);
            System.out.println("Spelarnamn: " + "\t" + "Poäng: ");
            while (resultset.next())
            {
                String stemp = resultset.getString("namn");
                int itemp = resultset.getInt("highscore");

                //Används för att enbart visa förnamnet
                if(visaEfternamn)
                {
                    String s = stemp;
                    int index = s.indexOf(' ');
                    if(index == -1)
                    {
                        stemp = s;
                    }
                    else
                    {
                        stemp = s.substring(0, index);
                    }
                }
                
                arrList.add(stemp + "\t" + itemp);
                //System.out.println(stemp + "\t" + itemp);
            }
        }
        catch (SQLException e)
        {
        }

        close();

        return arrList;
    }

    public ResultSet getTopScore()
    {
        connectToDB();

        if (connected == false)
        {
            return null; //Om det inte går att ansluta till servern så avbryt
        }

        ResultSet rs = null;

        try
        {
            statement = connect.createStatement();
            rs = statement.executeQuery("SELECT namn, highscore FROM highscore ORDER BY highscore DESC LIMIT 10");

        }
        catch (SQLException e)
        {
        }

        close();

        return rs;
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
        finally
        {
            if (connect != null)
            {
                System.out.println("Du är ansluten");
                connected = true;
            }
            else
            {
                System.out.println("Kunde inte ansluta");
                connected = false;
                return;
            }
        }
    }

    void close()
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
