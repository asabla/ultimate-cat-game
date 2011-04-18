package katt;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.Game;

public class GameOver extends BasicGameState implements ComponentListener{
	
	private Image back = null;
	private Image newgame = null;
	private Image highscore = null;
	private MouseOverArea[] areas = new MouseOverArea[2];
	private GameContainer gameContainer;
	private Game ettGame;
	private StateBasedGame game;
	private int ID = -1;
	
	
	public GameOver(int ID){
		super();
		this.ID = ID;
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		g.drawString("Game Over", 30, 30);
		g.setBackground(Color.red);
		
		areas[0].render(container, g);
		//game.getState(DenBraMenyn.theGame).render(container, game, g);
		
		
	}

	@Override
	public void componentActivated(AbstractComponent source) {
		if(source == areas[0]){
			game.enterState(StateHandler.theGame);
			
		}
		
	}

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		this.game = game;
		
		back = new Image("data/Img/Back.png");
		newgame = new Image("data/Img/Nytt.png");
		highscore = new Image("data/Img/Nyttover.png");
		
		//container.setMouseCursor("data/Img/cursor.png", 0, 0);
		
		for(int i = 0; i<2; i++)
		{
			if(i == 0)
			{
			areas[i] = new MouseOverArea(container, newgame, 250, 100, 225, 52, this);
			areas[i].setMouseOverImage(highscore);
			}
		}
		
	}

	@Override
	public void update(GameContainer container,StateBasedGame game, int delta) throws SlickException {
		
			
	}


	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return ID;
	}
	
	@Override
	public void leave(GameContainer container, StateBasedGame game)
			throws SlickException {
		
	}
	
}
