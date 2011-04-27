package katt;

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

public class DeadMenu extends BasicGameState implements ComponentListener {

	private Image newgame = null;
	private Image newgameOver = null;
	private MouseOverArea[] areas = new MouseOverArea[2];
	private StateBasedGame game;
	private int ID = -1;
	private float[] backgroundPos;
	private Image[] backgrounds;
	private Image bgSky;

	public DeadMenu(int ID) {
		super();
		this.ID = ID;
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		game.getState(StateHandler.THEGAME).render(container, game, g);
		areas[0].render(container, g);
	}

	@Override
	public void componentActivated(AbstractComponent source) {
		if (source == areas[0]) {
			StateHandler.dead = true;
			StateHandler.paused = false;
			game.enterState(StateHandler.THEGAME);
			// Player1.threadDone();
		}
	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		this.game = game;

		backgroundPos = new float[4];
		backgroundPos[0] = 0;
		backgroundPos[1] = 1280;
		backgroundPos[2] = 0;
		backgroundPos[3] = 1280;

		backgrounds = new Image[4];
		bgSky = new Image("data/Img/img_bg_sky.png");
		backgrounds[0] = new Image("data/Img/img_bg_layer1.png");
		backgrounds[1] = new Image("data/Img/img_bg_layer1.png");
		backgrounds[2] = new Image("data/Img/img_bg_layer2.png");
		backgrounds[3] = new Image("data/Img/img_bg_layer2.png");

		newgame = new Image("data/Img/Fortsatt1.png");
		newgameOver = new Image("data/Img/Fortsatt2.png");

		// container.setMouseCursor("data/Img/cursor.png", 0, 0);

		for (int i = 0; i < 2; i++) {
			if (i == 0) {
				areas[i] = new MouseOverArea(container, newgame, 130, 100, 400,
						52, this);
				areas[i].setMouseOverImage(newgameOver);
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

		// game.getState(StateHandler.theGame).update(container, game, delta);

		if (input.isKeyPressed(Input.KEY_RIGHT)) {
			StateHandler.dead = true;
			StateHandler.paused = false;
			game.enterState(StateHandler.THEGAME);
			// Player1.threadDone();
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
