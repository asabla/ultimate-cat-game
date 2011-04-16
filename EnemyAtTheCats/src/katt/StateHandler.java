package katt;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.StateBasedGame;

public final class StateHandler extends StateBasedGame {
	private final static int screenWidth = 640;
    private final static int screenHeight = 480;
    public static final int menu = 0;
    public static final int theGame = 1;
    public static final int gameOver = 2;
	public static SFXBank soundBank;
	public static boolean soundsOn;
	public static boolean musicOn;
	public static Sound bgm;
    
    
	public StateHandler() throws SlickException {
		super("Det bra spelet");
		soundsOn = true;
		musicOn = true;
		soundBank = new SFXBank();
		bgm = new Sound("data/audio/BGMMenu.ogg");
		this.addState(new Menu(menu));
		this.addState(new TheGame(theGame));
		this.addState(new GameOver(gameOver));
		this.enterState(menu);
		
	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		this.getState(menu).init(container, this);
		this.getState(theGame).init(container, this);
		this.getState(gameOver).init(container, this);
	}

	public static void main(String[] argv) throws SlickException {
        AppGameContainer appContainer = new AppGameContainer(new StateHandler());
        appContainer.setDisplayMode(screenWidth, screenHeight, false);
        appContainer.start();
	}
}
