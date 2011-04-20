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
import org.newdawn.slick.util.InputAdapter;

public class GameOver extends BasicGameState implements ComponentListener{
	
	private Image back = null;
	private Image newgame = null;
	private Image newgameOver = null;
	private Image backOver = null;
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
		
		g.drawString("Game Over", 300, 50);
		g.setBackground(Color.magenta);
		
		
		
		
		
		areas[0].render(container, g);
		areas[1].render(container, g);
		//game.getState(DenBraMenyn.theGame).render(container, game, g);
		
		
	}

	@Override
	public void componentActivated(AbstractComponent source) {
		if(source == areas[0]){
			StateHandler.paused = false;
			game.enterState(StateHandler.theGame);
			}
		if(source == areas[1])
		{
			StateHandler.paused = false;
			game.enterState(StateHandler.menu);
		}
		
	}

	@SuppressWarnings("deprecation")
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		this.game = game;
		
		back = new Image("data/Img/Back.png");
		newgame = new Image("data/Img/nyttspel1.png");
		newgameOver = new Image("data/Img/nyttspel2.png");
		back = new Image("data/Img/tillbaka1.png");
		backOver = new Image("data/Img/tillbaka2.png");
		
		//container.setMouseCursor("data/Img/cursor.png", 0, 0);
		
		for(int i = 0; i<2; i++)
		{
			if(i == 0)
			{
				areas[i] = new MouseOverArea(container, newgame, 130, 100, 400, 52, this);
				areas[i].setMouseOverImage(newgameOver);
			}
			if(i == 1)
			{
				areas[i] = new MouseOverArea(container, back, 135, 200, 400, 52, this);
				areas[i].setMouseOverImage(backOver);
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
