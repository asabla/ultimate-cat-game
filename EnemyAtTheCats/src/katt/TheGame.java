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

	protected Image[] backgrounds;
	protected float[] backgroundPos;
	protected float[] backgroundSpeed;
	private Space space; 
	private int mapCount;
	private int levelLength;
	private int currentLevel;
	private int loopCount;
	protected BlockMap[] blockMapRow;
	private BlockMap[] blockMapRowXtraLevel;
	protected float currentMapX;
	protected float neighbourMapX;
	protected int currentMap;
	protected int neighbourMap; // Både den som är innan currentmap och när
								// currentmap är på väg ut ur banan
	private Random rnd;
	private Database db;
	private String topScore;

	protected Player1[] players;
	private int playerCount;
	private boolean spaceRide = false;
	private GroundEnemy gEnemy;
	private boolean gogo;
	private boolean movePoint;
	private boolean moveLife;
	
	//Raketdelarna
	private RocketPart helmet;
	private RocketPart rocket;
	private RocketPart boots;
	//en array som ska hålla tagna raketdelar
	private RocketPart[] rocketParts;
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
	
	Image helmetImg = null;
	Image rocketImg = null;
	Image bootsImg = null;



	public static float posY = 0;
	
	// Poäng som flyger igenom luften när poängobjekt är tagen
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
		setCurrentLevel(1); // Starting Level
		setLevelLength(5); // Number of Maps before levelUp
		setLoopCount(0); // Number of Maps loaded

		// topScore = db.getSingleHighscoreResult();

		backgroundPos = new float[4];
		backgroundPos[0] = 0;
		backgroundPos[1] = 1280;
		backgroundPos[2] = 0;
		backgroundPos[3] = 1280;

		backgrounds = (new Image[4]);
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

		mapCount = 5; // Number of BlockMaps in BlockMapRow (Array of BlockMaps)

		currentMap = 0;
		neighbourMap = 1;

		currentMapX = 0;
		neighbourMapX = getMapWidth();

		blockMapRow = new BlockMap[mapCount];
		blockMapRow[0] = new BlockMap("data/Img/room.tmx");
		blockMapRow[1] = new BlockMap("data/Img/room2.tmx");
		blockMapRow[2] = new BlockMap("data/Img/room3.tmx");
		blockMapRow[3] = new BlockMap("data/Img/room4.tmx");
		blockMapRow[4] = new BlockMap("data/Img/room5.tmx");
		
		

		setPlayerCount(1);

		players = new Player1[getPlayerCount()];
		players[0] = new Player1(200, 400, "data/Img/cat.png",
				Input.KEY_UP, 3);
		// players[1] = new Player1(200, 400, "data/Img/cat2.png", Input.KEY_W,
		// 3);

		pointObject = new PickupObject();
		lifeObject = new PickupObject(0, 5000, 250);
		
		helmet = new RocketPart(7, 2800, 350);
		rocket = new RocketPart(8, 13000, 260);
		boots = new RocketPart(9, 20000, 320);
		
		rocketParts = new RocketPart[]{helmet, rocket, boots};
		bonusPlayed = false;

		setTime(1); // Startar spelet med 1sekund

		// container.setVSync(true);
		container.setTargetFrameRate(150);

		pointObjectImage = new Image((String) pointObject.getImgLoc());
		lifeObjectImage = new Image("data/Img/object0.png");
		
		helmetImg = new Image("data/Img/object7.png");
		rocketImg = new Image("data/Img/object8.png");
		bootsImg = new Image("data/Img/object9.png");

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
			game.enterState(StateHandler.PAUSE);
		}

		if (input.isKeyPressed(Input.KEY_I)) {
			players[0].setOnGround(true);
			if(game.getCurrentState() == game.getState(StateHandler.THEGAME)){
				players[0].setSpaceControl(true);
				StateHandler.soundBank.playSound("spaceflight");
				game.enterState(StateHandler.SPACE);
			}
			if(game.getCurrentState() == game.getState(StateHandler.XTRALEVEL)){
				game.enterState(StateHandler.THEGAME);
				players[0].setSpaceControl(false);
			}
		}

