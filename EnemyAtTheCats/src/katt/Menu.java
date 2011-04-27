package katt;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Menu extends BasicGameState implements ComponentListener {
	private Image back = null;
	private Image newgame = null;
	private Image newgameOver = null;
	private Image Highscore = null;
	private Image HighscoreOver = null;
	private Image logo = null;
	private MouseOverArea[] areas = new MouseOverArea[2];
	private StateBasedGame game;
	private int ID = -1;
	private Database db;
	private Functions func;
	private UnicodeFont myFont;
	private TextField player_name;

	public Menu(int ID) {
		super();
		this.ID = ID;
		db = new Database();
		func = new Functions();
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		g.setBackground(Color.decode("#8FB3C9"));
		g.drawImage(logo, 121.5f, 10);
		for (int i = 0; i < areas.length; i++) {
			areas[i].render(container, g);
		}
		
		player_name.render(container, g);
		g.drawString("Ditt namn: ", 10, 440);
	}

	@Override
	public void componentActivated(AbstractComponent source) {
		if (source == areas[0])// Nytt spel
		{
			StateHandler.paused = false;
			StateHandler.dead = false;

			StateHandler.soundBank.playSound("boing");
			func.setPlayerName(player_name.getText());
			game.enterState(StateHandler.THEGAME);

			// Player1.threadDone();
		}
		if (source == areas[1])// Visa Highscore - INTE KLAR
		{

			StateHandler.soundBank.playSound("boing");
			Menu_Highscore hs = new Menu_Highscore(StateHandler.HIGHSCORE);

			hs.setHighscores(db.getHighscoreToArrayList(10, false, false));
			game.enterState(StateHandler.HIGHSCORE);
		}
	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		this.game = game;

		logo = new Image("data/Img/logo.png");
		back = new Image("data/Img/Back.png");
		newgame = new Image("data/Img/Nyttspel1.png");
		newgameOver = new Image("data/Img/Nyttspel2.png");
		Highscore = new Image("data/Img/highscore1.png");
		HighscoreOver = new Image("data/Img/highscore2.png");
		
		myFont = func.setNewFont("Arial", 24);
		player_name = new TextField(container, myFont, 100, 425, 200, 30);
		player_name.setFocus(true);

		// container.setMouseCursor("data/Img/cursor.png", 0, 0);

		for (int i = 0; i < 2; i++) {
			if (i == 0) {
				areas[i] = new MouseOverArea(container, newgame, 153, 280, 400,
						52, this);
				areas[i].setMouseOverImage(newgameOver);
			}
			if (i == 1) {
				areas[i] = new MouseOverArea(container, Highscore, 153, 350, 400, 52, this);
				areas[i].setMouseOverImage(HighscoreOver);
			}

			// if(i == 1){
			// areas[i] = new MouseOverArea(container, newgame, 130, 100, 400,
			// 52, this);
			// areas[i].setMouseOverImage(newgameOver);
			// }
		}

	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		Input input = container.getInput();
		myFont.loadGlyphs();
		player_name.setFocus(true);

		if (input.isKeyPressed(Input.KEY_ESCAPE)) {
			StateHandler.paused = true;
			StateHandler.dead = false;
			game.enterState(StateHandler.THEGAME);
		}
	}

	@Override
	public int getID() {
		return ID;
	}

	@Override
	public void leave(GameContainer container, StateBasedGame game)
			throws SlickException {

	}
}
