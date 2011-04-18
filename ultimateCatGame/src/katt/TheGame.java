package katt;

import java.io.IOException;
import java.util.Random;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.particles.ConfigurableEmitter;
import org.newdawn.slick.particles.ParticleIO;
import org.newdawn.slick.particles.ParticleSystem;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class TheGame extends BasicGameState {
	public static TestFrame frame;
	public static float gameSpeed = 2f;
	public int ID;
	
	private Player1 mr;
	private int mapWidth = 640;
	private Polygon collisonBlock;
	private ParticleSystem smoke;
	private final float speedAcc = 0.2f;
	
	private static int time;
	public final static float gravity = 2f;
	private PickupObject pointObject;
	private PickupObject lifeObject;

	private Image[] backgrounds;
	private float[] backgroundPos;
	private float[] backgroundSpeed;

	
	private int mapCount;
	private int levelLength;
	private int currentLevel;
	private int loopCount;
	private BlockMap[] blockMapRow;
	private float currentMapX;
	private float neighbourMapX;
	private int currentMap;
	private int neighbourMap; // Både den som är innan currentmap och när currentmap är på väg ut ur banan
	private Random rnd;
	
	private Player1[] players;
	private int playerCount;

	/*
	 * Deklaration av variablerna för spelets olika bakgrund bgLayerX - Olika
	 * bakgrundslager float posXLayerX - Start position för bakgrundslagren
	 * movSpeedX - Speed variabel för bakgrundslagren
	 */
	Image bgSky = null;
	Image playerPlane = null;
	Image pointObjectImage = null;
	Image lifeObjectImage = null;

	float posY = 0;

	public TheGame(int ID) {
		super();

		this.ID = ID;

		frame = new TestFrame();
	}

	/**
	 * Set the start values of the project Read only in start of the project
	 */
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		currentLevel = 1;
		levelLength = 5;
		loopCount = 0; 
		
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

		backgroundSpeed = new float[4];
		backgroundSpeed[0] = 0.12f;
		backgroundSpeed[1] = 0.12f;
		backgroundSpeed[2] = 0.55f;
		backgroundSpeed[3] = 0.55f;

		mapCount = 5;
		
		currentMap = 0;
		neighbourMap = 1;
		
		currentMapX = 0;
		neighbourMapX = mapWidth;
		
		blockMapRow = new BlockMap[mapCount];
		blockMapRow[0] = new BlockMap("data/Img/room.tmx");
		blockMapRow[1] = new BlockMap("data/Img/room2.tmx");
		blockMapRow[2] = new BlockMap("data/Img/room3.tmx");
		blockMapRow[3] = new BlockMap("data/Img/room4.tmx");
		blockMapRow[4] = new BlockMap("data/Img/room5.tmx");
		
		playerCount = 2;
		
		players = new Player1[playerCount];
		players[0] = new Player1(200, 400, "data/Img/cat2.png", Input.KEY_UP, 3);
		players[1] = new Player1(200, 400, "data/Img/cat2.png", Input.KEY_W, 3);
		
		pointObject = new PickupObject();
		lifeObject = new PickupObject(0, 5000, 250);

		time = 1; // Startar spelet med 1sekund

		// container.setVSync(true);
		container.setTargetFrameRate(150);

		pointObjectImage = new Image((String) pointObject.getImgLoc());
		lifeObjectImage = new Image("data/Img/object0.png");

		//mr = new Player1(200, 400, "data/Img/cat2.png", Input.KEY_UP, 3);
		rnd = new Random();

	}

	/**
	 * Inputs and modifications
	 */
	public void update(GameContainer container, StateBasedGame game, int delta) {
		Input input = container.getInput();
		
		
		if (input.isKeyPressed(Input.KEY_ESCAPE)) {
			game.enterState(StateHandler.menu);
		}
		
		smoke.update(delta);
		time += delta; // Tilldelar tid till variabeln
		
		pointObject.upDateXPos();
		lifeObject.upDateXPos();
		
		mapHandler();
		
		for (int x = 0; x < 4; x++) {
			backgroundPos[x] = updateGraphicElement(backgroundPos[x],
					backgroundSpeed[x], 1280);
		}
		
		//*********************** Player ***********************
		for(int x = 0; x < playerCount; x++){
			players[x].keyPressed(input);
			players[x].setPlayerScore(players[x].getPlayerScore() + (time / 1000 + (int) gameSpeed)/ 2); // Tilldelar poäng till spelaren

			// Update hitbox location
			players[x].getPlayerBox().setY(players[x].getPlayerY());
			players[x].getPlayerBox().setX(players[x].getPlayerX());

			// if outside the window print dead
			if (playerDropOut(players[x])) {
				System.out.println("Dead");
				players[x].deadPlayer();
			}

			if (pointObjectPickup(players[x])) {
				if (StateHandler.soundsOn) {
					StateHandler.soundBank.playSound("crush");
				}

				players[x].setPlayerScore(players[x].getPlayerScore() + pointObject.getValue());
				pointObject = new PickupObject();

				try {
					pointObjectImage = new Image((String) pointObject.getImgLoc());
				} catch (SlickException e) {}
			}
			if (lifeObjectPickup(players[x])) {
				if (StateHandler.soundsOn) {
					StateHandler.soundBank.playSound("happy");
				}
				players[x].setPlayerlife(players[x].getPlayerlife() + 1);
				lifeObject.newObjectPosLong();
			}

			try {
				// Check if any collision is made && no jumping is active
				if (!entityCollisionWith(players[x].getPlayerBox(), blockMapRow[currentMap]) && !players[x].getJumping().isAlive()) {
					players[x].beginFall();
				}
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Draws everything in the container of the game
	 */
	public void render(GameContainer container, StateBasedGame game, Graphics g) {
		drawBackgrounds();
		
		blockMapRow[currentMap].getTmap().render((int) currentMapX, (int) posY);
		blockMapRow[neighbourMap].getTmap().render((int) neighbourMapX, (int) posY);
		
//		blockMapRow[currentMap].drawHitBox(g, currentMapX);
//		blockMapRow[neighbourMap].drawHitBox(g, neighbourMapX);
		
		for(Player1 pl : players){
			pl.updateAnimationSpeed();
			g.drawAnimation(pl.getCurrentAnimation(), pl.getPlayerX(), pl.getPlayerY());
		}

		// draw chosen player with current animation and current coordinate
//		((ConfigurableEmitter) smoke.getEmitter(0)).setPosition(mr.getPlayerX() + 30, mr.getPlayerY() + 15);
//		smoke.render();
		
		g.drawString("Player 1", 10, 30);
		g.drawString("Liv: " + players[0].getPlayerLife(), 10, 45);
		g.drawString("Poäng: " + players[0].getPlayerScore(), 10, 60);
		
		g.drawString("Player 2", 500, 30);
		g.drawString("Liv: " + players[1].getPlayerLife(), 500, 45);
		g.drawString("Poäng: " + players[1].getPlayerScore(), 500, 60);
		
		g.drawString("Tid: " + this.time / 1000 + "sec", 450, 450);
		g.drawString("Level: " + currentLevel, 550, 450);

		g.drawImage(pointObjectImage, pointObject.getxPos(),
				pointObject.getyPos());
		g.drawImage(lifeObjectImage, lifeObject.getxPos(), lifeObject.getyPos());

		
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game)
			throws SlickException {
		try {
			smoke = ParticleIO.loadConfiguredSystem("data/smokeSystem.xml");
		} catch (IOException e) {
			throw new SlickException("Failed to load particle systems", e);
		}

		if (StateHandler.musicOn) {
			StateHandler.bgm.loop();
		}
		// container.setVSync(true);
		// container.setTargetFrameRate(150);

	}

	/**
	 * Controls if target player collides with anything in chosen blockmap
	 * returns a boolean
	 */
	private boolean entityCollisionWith(Shape shp, BlockMap bMap)
			throws SlickException {
		for (int i = 0; i < bMap.getEntities().size(); i++) {
			Block entity1 = (Block) bMap.getEntities().get(i);
			if (shp.intersects(entity1.getPoly())) {
				collisonBlock = entity1.getPoly();
				return true;
			}
		}
		return false;
	}

	private float updateGraphicElement(float layerPos, float moveSpeed,
			int screenUpdate) {
		layerPos -= moveSpeed * gameSpeed;
		if (layerPos <= -screenUpdate) {
			return screenUpdate;
		}
		return layerPos;
	}

	private void drawBackgrounds() {
		bgSky.draw(0, 0);
		for (int x = 0; x < 4; x++) {
			backgrounds[x].draw(backgroundPos[x], posY);
		}
	}


	private void collisionHandler(Player1 pl, BlockMap map) {
		try {
			if (entityCollisionWith(pl.getPlayerBox(), map)) {
				if (pl.getPlayerX() + 51 < collisonBlock.getX()
						&& !(pl.getPlayerY() + 51 < collisonBlock.getY())) {
					System.out.println("Wall");
					pl.setPlayerX(pl.getPlayerX() - 2 * gameSpeed);
				}
				if (pl.getPlayerY() + 50 > collisonBlock.getY()) {
					System.out.println("Floor");
					pl.setOnGround(true);

					// Put Player above the collisionBlock
					pl.setPlayerY(collisonBlock.getY() - 50);

					// Reset the GravityEffect
					pl.setGravityEffect(gravity);
				}
			}
		} catch (SlickException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	private boolean playerDropOut(Player1 pl) {
		return pl.getPlayerBox().getX() < 0 || pl.getPlayerBox().getY() > 480;
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return ID;
	}

	public boolean pointObjectPickup(Player1 pl) {
		if (pl.getPlayerBox().intersects(pointObject.getRectangle())) {
			return true;
		} else {
			return false;
		}
	}

	public boolean lifeObjectPickup(Player1 pl) {
		if (pl.getPlayerBox().intersects(lifeObject.getRectangle())) {
			return true;
		} else {
			return false;
		}
	}
	
	private void mapHandler(){
		currentMapX -= gameSpeed;
		neighbourMapX -= gameSpeed;
		
		for(int x = 0; x < playerCount; x++){
			collisionHandler(players[x], blockMapRow[currentMap]);
			collisionHandler(players[x], blockMapRow[neighbourMap]);
		}
		
		//if(currentMapX + mapWidth < 0){
		if(currentMapX + mapWidth < players[0].getPlayerBox().getCenterX()){
			int temp = currentMap;
			currentMap = neighbourMap;
			neighbourMap = temp;
			
			float temp2 = currentMapX;
			currentMapX = neighbourMapX;
			neighbourMapX = temp2;
		}
		
		if(neighbourMapX + mapWidth <= 0){
			//if(neighbourMapX + mapWidth <= 0){
			neighbourMap = rnd.nextInt(mapCount - 1);
			while(neighbourMap == currentMap){
				neighbourMap = rnd.nextInt(mapCount - 1);
			}
			
			neighbourMapX = mapWidth;
			
			loopCount++;		
	
			if(loopCount >= levelLength){
				if (gameSpeed + speedAcc <= 10) {
					currentLevel++;
					gameSpeed += speedAcc;
					System.out.println("Currengamespeed: " + gameSpeed);
				}
				loopCount = 0;
			}
		}

		blockMapRow[currentMap].updateBlockMap(currentMapX,true);
		blockMapRow[neighbourMap].updateBlockMap(neighbourMapX, true);
		
	}

}