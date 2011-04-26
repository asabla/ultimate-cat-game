package katt;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Game;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Menu_Highscore extends BasicGameState implements ComponentListener
{
	private int ID = -1;
	private Database db;
	public ArrayList<String> highscores;
	
	private Image back = null;
	private Image backOver = null;
	private MouseOverArea[] areas = new MouseOverArea[2];
	private StateBasedGame game;
	private Highscores hs;
	
	private String hogsta;
	

	public Menu_Highscore(int ID)
	{
		super();
		this.ID = ID;
		
		db = new Database();
		hs = new Highscores();
		
		
		
	}
	
	public void setHighscores(ArrayList<String> scores)
	{
		highscores = scores;
	}
	
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException 
	{
		this.game = game;
		back = new Image("data/Img/tillbaka1.png");  //Tillbakaknappen
		backOver = new Image("data/Img/tillbaka2.png");  //Om musen ‰r ˆver sÂ byter bild
		
		
		//L‰gger alla bilder i en array, samt definerar areas och bildernas position. Samt storlek pÂ mouse over
		for (int i = 0; i < 2; i++)
		{
			if(i == 0)
			{
				areas[i] = new MouseOverArea(container, back, 130, 400, 400, 52, this);
				areas[i].setMouseOverImage(backOver);
			}
		}
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException
	{
		g.setBackground(Color.blue); //S‰tter bakgrundsf‰rg
		for(int i = 0; i < areas.length; i++)
		{
			//renderar knappen
			areas[0].render(container, g); //Skall vara i ist‰llet fˆr noll om det finns fler knappar
		}
		
		//integers som beskriver vart texten skall renderas
		int x = 100;
		int y = 100;
		int pos = 1;
		
		String global = "Globalt:";
		String lokal = "Lokalt:";
		
		g.drawString(global, 100, 80);
		// Loopar igenom arraylisten
		for(String s : highscores)
		{
			g.drawString(pos + ": " + s, x, y);  //Renderar varje highscore som h‰mtas
			y += 20;
			pos++;
		}
		
		g.drawString(lokal, 400, 80);
		g.drawString(hogsta, 400, 100);
		
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException
	{

	}

	@Override
	public void componentActivated(AbstractComponent source)
	{
		// Anv‰nds fˆr att kontrollera om anv‰ndare klickar pÂ nÂgon av knapparna
		if (source == areas[0])//Nytt spel
		{
			game.enterState(StateHandler.menu);
		}
	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException{
		highscores = new ArrayList<String>();
		highscores = db.getHighscoreToArrayList(10, false, true);
		hogsta = hs.tillString();
	}

	@Override
	public int getID()
	{
		// TODO Auto-generated method stub
		return ID;
	}
}