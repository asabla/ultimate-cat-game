package katt;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class PauseMenu extends BasicGameState implements ComponentListener {
	private Image back = null;
	private Image newgame = null;
	private Image newgameOver = null;
	private Image Highscore = null;
	private Image HighscoreOver = null;
	private MouseOverArea[] areas = new MouseOverArea[2];
	private StateBasedGame game;
	private int ID = -1;
	private Database db;

	public PauseMenu(int ID) {
		super();
		this.ID = ID;
		db = new Database();
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		game.getState(StateHandler.THEGAME).render(container, game, g);
		for (int i = 0; i < areas.length; i++) {
			areas[i].render(container, g);
		}
		
		if(StateHandler.bonusCompleted) {
			showBonusCode(g);
		}
	}

	@Override
	public void componentActivated(AbstractComponent source) {
		if (source == areas[0])// Nytt spel
		{
			StateHandler.paused = true;
			StateHandler.dead = false;
			if(StateHandler.bonus)
				game.enterState(StateHandler.XTRALEVEL);
			else {
				StateHandler.soundBank.playSound("spacedrop");
				game.enterState(StateHandler.SPACE);
			}
				
		}
		if (source == areas[1])// Visa Highscore - INTE KLAR
		{
			Menu_Highscore hs = new Menu_Highscore(StateHandler.HIGHSCORE);
			hs.setHighscores(db.getHighscoreToArrayList(10, false, false));

			game.enterState(StateHandler.HIGHSCORE);
		}
	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		this.game = game;

		back = new Image("data/Img/Back.png");
		newgame = new Image("data/Img/Nyttspel1.png");
		newgameOver = new Image("data/Img/Nyttspel2.png");
		Highscore = new Image("data/Img/highscore1.png");
		HighscoreOver = new Image("data/Img/highscore2.png");

		// container.setMouseCursor("data/Img/cursor.png", 0, 0);

		for (int i = 0; i < 2; i++) {
			if (i == 0) {
				areas[i] = new MouseOverArea(container, newgame, 130, 100, 400,
						52, this);
				areas[i].setMouseOverImage(newgameOver);
			}
			if (i == 1) {
				areas[i] = new MouseOverArea(container, Highscore, 130, 200,
						400, 52, this);
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
		
		
			
	}
	
	private void showBonusCode(Graphics g) {
//		g.setColor(Color.white);
//		g.fillRect(130, 300, 400, 52);
		g.setColor(Color.black);
		g.drawString("You completed the bonus stage!\n" +
				"Please collect your check at the homepage.",130 , 280);
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
