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

public class DeadMenu extends BasicGameState implements ComponentListener{
	
	private Image newgame = null;
	private Image newgameOver = null;
	private MouseOverArea[] areas = new MouseOverArea[2];
	private StateBasedGame game;
	private int ID = -1;
	
	
	public DeadMenu(int ID){
		super();
		this.ID = ID;
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		game.getState(StateHandler.theGame).render(container, game, g);
		areas[0].render(container, g);
	}

	@Override
	public void componentActivated(AbstractComponent source) {
		if(source == areas[0]){
			StateHandler.paused = true;
			game.enterState(StateHandler.theGame);
		}
	}

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		this.game = game;

		newgame = new Image("data/Img/Fortsatt1.png");
		newgameOver = new Image("data/Img/Fortsatt2.png");
		
		//container.setMouseCursor("data/Img/cursor.png", 0, 0);
		
		for(int i = 0; i<2; i++){
			if(i == 0){
			areas[i] = new MouseOverArea(container, newgame, 130, 100, 400, 52, this);
			areas[i].setMouseOverImage(newgameOver);
			}
			//if(i == 1){
			//areas[i] = new MouseOverArea(container, newgame, 130, 100, 400, 52, this);
			//areas[i].setMouseOverImage(newgameOver);
			//}
		}
		
	}

	@Override
	public void update(GameContainer container,StateBasedGame game, int delta) throws SlickException {
		Input input = container.getInput();
		
		if (input.isKeyPressed(Input.KEY_RIGHT)) {
			StateHandler.paused = true;
			game.enterState(StateHandler.theGame);
			Player1.threadDone();
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
