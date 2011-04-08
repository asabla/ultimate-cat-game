package katt;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class Game extends BasicGame {
	private TiledMap map1, map2;
	private Player mr;
	public static float gameSpeed = 1;
	private Long currentScore;
	private static UpdateScore ts;
	private String playerName;
	private float mapSpeed = 2f;
	private float bgSpeed1 = 0.12f;
	private float bgSpeed2 = 0.55f;
	private final static int screenWidth = 640;
	private final static int screenHeight = 480;
	private TestFrame testFrame;


	// ******************************************************************
	/*
	 * Deklaration av variablerna för spelets olika bakgrund bgLayerX - Olika
	 * bakgrundslager float posXLayerX - Start position för bakgrundslagren
	 * movSpeedX - Speed variabel för bakgrundslagren
	 */
	Image bgSky = null;
	Image bgLayer1 = null;
	Image bgLayer2 = null;
	Image bgLayer3 = null;
	Image bgLayer4 = null;
	Image playerPlane = null;
	float mapLayer1 = 0;
	float mapLayer2 = screenWidth;
	float posXLayer1 = 0;
	float posXLayer2 = screenWidth*2;
	float posXLayer3 = 0;
	float posXLayer4 = screenWidth*2;
	float posY = 0;

	// ******************************************************************

	public Game() {
		super("one class barebone game");
		testFrame = new TestFrame();
		testFrame.setVisible(true);
	}

	public void init(GameContainer container) throws SlickException {
		currentScore = 0L;
		playerName = "Klas";
		//container.setVSync(true);
		map1 = new TiledMap("data/room2.tmx");
		map2 = new TiledMap("data/room.tmx");
		mr = new Player(100, 300, "src/data/barney.png");
		mr.setCurrentAnimation(mr.getRun());
		/*
		 * Filnamn på game-assets som laddas
		 */
		bgSky = new Image("data/img_bg_sky.png");
		bgLayer1 = new Image("data/img_bg_layer1.png");
		bgLayer2 = new Image("data/img_bg_layer1.png");
		bgLayer3 = new Image("data/img_bg_layer2.png");
		bgLayer4 = new Image("data/img_bg_layer2.png");
	}

	public void update(GameContainer container, int delta) {
		Input input = container.getInput();
		mapLayer1 -= (int) mapSpeed * gameSpeed;
		mapLayer2 -= (int) mapSpeed * gameSpeed;

		posXLayer1 -= bgSpeed1 * gameSpeed;
		posXLayer2 -= bgSpeed1 * gameSpeed;

		posXLayer3 -= bgSpeed2 * gameSpeed;
		posXLayer4 -= bgSpeed2 * gameSpeed;

		if (posXLayer1 <= -screenWidth*2) {
			posXLayer1 = screenWidth*2;
		}
		if (posXLayer2 <= -screenWidth*2) {
			posXLayer2 = screenWidth*2;
		}
		if (posXLayer3 <= -screenWidth*2) {
			posXLayer3 = screenWidth*2;
		}
		if (posXLayer4 <= -screenWidth*2) {
			posXLayer4 = screenWidth*2;
		}
		if (mapLayer1 <= -screenWidth) {
			mapLayer1 = screenWidth;
		}
		if (mapLayer2 <= -screenWidth) {
			mapLayer2 = screenWidth;
		}

		if (container.getInput().isKeyDown(Input.KEY_UP)) {
			mr.setCurrentAnimation(mr.getJump());
			mr.setPlayerY(mr.getPlayerY() - 1);
			mr.getPlayerBox().setY(mr.getPlayerY());
			try {
				if (entityCollisionWith()) {
					mr.setPlayerY(mr.getPlayerY() + 1);
					mr.getPlayerBox().setY(mr.getPlayerY());
				}
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}

		if (container.getInput().isKeyDown(Input.KEY_DOWN)) {
			mr.setCurrentAnimation(mr.getRun());
			mr.setPlayerY(mr.getPlayerY() + 1);
			mr.getPlayerBox().setY(mr.getPlayerY());
			try {
				if (entityCollisionWith()) {
					mr.setPlayerY(mr.getPlayerY() - 1);
					mr.getPlayerBox().setY(mr.getPlayerY());
				}
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}

		if (container.getInput().isKeyPressed(Input.KEY_RIGHT)) {
			if(gameSpeed - 0.5f < 35)
				gameSpeed += 0.5f;
			System.out.println("Currengamespeed: " + gameSpeed);
		}
		if (container.getInput().isKeyPressed(Input.KEY_LEFT)) {
			if (gameSpeed - 0.5f >= 1)
				gameSpeed -= 0.5f;
			System.out.println("Currengamespeed: " + gameSpeed);
		}
		if (container.getInput().isKeyPressed(Input.KEY_SPACE)) {
			currentScore += 500;
			System.out.println("Current Score: " + currentScore);
		}
		if (container.getInput().isKeyPressed(Input.KEY_C)) {
			//ts = new UpdateScore(playerName, currentScore);
			
		}
		
	}

	public boolean entityCollisionWith() throws SlickException {
		// for (int i = 0; i < BlockMap.entities.size(); i++) {
		// Block entity1 = (Block) BlockMap.entities.get(i);
		// if (mr.getPlayerBox().intersects(entity1.poly)) {
		// if(mr.getPlayerBox().intersects())
		// return true;
		// }
		// }
		return false;
	}

	public void render(GameContainer container, Graphics g) {
		bgSky.draw(0, 0);
		bgLayer1.draw(posXLayer1, posY);
		bgLayer2.draw(posXLayer2, posY);

		bgLayer3.draw(posXLayer3, posY);
		bgLayer4.draw(posXLayer4, posY);

		map1.render((int) mapLayer1, (int) posY);
		map2.render((int) mapLayer2, (int) posY);

		mr.updateAnimation();
		g.drawAnimation(mr.getCurrentAnimation(), mr.getPlayerX(), mr.getPlayerY());
	}
	
	public long getCurrentScore(){
		return currentScore;
	}

	public static void main(String[] argv) throws SlickException {
		AppGameContainer container = new AppGameContainer(new Game(), screenWidth, screenHeight,false);
		container.start();
	}
}