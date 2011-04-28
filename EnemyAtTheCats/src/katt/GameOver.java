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
	private TextField f_namn;
	private TextField f_email1;
	private TextField f_email2;
	private TextField f_telefon;
	private TextField f_points;
	private String message = "google";
	private String snabel = "@";
	private static String pscore;
	private Image sendScore;
	private Database db;
	private Functions func;
	private Highscores scores;
	private String superName;
	private Score ss;

	public GameOver(int ID) {
		super();
		this.ID = ID;
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		
		g.setBackground(Color.magenta);

		areas[0].render(container, g);
		areas[1].render(container, g);
		areas[2].render(container, g);

		// myFont.drawString(0,0,"test"); //GÂr att anv‰nda fonten till att
		// skriva ut

		f_namn.render(container, g);
		f_email1.render(container, g);
		f_email2.render(container, g);
		f_telefon.render(container, g);
//		f_points.render(container, g);

		g.drawString(superName, 130, 70);
		g.setFont(myFont);
		g.drawString(message, 200, 550);
		
		g.drawString(snabel, 355, 310);
		g.drawString(pscore, 150, 390);
		
		g.drawImage(sadCat, (720-sadCat.getWidth())/2, 400);
		
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
		if (source == areas[2]) {
			StateHandler.soundBank.playSound("boing");
			int tmp = Integer.parseInt(pscore); // Parsar om texten
			String mail = f_email1.getText() + "@" + f_email2.getText();												// ifrÂn highscore
															// till en int
			db.sendHighscore(f_namn.getText(), tmp, mail,
					f_telefon.getText()); // Testar att skicka in resultatet
		}

		if (source == f_namn) {
			StateHandler.soundBank.playSound("boing");
			f_email1.setFocus(true);
		}
		if (source == f_email1) {
			StateHandler.soundBank.playSound("boing");
			f_email2.setFocus(true);
		}
		if(source == f_email2);{
			StateHandler.soundBank.playSound("boing");
			f_telefon.setFocus(true);
		}
		if (source == f_telefon) {
			StateHandler.soundBank.playSound("boing");
			// setPoints
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
		f_namn = new TextField(container, myFont, 150, 270, 200, 35);
		f_email1 = new TextField(container, myFont, 150, 310, 200, 35);
		f_email2 = new TextField(container, myFont, 385, 310, 200, 35);
		f_telefon = new TextField(container, myFont, 150, 350, 200, 35);
//		f_points = new TextField(container, myFont, 150, 390, 200, 35);
		db = new Database();
		func = new Functions();
		scores = new Highscores();
		ss = new Score(Long.parseLong(pscore));
		ss.setName(func.getPlayerName());
		superName = scores.check(ss); //Strängen används för att skriva ut info till spelaren

		f_namn.setText(func.getPlayerName()); // S‰tter spelarens namn fˆrdefinerat
		f_email1.setText("mail"); // S‰tter spelarens email
		f_email2.setText("gmail.com");//Sätter vad som ska stå efter @
		f_telefon.setText("324-234234"); // S‰tter spelarens telefon
//		f_points.setText(GameOver.pscore); // H‰mtar spelarens po‰ng n‰r denne
										   // fˆrlorar
//		f_namn.setFocus(true);
	}

	@Override
	public void leave(GameContainer container, StateBasedGame game)
			throws SlickException {

	}

	public UnicodeFont getNewFont(String fontName, int fontSize) {
		UnicodeFont font = new UnicodeFont(new Font(fontName, Font.PLAIN,
				fontSize));
		font.addGlyphs("@");
		font.getEffects().add(new ColorEffect(java.awt.Color.white)); // MÂste
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