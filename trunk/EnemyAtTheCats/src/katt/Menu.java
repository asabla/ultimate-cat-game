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

public class Menu extends BasicGameState implements ComponentListener{
	
	private Image back = null;
	private Image newgame = null;
	private Image newgameOver = null;
	private MouseOverArea[] areas = new MouseOverArea[2];
	private StateBasedGame game;
	private int ID = -1;
	
	
	public Menu(int ID){
		super();
		this.ID = ID;
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		back.draw(0,0);
		areas[0].render(container, g);
	}

	@Override
	public void componentActivated(AbstractComponent source) {
		if(source == areas[0]){
			StateHandler.paused = false;
			game.enterState(StateHandler.theGame);
		}
	}

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		this.game = game;
		
		back = new Image("data/Img/Back.png");
		newgame = new Image("data/Img/Nyttspel1.png");
		newgameOver = new Image("data/Img/Nyttspel2.png");
		
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
		
		if (input.isKeyPressed(Input.KEY_ESCAPE)) {
			game.enterState(StateHandler.theGame);
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
