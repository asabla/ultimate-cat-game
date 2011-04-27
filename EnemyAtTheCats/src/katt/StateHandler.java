package katt;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.StateBasedGame;

public final class StateHandler extends StateBasedGame {
	private final static int screenWidth = 720;
	private final static int screenHeight = 480;
	public static final int MENU = 0;
	public static final int THEGAME = 1;
	public static final int GAMEOVER = 2;
	public static final int DEADMENU = 3;
	public static final int HIGHSCORE = 4;
	public static final int PAUSE = 5;
	public static final int XTRALEVEL = 7;
    public static final int SPACE = 6;
	public static SFXBank soundBank;
	public static boolean soundsOn;
	public static boolean musicOn;
	public static Sound bgm, spaceMusic;
	public static boolean paused;
	public static boolean dead;
	public static boolean bonus;

	public StateHandler() throws SlickException {
		super("Ett spel av katter, om katter, för katter, med KLÖS!");
		soundsOn = true;
		musicOn = true;
		paused = false;
		dead = false;
		bonus = false;
		soundBank = new SFXBank();
		bgm = new Sound("data/audio/BGMMenu.ogg");
		spaceMusic = new Sound("data/audio/BGMSpace.ogg");

		this.addState(new Menu(MENU));
		this.addState(new TheGame(THEGAME));
		this.addState(new GameOver(GAMEOVER));
		this.addState(new DeadMenu(DEADMENU));
		this.addState(new Menu_Highscore(HIGHSCORE));
		this.addState(new PauseMenu(PAUSE));
		this.addState(new Space(SPACE));
		this.addState(new XtraLevel(XTRALEVEL));
		this.enterState(MENU);


	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		this.getState(MENU).init(container, this);
		this.getState(THEGAME).init(container, this);
		this.getState(GAMEOVER).init(container, this);
		this.getState(HIGHSCORE).init(container, this);	
		this.getState(XTRALEVEL).init(container, this);	
		this.getState(PAUSE).init(container, this);
	}

	public static void main(String[] argv) throws SlickException {
		AppGameContainer appContainer = new AppGameContainer(new StateHandler());
		appContainer.setDisplayMode(screenWidth, screenHeight, false);
		appContainer.start();
	}
}
