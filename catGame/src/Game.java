import java.util.ArrayList;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Polygon;

public class Game extends BasicGame {
	private BlockMap mapX1, mapX2;
	private ArrayList<BlockMap> mapRow;
	private Player mr;
	public static float gameSpeed = 1f;
	private Long currentScore;
	private static UpdateScore ts;
	private String playerName;
	private float mapSpeed = 2f;
	private float bgSpeed1 = 0.12f;
	private float bgSpeed2 = 0.55f;
	private final static int screenWidth = 640;
	private final static int screenHeight = 480;
	private Polygon collisonBlock;

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
	float posXLayer2 = screenWidth * 2;
	float posXLayer3 = 0;
	float posXLayer4 = screenWidth * 2;
	float posY = 0;

	// ******************************************************************

	public Game() {
		super("one class barebone game");
	}

	public void init(GameContainer container) throws SlickException {
		currentScore = 0L;
		playerName = "Klas";
		//container.setVSync(true);
		mapX1 = new BlockMap("data/room3.tmx");
		mapX2 = new BlockMap("data/room3.tmx");

		mr = new Player(200, 400, "src/data/cat2.png");
		mr.setCurrentAnimation(mr.getRun());
		mr.setOnGround(false);
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
		mr.getPlayerBox().setY(mr.getPlayerY());
		mr.getPlayerBox().setX(mr.getPlayerX());
		if (mr.getPlayerBox().getX() < 0) {
			System.out.println("Dead");

		}
		try {
			if (entityCollisionWith(mapX1) || entityCollisionWith(mapX2)) {
				if (collisonBlock.getX() > mr.getPlayerX()) {
					System.out.println("Wall");
					mr.setPlayerX(mr.getPlayerX() - 2 * gameSpeed);
					// mr.getPlayerBox().setX(mr.getPlayerX());
				}
				if (collisonBlock.getY() < mr.getPlayerY() +50) {
					System.out.println("Floor");
	
					if (!mr.isOnGround())
						mr.setOnGround(true);
					mr.setPlayerY(mr.getPlayerY() - 2 * gameSpeed);
					
					// mr.getPlayerBox().setY(mr.getPlayerY());

				}
			}
		} catch (SlickException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		mapLayer1 -= (int) mapSpeed * gameSpeed;
		mapLayer2 -= (int) mapSpeed * gameSpeed;

		posXLayer1 -= bgSpeed1 * gameSpeed;
		posXLayer2 -= bgSpeed1 * gameSpeed;

		posXLayer3 -= bgSpeed2 * gameSpeed;
		posXLayer4 -= bgSpeed2 * gameSpeed;

		if (posXLayer1 <= -screenWidth * 2) {
			posXLayer1 = screenWidth * 2;
		}
		if (posXLayer2 <= -screenWidth * 2) {
			posXLayer2 = screenWidth * 2;
		}
		if (posXLayer3 <= -screenWidth * 2) {
			posXLayer3 = screenWidth * 2;
		}
		if (posXLayer4 <= -screenWidth * 2) {
			posXLayer4 = screenWidth * 2;
		}
		if (mapLayer1 <= -screenWidth) {
			mapLayer1 = screenWidth;
			if (gameSpeed - 0.2f < 10) {
				gameSpeed += 0.2f;
				System.out.println("Currengamespeed: " + gameSpeed);
			}
		}
		if (mapLayer2 <= -screenWidth) {
			mapLayer2 = screenWidth;
			if (gameSpeed - 0.2f < 10) {
				gameSpeed += 0.2f;
				System.out.println("Currengamespeed: " + gameSpeed);
			}
		}

		mr.keyPressed(input);

		mapX1.updateBlockMap(mapLayer1);

		mapX2.updateBlockMap(mapLayer2);

	}

	public boolean entityCollisionWith(BlockMap bMap) throws SlickException {
		for (int i = 0; i < bMap.getEntities().size(); i++) {
			Block entity1 = (Block) bMap.getEntities().get(i);
			if (mr.getPlayerBox().intersects(entity1.getPoly())) {
				if (mr.getPlayerBox().intersects(entity1.getPoly())) {
					collisonBlock = entity1.getPoly();
					return true;
				}
			}
		}
		return false;
	}

	public void render(GameContainer container, Graphics g) {
		bgSky.draw(0, 0);

		bgLayer1.draw(posXLayer1, posY);
		bgLayer2.draw(posXLayer2, posY);

		bgLayer3.draw(posXLayer3, posY);
		bgLayer4.draw(posXLayer4, posY);

		mapX1.getTmap().render((int) mapLayer1, (int) posY);
		//mapX1.drawHitBox(g, (int) mapLayer1);

		mapX2.getTmap().render((int) mapLayer2, (int) posY);
		//mapX2.drawHitBox(g, (int) mapLayer2);

		mr.updateAnimation();
		g.drawAnimation(mr.getCurrentAnimation(), mr.getPlayerX(),mr.getPlayerY());
	}

	public static void main(String[] argv) throws SlickException {
		AppGameContainer container = new AppGameContainer(new Game(),
				screenWidth, screenHeight, false);
		container.start();
	}
}