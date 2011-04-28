package katt;

import java.awt.Font;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

public class Functions
{
	private static String PlayerName = "";
	private Random rnd;
	
	public Functions()
	{
		rnd = new Random();
	}
	
	public String getPlayerName()
	{
		return PlayerName;
	}
	
	//Returnerar en genererad sträng
	public String getRandomString(){
		//Skapar en array som håller åtta bokstäver/siffror
	    char[] pw = new char[8];
	    //Sätter ett nummer som motsvarar en bokstav
	    int c  = 'A';
	    //variabel som håller ett slumpmässigt nummer som motsvarar stora bokstäver,
	    //små bokstäver eller siffror
	    int  r1 = 0;
	    //Loopar och slumpar fram nummer till strängen
	    for (int i=0; i < 8; i++)
	    {
	      r1 = rnd.nextInt(3);
	      switch(r1) {
	        case 0: c = '0' +  rnd.nextInt(10); break;
	        case 1: c = 'a' +  rnd.nextInt(26); break;
	        case 2: c = 'A' +  rnd.nextInt(26); break;
	      }
	      pw[i] = (char)c;
	    }
	    //Returnerar strängen
	    System.out.println(new String(pw));
	    return new String(pw);
	}
	
	public void setPlayerName(String name)
	{
		PlayerName = name;
	}
	
	public UnicodeFont setNewFont(String fontName, int fontSize)
	{
		UnicodeFont font = new UnicodeFont(new Font(fontName, Font.PLAIN, fontSize));
		font.addGlyphs("@");
		// Måste vara minst en effekt aktiverad för att det ska fungera
		font.getEffects().add(new ColorEffect(java.awt.Color.white));
		return (font);
	}
	
	//Returnerar ett boolean-värde efter att ha kontrollerat om strängen är godkänd som email-adress
    public boolean validateEmail(String email)
    {
        boolean check;

        System.out.println("Kontrollerar mailen: " + email);

        //Pattern p = Pattern.compile(".+@.+\\.[a-z]+"); //Gammal kontrollsträng
        Pattern p = Pattern.compile("^[\\w\\-]+(\\.[\\w\\-]+)*@([A-Za-z0-9-]+\\.)+[A-Za-z]{2,4}$");
        Matcher m = p.matcher(email);
        check = m.matches();

        return check;
    }

    //Returnerar ett boolean-värde efter att ha kontrollerat om telefon-numret är godkänt
    public boolean validatePhone(String phone)
    {
        boolean c_phone = false;

        // Exempel på vilka nummertyper som den klarar av
        //0  072-5424315
        //1  0725494392
        //2  018-124354
        //3  018155394

        String[] patterns = new String[4];
        patterns[0] = "\\d{3}-\\d{7}";
        patterns[1] = "\\d{10}";
        patterns[2] = "\\d{3}-\\d{6}";
        patterns[3] = "\\d{9}";

        System.out.println("Börjar kontroll av numret: " + phone);

        for(String s : patterns)
        {
            //System.out.println("Pattern: " + s);  //Felsökningssträng
            //Regex-uttrycket som kontrollerar om det är ett nummer
            Pattern p = Pattern.compile(s);
            Matcher m = p.matcher(phone); //Kontrollerar strängen med numret
            
            if(m.matches()) //Om det finns några resultat som stämmer överens så kör detta
            {
                c_phone = m.matches();  //Hämtar boolvärdet och sätter det
                //System.out.println(c_phone);  //Felsökningssträng
            }
        }

        return c_phone;
    }
}
