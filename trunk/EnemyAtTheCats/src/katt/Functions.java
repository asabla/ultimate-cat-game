package katt;

import java.awt.Font;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

public class Functions
{
	private static String PlayerName = "";
	
	public Functions()
	{
		
	}
	
	public String getPlayerName()
	{
		return PlayerName;
	}
	
	public void setPlayerName(String name)
	{
		PlayerName = name;
	}
	
	public UnicodeFont setNewFont(String fontName, int fontSize)
	{
		UnicodeFont font = new UnicodeFont(new Font(fontName, Font.PLAIN, fontSize));
		font.addGlyphs("@");
		// M�ste vara minst en effekt aktiverad f�r att det ska fungera
		font.getEffects().add(new ColorEffect(java.awt.Color.white));
		return (font);
	}
	
	//Returnerar ett boolean-v�rde efter att ha kontrollerat om str�ngen �r godk�nd som email-adress
    public boolean validateEmail(String email)
    {
        boolean check;

        System.out.println("Kontrollerar mailen: " + email);

        //Pattern p = Pattern.compile(".+@.+\\.[a-z]+"); //Gammal kontrollstr�ng
        Pattern p = Pattern.compile("^[\\w\\-]+(\\.[\\w\\-]+)*@([A-Za-z0-9-]+\\.)+[A-Za-z]{2,4}$");
        Matcher m = p.matcher(email);
        check = m.matches();

        return check;
    }

    //Returnerar ett boolean-v�rde efter att ha kontrollerat om telefon-numret �r godk�nt
    public boolean validatePhone(String phone)
    {
        boolean c_phone = false;

        // Exempel p� vilka nummertyper som den klarar av
        //0  072-5424315
        //1  0725494392
        //2  018-124354
        //3  018155394

        String[] patterns = new String[4];
        patterns[0] = "\\d{3}-\\d{7}";
        patterns[1] = "\\d{10}";
        patterns[2] = "\\d{3}-\\d{6}";
        patterns[3] = "\\d{9}";

        System.out.println("B�rjar kontroll av numret: " + phone);

        for(String s : patterns)
        {
            //System.out.println("Pattern: " + s);  //Fels�kningsstr�ng
            //Regex-uttrycket som kontrollerar om det �r ett nummer
            Pattern p = Pattern.compile(s);
            Matcher m = p.matcher(phone); //Kontrollerar str�ngen med numret
            
            if(m.matches()) //Om det finns n�gra resultat som st�mmer �verens s� k�r detta
            {
                c_phone = m.matches();  //H�mtar boolv�rdet och s�tter det
                //System.out.println(c_phone);  //Fels�kningsstr�ng
            }
        }

        return c_phone;
    }
}
