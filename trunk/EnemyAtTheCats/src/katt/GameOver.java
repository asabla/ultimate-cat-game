package katt;

import java.awt.Font;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.Game;

public class GameOver extends BasicGameState implements ComponentListener {

	private Image back = null;
	private Image newgame = null;
	private Image newgameOver = null;
	private Image backOver = null;
	private Image sadCat = null;
	private Image gameover = null;
	private MouseOverArea[] areas = new MouseOverArea[3];
	private GameContainer gameContainer;
	private Game ettGame;
	private StateBasedGame game;
	private int ID = -1;
	private UnicodeFont myFont;
	private String f_namn;
	private String f_email;
	private String f_telefon;
	private static String pscore;
	private Image sendScore;
	private Database db;
	private Functions func;
	private Highscores scores;
	private String superName;
	private Score ss;
	
	private StartScreen start;

	public GameOver(int ID) {
		super();
		this.ID = ID;
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		
		game.getState(StateHandler.THEGAME).render(container, game, g);
		
		//g.setBackground(Color.magenta);

		areas[0].render(container, g);
		areas[1].render(container, g);
		areas[2].render(container, g);

		f_namn = start.getNamn();
		f_email = start.getMail();
		f_telefon = start.getTel();
		
		
		g.drawString(superName, 130, 70);
		g.setFont(myFont);
		g.drawString(pscore, 150, 350);
		
		g.drawString(f_namn, 150, 260);
		g.drawString(f_email, 150, 290);
		g.drawString(f_telefon, 150, 320);
		
		g.drawImage(sadCat, 590, 410);
		
		g.drawImage(gameover, 130, 10);
	}

	@Override
	public void componentActivated(AbstractComponent source) {
		
		
		if (source == areas[0]) {
			StateHandler.paused = false;
			StateHandler.dead = false;
			StateHandler.bonus = false;
			StateHandler.soundBank.playSound("boing");
			game.enterState(StateHandler.THEGAME);

		}
		if (source == areas[1]) {

		

			StateHandler.soundBank.playSound("boing");
			game.enterState(StateHandler.MENU);

		}
		
	}

	@SuppressWarnings("deprecation")
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		this.game = game;

		back = new Image("data/Img/Back.png");
		newgame = new Image("data/Img/nyttspel1.png");
		newgameOver = new Image("data/Img/nyttspel2.png");
		back = new Image("data/Img/tillbaka1.png");
		backOver = new Image("data/Img/tillbaka2.png");
		sendScore = new Image("data/Img/object1.png");
		sadCat = new Image("data/Img/SadCat.png");
		gameover = new Image ("data/Img/gameover.png");

		// container.setMouseCursor("data/Img/cursor.png", 0, 0);

		for (int i = 0; i < 3; i++) {
			if (i == 0) {
				areas[i] = new MouseOverArea(container, newgame, 130, 100, 400,
						52, this);
				areas[i].setMouseOverImage(newgameOver);
			}
			if (i == 1) {
				areas[i] = new MouseOverArea(container, back, 135, 200, 400,
						52, this);
				areas[i].setMouseOverImage(backOver);
			}
			if (i == 2) {
				areas[i] = new MouseOverArea(container, sendScore, 150, 410,
						100, 52, this);
			}
		}

		myFont = getNewFont("Arial", 24);
		
		start = new StartScreen(null);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		myFont.loadGlyphs();
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return ID;
	}

	public static void setPScore(String score) {
		pscore = score;
	}

	public void enter(GameContainer container, StateBasedGame game)
			throws SlickException {
		
		db = new Database();
		func = new Functions();
		scores = new Highscores();
		ss = new Score(Long.parseLong(pscore));
		ss.setName(func.getPlayerName());
		superName = scores.check(ss); //Strängen används för att skriva ut info till spelaren

	}

	@Override
	public void leave(GameContainer container, StateBasedGame game)
			throws SlickException {

	}

	public UnicodeFont getNewFont(String fontName, int fontSize) {
		UnicodeFont font = new UnicodeFont(new Font(fontName, Font.PLAIN,
				fontSize));
		font.addGlyphs("@");
		font.getEffects().add(new ColorEffect(java.awt.Color.black)); // MÂste
																		// vara
																		// minst
																		// en
																		// effekt
																		// aktiverad
																		// fˆr
																		// att
																		// det
																		// ska
																		// fungera
		return (font);
	}
}