//		smoke.update(delta);
		setTime(getTime() + delta); // Tilldelar tid till variabeln
		
		if(input.isKeyPressed(Input.KEY_A)){
			game.enterState(StateHandler.SPACE);
			spaceRide = true;
			StateHandler.soundBank.playSound("spaceflight");
		}
		if(input.isKeyPressed(Input.KEY_U)){
			game.enterState(StateHandler.XTRALEVEL);
			spaceRide = true;
			StateHandler.soundBank.playSound("spaceflight");
		}
		
		time += delta; // Tilldelar tid till variabeln


		pointObject.upDateXPos();
		lifeObject.upDateXPos();
		gEnemy.upDateXPos();
		
		helmet.upDatePartPos();
		rocket.upDatePartPos();
		boots.upDatePartPos();
		
		

		mapHandler();

		for (int x = 0; x < 4; x++) {
			backgroundPos[x] = updateGraphicElement(backgroundPos[x],
					backgroundSpeed[x], 1280);
		}

		// *********************** Player ***********************
		int spriteSizeX = 20;
		float currentPlX = players[0].getPlayerX();
		float currentPlY = players[0].getPlayerY();
		
		for (int x = 0; x < getPlayerCount(); x++) {
			players[x].keyPressed(input);
			players[x].setPlayerScore(
					players[x].getPlayerScore()+ 
					(getTime() / 1000 + (int) gameSpeed) / 2);

			// Update hitbox location
			players[x].getPlayerBox().setY(players[x].getPlayerY());
			players[x].getPlayerBox().setX(players[x].getPlayerX());
			
			//Dubbla metoder här nedanför?? setLocation gör väl samma sak, fast i en metod
			int hitBoxPushY = 10;
			   
			   int spriteSizeY = 35;
			
			  players[x].getBottomHitBox().setX(players[x].getPlayerX()+spriteSizeX);
			   players[x].getBottomHitBox().setY(players[x].getPlayerY() + spriteSizeY - 5);
			   
			   players[x].getTopHitBox().setX(players[x].getPlayerX()+spriteSizeX);
			   players[x].getTopHitBox().setY(players[x].getPlayerY()+ hitBoxPushY);
			   
			   players[x].getFrontHitBox().setX(players[x].getPlayerX()+ 25 + spriteSizeX);
			   players[x].getFrontHitBox().setY(players[x].getPlayerY()+ hitBoxPushY + 5);

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
				game.enterState(StateHandler.DEADMENU);
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
				game.enterState(StateHandler.GAMEOVER);
			}

			try {
				// Check if any collision is made && no jumping is active && no falling is active
				if (!entityCollisionWith(players[x].getPlayerBox(),blockMapRow[currentMap]) 
						&& !players[x].getJumping().isAlive() 
						&& !players[x].getFalling().isAlive())
				{
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
				game.enterState(StateHandler.DEADMENU);
			}
			if (rocketPartTaken(players[x])){
				StateHandler.soundBank.playSound("harp1");				
				if(rocketAssembled() && !bonusPlayed){
					game.enterState(StateHandler.SPACE);
					spaceRide = true;
					
					StateHandler.soundBank.playSound("spaceflight");
					
					game.enterState(StateHandler.SPACE);
					spaceRide = true;
					bonusPlayed = true;
				}
			}
			
//			if(rocketAssembled() && !bonusPlayed){
//
//				game.enterState(StateHandler.SPACE);
//				spaceRide = true;
//				
//				StateHandler.soundBank.playSound("spaceflight");
//				game.enterState(StateHandler.SPACE);
//				spaceRide = true;
//
//				System.err.println("Bonus activated!");
//				bonusPlayed = true;
//			}
		}
		
	}

	/**
	 * Draws everything in the container of the game
	 */
	public void render(GameContainer container, StateBasedGame game, Graphics g) {
		drawBackgrounds();

		if (game.getState(StateHandler.THEGAME) == game.getCurrentState()) {

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

		
		

			g.drawImage(pointObjectImage, pointObject.getxPos(),
					pointObject.getyPos());
			g.drawImage(lifeObjectImage, lifeObject.getxPos(),
					lifeObject.getyPos());

			g.drawImage(gEnemyImage, gEnemy.getPosX(), gEnemy.getPosY());

			
			g.drawImage(helmetImg, helmet.getxPos(), helmet.getyPos());
			g.drawImage(rocketImg, rocket.getxPos(), rocket.getyPos());
			g.drawImage(bootsImg, boots.getxPos(), boots.getyPos());	


		}

		// draw chosen player with current animation and current coordinate

		g.drawString("Player 1", 10, 30);
		g.drawString("Liv: " + players[0].getPlayerLife(), 10, 45);
		g.drawString("Poäng: " + players[0].getPlayerScore(), 10, 60);

		g.drawString("Topscore: " + topScore, 150, 10);

		// g.drawString("Player 2", 500, 30);
		// g.drawString("Liv: " + players[1].getPlayerLife(), 500, 45);
		// g.drawString("Poäng: " + players[1].getPlayerScore(), 500, 60);

		g.drawString("Tid: " + this.getTime() / 1000 + "sec", 450, 450);
		g.drawString("Level: " + getCurrentLevel(), 550, 450);
		
		//Ritar ut bonusrutor, och de raketdelar som hittils är tagna
		g.drawString("BONUS", 550, 40);
		g.drawRect(550, 10, 28, 28);
		g.drawRect(590, 10, 28, 28);
		g.drawRect(630, 10, 28, 28);
		
		if(helmet.isTaken()){
			g.drawImage(helmetImg, 550, 10);
		}
		if(rocket.isTaken()){
			g.drawImage(rocketImg, 590, 10);
		}		
		if(boots.isTaken()){
			g.drawImage(bootsImg, 630, 10);
		}
		g.draw(players[0].getBottomHitBox());
		g.draw(players[0].getTopHitBox());
		g.draw(players[0].getFrontHitBox());
		
		if (game.getState(StateHandler.THEGAME) == game.getCurrentState() || game.getState(StateHandler.XTRALEVEL) == game.getCurrentState()) {

			for (Player1 pl : players) {
				pl.updateAnimationSpeed();
				g.drawAnimation(pl.getCurrentAnimation(), pl.getPlayerX(),
						pl.getPlayerY());

				
			}
		}
		}
	


	@Override
	public void enter(GameContainer container, StateBasedGame game)
			throws SlickException {
		
		players[0].setCurrentAnimation(players[0].getRun());

		if (StateHandler.musicOn) {
			StateHandler.bgm.loop();
		}

		if (StateHandler.paused) {
			StateHandler.paused = false;
			
			int playerX = (int) players[0].getPlayerX();
			int playerY = (int) players[0].getPlayerY();
			int playerLife = players[0].getPlayerlife();
			long playerScore = players[0].getPlayerScore();
			

			blockMapRow[currentMap].updateBlockMap(currentMapX, true);

			players = new Player1[getPlayerCount()];
			players[0] = new Player1(playerX, playerY, "data/Img/cat.png",
					Input.KEY_UP, playerLife);
			players[0].setPlayerScore(playerScore);
			
			players[0].beginFall();
			
		} 
		else if (StateHandler.bonus) {
			
			gameSpeed = XtraLevel.normalGameSpeed;
			spaceRide = false;
			
			int playerX = (int) players[0].getPlayerX();
			int playerY = (int) players[0].getPlayerY();
			int playerLife = players[0].getPlayerlife();
			long playerScore = players[0].getPlayerScore();
			

			blockMapRow[currentMap].updateBlockMap(currentMapX, true);

			players = new Player1[getPlayerCount()];
			players[0] = new Player1(playerX, playerY, "data/Img/cat.png",
					Input.KEY_UP, playerLife);
			players[0].setPlayerScore(playerScore);
			
			players[0].beginFall();
			
		}else if (StateHandler.dead) {
			pointObject.newObjectPos();
			gEnemy.newObjectPos();
			lifeObject.newObjectPos();
			setLoopCount(0);

			currentMap = 0;
			neighbourMap = 1;

			currentMapX = 0;
			neighbourMapX = getMapWidth();

			int playerLife = players[0].getPlayerlife();
			long playerScore = players[0].getPlayerScore();

			blockMapRow[currentMap].updateBlockMap(currentMapX, true);

			players = new Player1[getPlayerCount()];
			players[0] = new Player1(200, 400, "data/Img/cat.png",
					Input.KEY_UP, playerLife);
			players[0].setPlayerScore(playerScore);
		}

		else { // Nytt Spel

			setCurrentLevel(1);
			setLevelLength(5);
			setLoopCount(0);
			gameSpeed = 2f;
			currentMap = 0;
			neighbourMap = 1;

			currentMapX = 0;
			neighbourMapX = getMapWidth();

			setPlayerCount(1);

			setTime(1); // Startar spelet med 1sekund

			players = new Player1[getPlayerCount()];
			players[0] = new Player1(200, 400, "data/Img/cat.png", Input.KEY_UP, 3);
			blockMapRow[currentMap].updateBlockMap(currentMapX, true);
			// players[1] = new Player1(200, 400, "data/Img/cat2.png",
			// Input.KEY_W, 3);
			
			//nollställer raketdelarna
			for(int i = 0; i < 3; i++){
				rocketParts[i].setTaken(false);
			}
			helmet.setxPos(2800);
			rocket.setxPos(14000);
			boots.setxPos(20000);
			
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
	protected boolean entityCollisionWith(Shape shp, BlockMap bMap)
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
	protected float updateGraphicElement(float layerPos, float moveSpeed,
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
	public void drawBackgrounds() {
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
	protected void collisionHandler(Player1 pl, BlockMap map) {
		try {
			if (entityCollisionWith(pl.getBottomHitBox(), map)) {
				pl.setPlayerY(collisonBlock.getMinY() - 50);
				pl.setOnGround(true);
				pl.setGravityEffect(gravity);
				System.out.println("Floor");
			}

			if(entityCollisionWith(pl.getTopHitBox(), map)) {
				pl.setPlayerY(collisonBlock.getMaxY());
				System.out.println("Roof");
				pl.setOnGround(true);
				pl.beginFall();
				
			}
			if(entityCollisionWith(pl.getFrontHitBox(), map) && !entityCollisionWith(pl.getBottomHitBox(), map)) {
				System.out.println("Wall");
				pl.setPlayerX(collisonBlock.getMinX() - 50);
			}
			
//			if (entityCollisionWith(pl.getPlayerBox(), map)) {
//				
//				if (wallHit(map, pl))
//				{
//					System.out.println("Wall");
//					pl.setPlayerX(pl.getPlayerX() - 2 * gameSpeed);
//				}
//				if (pl.getPlayerY() + 50 > collisonBlock.getY()
//						&& !wallHit(map, pl))
//				{
//					System.out.println("Floor");
//					pl.setOnGround(true);
//
//					// Put Player above the collisionBlock
//					pl.setPlayerY(collisonBlock.getY() - 50);
//
//					// Reset the GravityEffect
//					pl.setGravityEffect(gravity);
//				}
//			}
		} catch (SlickException e1){}

	}

	/**
	 * Returns true if player is outside the screen
	 * @param pl Target player
	 * @return true if outside the screen, false if not
	 * @author Jonathan B
	 */

	protected boolean playerDropOut(Player1 pl) {
		return pl.getPlayerBox().getX() < 0 || pl.getPlayerBox().getY() > 480 || pl.getPlayerBox().getY() < -50;

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
	protected boolean objectCollide(Player1 pl, Shape shp) {
		if (pl.getTopHitBox().intersects(shp)
				|| pl.getFrontHitBox().intersects(shp)
				|| pl.getBottomHitBox().intersects(shp)) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * Kontrollerar om alla raketdelar är tagna
	 * @return true om så är fallet
	 */
	private boolean rocketAssembled(){
		if(helmet.isTaken() && 
				rocket.isTaken() &&
				boots.isTaken()){
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * Kontrollerar om en raketdel är tagen
	 * @param pl Target Player
	 * @return true om spelaren kolliderat med en raketdel
	 */
	private boolean rocketPartTaken(Player1 pl){
		for(int i = 0; i < 3; i++){
			if(pl.getPlayerBox().intersects(rocketParts[i].getRectangle())){
				rocketParts[i].setxPos(-100);
				rocketParts[i].setTaken(true);
				return true;
			}
		}
		return false;
	}


	/**
	 * Handels the moving, buffering and looping of maps. Update positions of collisionblocks
	 * @author Jonathan B
	 */
	protected void mapHandler() {
		currentMapX -= gameSpeed;
		neighbourMapX -= gameSpeed;

		for (int x = 0; x < getPlayerCount(); x++) {
			collisionHandler(players[x], blockMapRow[currentMap]);
			collisionHandler(players[x], blockMapRow[neighbourMap]);
		}

		//if(currentMapX + mapWidth < 0){
		if (currentMapX + getMapWidth() < players[0].getPlayerBox().getCenterX()) {
			int temp = currentMap;
			currentMap = neighbourMap;
			neighbourMap = temp;

			float temp2 = currentMapX;
			currentMapX = neighbourMapX;
			neighbourMapX = temp2;
			
		}

		if (neighbourMapX + getMapWidth() <= 0) {
			// if(neighbourMapX + mapWidth <= 0){
			neighbourMap = rnd.nextInt(mapCount - 1);
			while (neighbourMap == currentMap) {
				neighbourMap = rnd.nextInt(mapCount - 1);
			}

			neighbourMapX = getMapWidth();

			setLoopCount(getLoopCount() + 1);


			if (getLoopCount() >= getLevelLength()) {
				if (gameSpeed + speedAcc <= 10) {
					setCurrentLevel(getCurrentLevel() + 1);

								
					gameSpeed += speedAcc;
					System.out.println("Currengamespeed: " + gameSpeed);
				}
				setLoopCount(0);
			}
		}

		blockMapRow[currentMap].updateBlockMap(currentMapX, true);
		blockMapRow[neighbourMap].updateBlockMap(neighbourMapX, true);

	}

	private boolean gEnemyHit(Player1 pl)
	{
		if (pl.getPlayerBox().intersects(gEnemy.getRectangle()) 
				&& !gEnemy.isCollided())
			
		{
			gEnemy.setCollided(true);
			System.out.println("Enemy collision");
			return true;
		} else
		{
			return false;
		}
	}

	// Sätter banan och poäng när katten börjar om efter att ha dött, så 
	// att det inte finns några poäng och att katten börjar på första banan på den leveln katten har dött



	private void newStartAfterCatHasPassedAway()	
	{
		gEnemy.newObjectPos();

		pointObject.newObjectPos();
		gEnemy.newObjectPos();
		pointObject.newObjectPos();		
		lifeObject.newObjectPos();
		setLoopCount(0);

		currentMap = 0;
		neighbourMap = 1;

		currentMapX = 0;
		neighbourMapX = getMapWidth();
	}

	
	public void setPlayerCount(int playerCount) {
		this.playerCount = playerCount;
	}

	public int getPlayerCount() {
		return playerCount;
	}

	public void setCurrentLevel(int currentLevel) {
		this.currentLevel = currentLevel;
	}

	public int getCurrentLevel() {
		return currentLevel;
	}

	public void setLevelLength(int levelLength) {
		this.levelLength = levelLength;
	}

	public int getLevelLength() {
		return levelLength;
	}

	public void setLoopCount(int loopCount) {
		this.loopCount = loopCount;
	}

	public int getLoopCount() {
		return loopCount;
	}

	public void setMapWidth(int mapWidth) {
		this.mapWidth = mapWidth;
	}

	public int getMapWidth() {
		return mapWidth;
	}

	public static void setTime(int time) {
		TheGame.time = time;
	}

	public static int getTime() {
		return time;
	}

	


}