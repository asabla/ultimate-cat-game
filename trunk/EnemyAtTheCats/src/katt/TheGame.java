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

	private int mapWidth = 720;
	private Polygon collisonBlock;
	

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
	private int neighbourMap; // Både den som är innan currentmap och när
								// currentmap är på väg ut ur banan
	private Random rnd;
	private Database db;
	private String topScore;

	private Player1[] players;
	private int playerCount;
	private boolean spaceRide = false;
	private GroundEnemy gEnemy;
	private boolean gogo;
	private boolean movePoint;
	private boolean moveLife;
	
	//En raketdel
	private PickupObject rocketPart;	
	//en array som ska hålla tagna raketdelar
	private boolean[] rocketParts;
	//Variabel som håller reda på om bonusbanan är spelad
	private boolean bonusPlayed;
	/*
	 * Deklaration av variablerna för spelets olika bakgrund bgLayerX - Olika
	 * bakgrundslager float posXLayerX - Start position för bakgrundslagren
	 * movSpeedX - Speed variabel för bakgrundslagren
	 */
	Image bgSky = null;
	Image playerPlane = null;
	Image pointObjectImage = null;
	Image lifeObjectImage = null;
	Image gEnemyImage = null;
	Image rocketPartImage = null;
	
	Image rocket1 = null;
	Image rocket2 = null;
	Image rocket3 = null;


	public static float posY = 0;
	float startPy;// start pos Y för i väg flygande objekt
	float startPx;// start pos X för i väg flygande objekt
	float slutPXv;// gräns för flygande objekt vänster
	float slutPXh;// gräns för flygande objekt höger
	float slutPy;// gräns för flygande objekt på Y axel

	public TheGame(int ID) {
		super();

		this.ID = ID;

		frame = new TestFrame();

		db = new Database();
	}

	/**
	 * Set the start values of the project Read only in start of the project
	 */
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		currentLevel = 1;
		levelLength = 5;
		loopCount = 0;

		// topScore = db.getSingleHighscoreResult();

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

		playerCount = 1;

		players = new Player1[playerCount];
		players[0] = new Player1(200, 400, "data/Img/cat.png",
				Input.KEY_UP, 3);
		// players[1] = new Player1(200, 400, "data/Img/cat2.png", Input.KEY_W,
		// 3);

		pointObject = new PickupObject();
		lifeObject = new PickupObject(0, 5000, 250);
		
		rocketPart = new PickupObject(7, 720, 300);		
		rocketParts = new boolean[]{false, false, false};
		bonusPlayed = false;

		time = 1; // Startar spelet med 1sekund

		// container.setVSync(true);
		container.setTargetFrameRate(150);

		pointObjectImage = new Image((String) pointObject.getImgLoc());
		lifeObjectImage = new Image("data/Img/object0.png");
		rocketPartImage = new Image((String) rocketPart.getImgLoc());
		
		rocket2 = new Image("data/Img/object8.png");
		rocket3 = new Image("data/Img/object9.png");
		rocket1 = new Image("data/Img/object7.png");

		// mr = new Player1(200, 400, "data/Img/cat2.png", Input.KEY_UP, 3);
		rnd = new Random();
		gEnemy = new GroundEnemy(1);
		gEnemyImage = new Image(gEnemy.getImgLoc());
		startPy = 200f;
		startPx = 200f;
		slutPy = startPy - 100f;
		slutPXh = startPx + 40f;
		slutPXv = startPx - 40f;

	}

	/**
	 * Inputs and modifications
	 */

	public void update(GameContainer container, StateBasedGame game, int delta) {
		Input input = container.getInput();
		
		if (input.isKeyPressed(Input.KEY_ESCAPE)) {
			players[0].setOnGround(true);
			game.enterState(StateHandler.pause);
		}
		if(input.isKeyPressed(Input.KEY_U)){
			game.enterState(StateHandler.space);
			spaceRide = true;
		}
		
		time += delta; // Tilldelar tid till variabeln

		pointObject.upDateXPos();
		lifeObject.upDateXPos();
		gEnemy.upDateXPos();
		if(rocketPart != null){
			rocketPart.upDatePartPos();
			if(rocketPart.getxPos()< -20){
				if(rocketPart.getObjectType() < 9){
				rocketPart = new PickupObject(rocketPart.getObjectType() + 1, 700, 300);
				try{
				rocketPartImage = new Image((String)rocketPart.getImgLoc());
				}catch(Exception e){
					System.err.print(e.getMessage());
				}
				}
				else{
					rocketPart = null;
				}
			}			
			}
		

		mapHandler();

		for (int x = 0; x < 4; x++) {
			backgroundPos[x] = updateGraphicElement(backgroundPos[x],
					backgroundSpeed[x], 1280);
		}

		// *********************** Player ***********************
		int spriteSizeX = 20;
		float currentPlX = players[0].getPlayerX();
		float currentPlY = players[0].getPlayerY();
		
		for (int x = 0; x < playerCount; x++) {
			players[x].keyPressed(input);
			players[x].setPlayerScore(
					players[x].getPlayerScore()+ 
					(time / 1000 + (int) gameSpeed) / 2);

			// Update hitbox location
			players[x].getPlayerBox().setY(players[x].getPlayerY());
			players[x].getPlayerBox().setX(players[x].getPlayerX());

			players[x].getBottomHitBox().setLocation(currentPlX + spriteSizeX, currentPlY + 30);
			players[x].getTopHitBox().setLocation(currentPlX + spriteSizeX, currentPlY + 10);
			players[x].getFrontHitBox().setLocation(currentPlX + spriteSizeX + 25 , currentPlY + 15);

			// if outside the window print dead
			if (playerDropOut(players[x])) {
				input.clearKeyPressedRecord();
				System.out.println("Dead");
				System.out.println("Ramlade ut " + players[x].getPlayerY());
				
				// newStartAfterCatHasPassedAway();
				players[x].setOnGround(true);
				GameOver.setPScore("" + players[x].getPlayerScore());
				game.enterState(StateHandler.deadMenu);
				players[x].deadPlayer();

			}

			if (objectCollide(players[x], pointObject.getRectangle())) {
					StateHandler.soundBank.playSound("crush");


				players[x].setPlayerScore(players[x].getPlayerScore() + pointObject.getValue());
				movePoint = true;
				pointObject = new PickupObject();

				try {
					pointObjectImage = new Image(pointObject.getImgLoc());
				} catch (SlickException e) {
				}
			}
			
			if (objectCollide(players[x], lifeObject.getRectangle())) {
					StateHandler.soundBank.playSound("happy");

				moveLife = true;
				players[x].setPlayerlife(players[x].getPlayerlife() + 1);
				lifeObject.newObjectPosLong();
			}
			if (players[x].getPlayerlife() == 0) {
				game.enterState(StateHandler.gameOver);
			}

			try {
				// Check if any collision is made && no jumping is active
				if (!entityCollisionWith(players[x].getPlayerBox(),
						blockMapRow[currentMap])
						&& !players[x].getJumping().isAlive()
						&& !players[x].getFalling().isAlive()) {
					players[x].beginFall();
				}
			} catch (SlickException e) {
				e.printStackTrace();
			}

			if (objectCollide(players[x],gEnemy.getRectangle())) {
				input.clearKeyPressedRecord();

					StateHandler.soundBank.playSound("crash");

				players[x].loosePlayerLife();
				System.out.println("Träffade fiende");
				players[x].setOnGround(true);
				// newStartAfterCatHasPassedAway();
				game.enterState(StateHandler.deadMenu);
			}
			//Kontrollerar att inte alla raketdelar har passerat och om katten kolliderat med någon
			if(rocketPart != null){
			if (objectCollide(players[x], rocketPart.getRectangle())){
   		     StateHandler.soundBank.playSound("Harp1");

				//Sparar att raketdelen är tagen, i vår Array
				rocketParts[rocketPart.getObjectType() - 7] =
					true;
				
				if(rocketPart.getObjectType() >= 7 && rocketPart.getObjectType()<9){
				rocketPart = new PickupObject(rocketPart.getObjectType() + 1, 
						640, 300);
				try{
				rocketPartImage = new Image((String)rocketPart.getImgLoc());
				}
				catch(SlickException e){
					System.out.println(e.getMessage());
				}
				}
				else{
					rocketPart = null;
				}				
			}
			}
			if(rocketAssembled() && !bonusPlayed){
				StateHandler.soundBank.playSound("spaceflight");
				game.enterState(StateHandler.space);
				spaceRide = true;
				System.err.println("Bonus activated!");
				bonusPlayed = true;
			}
		}
	}

	/**
	 * Draws everything in the container of the game
	 */
	public void render(GameContainer container, StateBasedGame game, Graphics g) {
		drawBackgrounds();

		if (game.getState(StateHandler.theGame) == game.getCurrentState()) {
			blockMapRow[currentMap].getTmap().render((int) currentMapX,
					(int) posY);
			blockMapRow[neighbourMap].getTmap().render((int) neighbourMapX,
					(int) posY);


//			blockMapRow[currentMap].drawHitBox(g, currentMapX);
//			blockMapRow[neighbourMap].drawHitBox(g, neighbourMapX);
		}

		

		// ********************************************************

		// om Movepoint eller lifePoint är true så kör denna igång och flyttar
		// poängobjektet uppåt tills den nåt rätt Y pos = slutPy.
		// movePoint är true om katten tagit ett objekt,moveLife är true om
		// katten tagit ett liv,
		String lifePoint = "";
		if (movePoint) {
			lifePoint = "" + pointObject.getValue();
		}
		if (moveLife) {
			lifePoint = "1 UP";
		}
		
		// gogo är en koll att så den fortsätter tills den nåt max pos på X
		// kollar om den har nått slutPy
		if (startPy == slutPy) {
			g.drawString(lifePoint, startPx, slutPy);
			movePoint = false;
			moveLife = false;
			startPy = 400f;
			startPx = 200f;

		}
		// Kollar att den den inte gått för mycket åt höger, körs tills den
		// når slutPXH = den högra gränsen på X
		else if (startPx > slutPXh || (gogo)) {

			g.drawString(lifePoint, startPx--, (startPy--));
			if (startPx > slutPXv) {
				gogo = true;
			} else {
				gogo = false;
			}
		}
		// Kollar att den den inte gått för mycket åt vänster, körs tills
		// den når slutPXv = den vänstra gränsen på X
		else if (startPx < slutPXv || (!gogo)) {
			g.drawString(lifePoint, startPx++, (startPy--));
			if (startPx < slutPXh) {
				gogo = false;
			} else {
				gogo = true;
			}
		}


		// ********************************************************

		if(!spaceRide){

		if (game.getState(StateHandler.theGame) == game.getCurrentState()) {
			for (Player1 pl : players) {
				pl.updateAnimationSpeed();
				g.drawAnimation(pl.getCurrentAnimation(), pl.getPlayerX(),
						pl.getPlayerY());

//				g.draw(pl.getBottomHitBox());
//				g.draw(pl.getTopHitBox());
//				g.draw(pl.getFrontHitBox());
			}
		}
		

			g.drawImage(pointObjectImage, pointObject.getxPos(),
					pointObject.getyPos());
			g.drawImage(lifeObjectImage, lifeObject.getxPos(),
					lifeObject.getyPos());

			g.drawImage(gEnemyImage, gEnemy.getPosX(), gEnemy.getPosY());
			
			if(rocketPart != null){
				g.drawImage(rocketPartImage, rocketPart.getxPos(), rocketPart.getyPos());
				}

		}

		// draw chosen player with current animation and current coordinate

		g.drawString("Player 1", 10, 30);
		g.drawString("Liv: " + players[0].getPlayerLife(), 10, 45);
		g.drawString("Poäng: " + players[0].getPlayerScore(), 10, 60);

		g.drawString("Topscore: " + topScore, 150, 10);

		// g.drawString("Player 2", 500, 30);
		// g.drawString("Liv: " + players[1].getPlayerLife(), 500, 45);
		// g.drawString("Poäng: " + players[1].getPlayerScore(), 500, 60);

		g.drawString("Tid: " + this.time / 1000 + "sec", 450, 450);
		g.drawString("Level: " + currentLevel, 550, 450);
		
		//Ritar ut bonusrutor, och de raketdelar som hittils är tagna
		g.drawString("BONUS", 550, 40);
		g.drawRect(550, 10, rocket1.getWidth(), rocket1.getHeight());
		g.drawRect(590, 10, rocket1.getWidth(), rocket1.getHeight());
		g.drawRect(630, 10, rocket1.getWidth(), rocket1.getHeight());
		if(rocketParts[0] == true){
			g.drawImage(rocket1, 550, 10);
		}
		if(rocketParts[1] == true){
			g.drawImage(rocket2, 590, 10);
		}		
		if(rocketParts[2] == true){
			g.drawImage(rocket3, 630, 10);
		}
		}
	

	@Override
	public void enter(GameContainer container, StateBasedGame game)
			throws SlickException {

		if (StateHandler.musicOn) {
			StateHandler.bgm.loop();
		}

		if (StateHandler.paused) {
			
			
			int playerX = (int) players[0].getPlayerX();
			int playerY = (int) players[0].getPlayerY();
			int playerLife = players[0].getPlayerlife();
			long playerScore = players[0].getPlayerScore();
			

			blockMapRow[currentMap].updateBlockMap(currentMapX, true);

			players = new Player1[playerCount];
			players[0] = new Player1(playerX, playerY, "data/Img/cat.png",
					Input.KEY_UP, playerLife);
			players[0].setPlayerScore(playerScore);
			
			players[0].beginFall();
			
		} else if (StateHandler.dead) {
			pointObject.newObjectPos();
			gEnemy.newObjectPos();
			lifeObject.newObjectPos();
			loopCount = 0;

			currentMap = 0;
			neighbourMap = 1;

			currentMapX = 0;
			neighbourMapX = mapWidth;

			int playerLife = players[0].getPlayerlife();
			long playerScore = players[0].getPlayerScore();

			blockMapRow[currentMap].updateBlockMap(currentMapX, true);

			players = new Player1[playerCount];
			players[0] = new Player1(200, 400, "data/Img/cat.png",
					Input.KEY_UP, playerLife);
			players[0].setPlayerScore(playerScore);
		}

		else {

			currentLevel = 1;
			levelLength = 5;
			loopCount = 0;
			gameSpeed = 2f;
			currentMap = 0;
			neighbourMap = 1;

			currentMapX = 0;
			neighbourMapX = mapWidth;

			playerCount = 1;

			time = 1; // Startar spelet med 1sekund

			players = new Player1[playerCount];
			players[0] = new Player1(200, 400, "data/Img/cat.png", Input.KEY_UP, 3);
			blockMapRow[currentMap].updateBlockMap(currentMapX, true);
			// players[1] = new Player1(200, 400, "data/Img/cat2.png",
			// Input.KEY_W, 3);
		}
	}

	@Override
	public void leave(GameContainer container, StateBasedGame game)
			throws SlickException {
		if (StateHandler.bgm.playing()) {
			StateHandler.bgm.stop();
		}
	}

	/**
	 * Controls if target playershape collides with anything in chosen blockmap
	 * @param shp The playershape to make collision control with 
	 * @param bMap The BlockMap that objectshape collides with
	 * @return true : if shape collides with mapelement, false : if shape dont collide with mapElement 
	 * @author Jonathan B
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

	/**
	 * Updates the position of the background
	 * @param layerPos current position of the background
	 * @param moveSpeed The movmentspeed of the background
	 * @param screenUpdate The width of the background and when it shall reloop
	 * @return The new position of the background 
	 * @author Jonathan B
	 */
	private float updateGraphicElement(float layerPos, float moveSpeed,
			int screenUpdate) {
		layerPos -= moveSpeed * gameSpeed;
		if (layerPos <= -screenUpdate) {
			return screenUpdate;
		}
		return layerPos;
	}

	/**
	 * Draw all backgrounds with current position
	 * @author Jonathan B
	 */
	private void drawBackgrounds() {
		bgSky.draw(0, 0);
		for (int x = 0; x < 4; x++) {
			backgrounds[x].draw(backgroundPos[x], posY);
		}
	}

	/**
	 * Handels the events if a player collides with a mapElement
	 * @param pl Target player
	 * @param map Target map
	 * @author Jonathan B, Oskar, Thomas
	 */
	private void collisionHandler(Player1 pl, BlockMap map) {
		try {
			if (entityCollisionWith(pl.getBottomHitBox(), map)) {
				pl.setPlayerY(collisonBlock.getMinY() - 50);
				pl.setOnGround(true);
				pl.setGravityEffect(gravity);
				System.out.println("Floor");
			}
			if (entityCollisionWith(pl.getTopHitBox(), map)
					&& !entityCollisionWith(pl.getBottomHitBox(), map)) {
				pl.setPlayerY(collisonBlock.getMaxY() + 1);
				System.out.println("Roof");
			}
			if (entityCollisionWith(pl.getFrontHitBox(), map)
					&& !entityCollisionWith(pl.getBottomHitBox(), map)) {
				pl.setPlayerX(collisonBlock.getMinX() - 50);
			}
		} catch (SlickException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	/**
	 * Returns true if player is outside the screen
	 * @param pl Target player
	 * @return true if outside the screen, false if not
	 * @author Jonathan B
	 */
	private boolean playerDropOut(Player1 pl) {
		return pl.getPlayerBox().getX()+40 < 0 || pl.getPlayerBox().getY() > 480;
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return ID;
	}
	
	/**
	 * Returns true if player collide with a objectshape
	 * @param pl Target player
	 * @param shp Target objectshape
	 * @return true if collide, false if not
	 * @author Jonathan B, Viktor
	 */
	private boolean objectCollide(Player1 pl, Shape shp) {
		if (pl.getTopHitBox().intersects(shp)
				|| pl.getFrontHitBox().intersects(shp)
				|| pl.getBottomHitBox().intersects(shp)) {
			return true;
		} else {
			return false;
		}
	}
	//Kontrollerar om raketen är ihopsatt
	private boolean rocketAssembled(){
		if(rocketParts[0] == true && 
				rocketParts[1] == true &&
				rocketParts[2] == true){
			return true;
		}
		else{
			return false;
		}
	}


	/**
	 * Handels the moving, buffering and looping of maps. Update positions of collisionblocks
	 * @author Jonathan B
	 */
	private void mapHandler() {
		currentMapX -= gameSpeed;
		neighbourMapX -= gameSpeed;

		for (int x = 0; x < playerCount; x++) {
			collisionHandler(players[x], blockMapRow[currentMap]);
			collisionHandler(players[x], blockMapRow[neighbourMap]);
		}

		//if(currentMapX + mapWidth < 0){
		if (currentMapX + mapWidth < players[0].getPlayerBox().getCenterX()) {
			int temp = currentMap;
			currentMap = neighbourMap;
			neighbourMap = temp;

			float temp2 = currentMapX;
			currentMapX = neighbourMapX;
			neighbourMapX = temp2;
		}

		if (neighbourMapX + mapWidth <= 0) {
			// if(neighbourMapX + mapWidth <= 0){
			neighbourMap = rnd.nextInt(mapCount - 1);
			while (neighbourMap == currentMap) {
				neighbourMap = rnd.nextInt(mapCount - 1);
			}

			neighbourMapX = mapWidth;

			loopCount++;

			if (loopCount >= levelLength) {
				if (gameSpeed + speedAcc <= 10) {
					currentLevel++;
					gameSpeed += speedAcc;
					System.out.println("Currengamespeed: " + gameSpeed);
				}
				loopCount = 0;
			}
		}

		blockMapRow[currentMap].updateBlockMap(currentMapX, true);
		blockMapRow[neighbourMap].updateBlockMap(neighbourMapX, true);

	}
	
}