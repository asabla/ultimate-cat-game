package katt;

import java.awt.Font;

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
		// Måste vara minst en effekt aktiverad för att det ska fungera
		font.getEffects().add(new ColorEffect(java.awt.Color.white));
		return (font);
	}
}
