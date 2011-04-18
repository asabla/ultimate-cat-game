package katt;

import java.io.IOException;
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
	public static float gameSpeed = 1f;

	private BlockMap mapX1, mapX2;
	private Player1 mr;
	private float bgSpeed1 = 0.12f;
	private float bgSpeed2 = 0.55f;
	private int screenWidth = 640;
	private Polygon collisonBlock;
	private ParticleSystem smoke;
	private final float speedAcc = 0.2f;
	private StateBasedGame game;
	public int ID;
	public static int time;
	public final static float gravity = 2f;
	private PickupObject pointObject;
	private PickupObject lifeObject;
	private GroundEnemy gEnemy;
	private boolean gogo;
    private boolean movePoint;
    private boolean moveLife;

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
	Image pointObjectImage = null;
	Image lifeObjectImage = null;
	Image gEnemyImage = null;

	float mapLayer1 = 0;
	float mapLayer2 = screenWidth;
	float posXLayer1 = 0;
	float posXLayer2 = screenWidth * 2;
	float posXLayer3 = 0;
	float posXLayer4 = screenWidth * 2;
	float posY = 0;
	float startPy;
    float startPx;
    float slutPXv;
    float slutPXh;
    float slutPy;

	public TheGame(int ID) {
		super();

		this.ID = ID;

		frame = new TestFrame();
	}

	/**
	 * Set the start values of the project Read only in start of the project
	 */
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		this.game = game;
		pointObject = new PickupObject();
		lifeObject = new PickupObject(0, 5000, 250);
		gEnemy = new GroundEnemy(1);
		time = 1; // Startar spelet med 1sekund

		// container.setVSync(true);
		container.setTargetFrameRate(150);

		pointObjectImage = new Image(pointObject.getImgLoc());
		lifeObjectImage = new Image("data/Img/object0.png");
		gEnemyImage = new Image(gEnemy.getImgLoc());
		initPlayers();
		initBackgrounds();
		startPy = mr.getPlayerY();
        startPx = mr.getPlayerX();
        slutPy = startPy - 200f;
        slutPXh = startPx + 40f;
        slutPXv = startPx - 40f;

	}

	/**
	 * Inputs and modifications
	 */
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) {
		Input input = container.getInput();
		mr.keyPressed(input);
		smoke.update(delta);

		time += delta; // Tilldelar tid till variabeln
		mr.setPlayerScore(mr.getPlayerScore() + (time / 1000 + (int) gameSpeed)
				/ 2); // Tilldelar poäng till spelaren

		// Update hitbox location
		mr.getPlayerBox().setY(mr.getPlayerY());
		mr.getPlayerBox().setX(mr.getPlayerX());

		// if outside the window print dead
		if (playerDropOut(mr)) {
			System.out.println("Dead");
			mr.deadPlayer();
		}

		try {
			// Check if any collision is made && no jumping is active
			if (!entityCollisionWith(mr.getPlayerBox(), mapX1)
					&& !entityCollisionWith(mr.getPlayerBox(), mapX2)
					&& !mr.getJumping().isAlive()) {
				mr.beginFall();
			}
		} catch (SlickException e) {
			e.printStackTrace();
		}

		collisionHandler(mr, mapX1);
		collisionHandler(mr, mapX2);

		

		mapX1.moveBlockMap();
		mapX2.moveBlockMap();

		pointObject.upDateXPos();
		lifeObject.upDateXPos();
		gEnemy.upDateXPos();
		updateAllDrawPosition();
		mapX1.updateBlockMap(mapLayer2); // FUL-KOD!!
		mapX2.updateBlockMap(mapLayer1);

		if (input.isKeyPressed(Input.KEY_ESCAPE)) {
			game.enterState(StateHandler.menu);
		}

		if (pointObjectPickup(mr)) {
			if (StateHandler.soundsOn) {
				StateHandler.soundBank.playSound("crush");
			}
			
			mr.setPlayerScore(mr.getPlayerScore() + pointObject.getValue());
			movePoint = true;
			pointObject = new PickupObject();
			
			try {
				pointObjectImage = new Image(pointObject.getImgLoc());
			} catch (SlickException e) {
			}
		}
		if (lifeObjectPickup(mr)) {
			if (StateHandler.soundsOn) {
				StateHandler.soundBank.playSound("happy");
			}
			moveLife = true;
			mr.gainPlayerLife();
			lifeObject.newObjectPosLong();
		}
		
		if (gEnemyHit(mr)){
			if (StateHandler.soundsOn){
				StateHandler.soundBank.playSound("crash");
			}
			mr.loosePlayerLife();
		}

	}

	/**
	 * Draws everything in the container of the game
	 */
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) {
		drawBackgrounds();
		renderMaps();
		mr.updateAnimationSpeed();
		System.out.println("Y "+startPy  + "X "+startPx);
		// om Movepoint eller lifePoint är true så kör denna igång och flyttar poängobjektet uppåt tills den nåt rätt Y pos = slutPy.
		// movePoint är true om katten tagit ett objekt,moveLife är true om katten tagit ett liv, 
				String lifePoint = "";
				if(movePoint || moveLife){
					if(movePoint)
				{ 
						lifePoint = "" + pointObject.getValue();
				}
					if(moveLife){
						lifePoint = "1 UP";
					}
				
				
				}
				{
						// gogo är en koll att så den fortsätter tills den nåt max pos på X
						// kollar om den har nått slutPy
	             	 if(startPy == slutPy){
	             		g.drawString(lifePoint, startPx, slutPy);
	             		movePoint = false;
	             		moveLife = false;
	             		startPy = 400f;
	                    startPx = 200f;
	                    
	                  }
	             	 // Kollar att den den inte gått för mycket åt höger, körs tills den når slutPXH = den högra gränsen på X
	             	 else if(startPx > slutPXh || (gogo)){
	             		
	             	g.drawString(lifePoint,startPx --,(startPy --));
	             	if(startPx > slutPXv){ gogo = true;}
	             	else{
	             		gogo = false;
	             		}
     	             	 }
	             	// Kollar att den den inte gått för mycket åt vänster, körs tills den når slutPXv = den vänstra gränsen på X
	             	 else if(startPx < slutPXv || (!gogo)){
	             		g.drawString(lifePoint ,startPx ++,(startPy --));
	                 	if(startPx < slutPXh){ gogo = false;}
	                 	else{ 
	                 		gogo = true;
	                 		}
	             	}
	             	}
	     // draw chosen player with current animation and current coordinate
		((ConfigurableEmitter) smoke.getEmitter(0)).setPosition(
				mr.getPlayerX() + 30, mr.getPlayerY() + 15);
		smoke.render();
	
		g.drawAnimation(mr.getCurrentAnimation(), mr.getPlayerX(), mr.getPlayerY());
		
		g.drawString("Liv: " + mr.getPlayerLife(), 10, 30);
		g.drawString("Poäng: " + mr.getPlayerScore(), 100, 10);
		g.drawString("Tid: " + TheGame.time / 1000 + "sec", 100, 30);
		
		g.drawImage(pointObjectImage, pointObject.getxPos(), pointObject.getyPos());
		g.drawImage(lifeObjectImage, lifeObject.getxPos(), lifeObject.getyPos());
		g.drawImage(gEnemyImage, gEnemy.getxPos(), gEnemy.getyPos());


		// mapX1.drawHitBox(g, mapLayer1);
		// mapX2.drawHitBox(g, mapLayer2);
	}

	/**
	 * Controls if target player collides with anything in chosen blockmap
	 * returns a boolean
	 */
	private boolean entityCollisionWith(Shape shp, BlockMap bMap)
			throws SlickException {
		for (int i = 0; i < bMap.getEntities().size(); i++) {
			Block entity1 = bMap.getEntities().get(i);
			if (shp.intersects(entity1.getPoly())) {
				collisonBlock = entity1.getPoly();
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 */
	private void updateAllDrawPosition() {
		// Update maplayer and background Y-Position
		posXLayer1 = updateGraphicElement(posXLayer1, bgSpeed1, screenWidth * 2);
		posXLayer2 = updateGraphicElement(posXLayer2, bgSpeed1, screenWidth * 2);
		posXLayer3 = updateGraphicElement(posXLayer3, bgSpeed2, screenWidth * 2);
		posXLayer4 = updateGraphicElement(posXLayer4, bgSpeed2, screenWidth * 2);

		mapLayer1 -= gameSpeed;
		mapLayer2 -= gameSpeed;

		// Move maplayers and background if outside the window

		// Add to the speed value each loop
		if (mapLayer1 <= -screenWidth) {
			mapLayer1 = screenWidth;
			mapX1.clearBlocks();
			mapX1.loadBlocks();
			if (gameSpeed - 0.2f < 10) {
				gameSpeed += speedAcc;
				System.out.println("Currengamespeed: " + gameSpeed);
			}
		}
		if (mapLayer2 <= -screenWidth) {
			mapLayer2 = screenWidth;
			mapX2.clearBlocks();
			mapX2.loadBlocks();
			if (gameSpeed - 0.2f < 10) {
				gameSpeed += speedAcc;
				System.out.println("Currengamespeed: " + gameSpeed);
			}
		}
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
		bgLayer1.draw(posXLayer1, posY);
		bgLayer2.draw(posXLayer2, posY);
		bgLayer3.draw(posXLayer3, posY);
		bgLayer4.draw(posXLayer4, posY);
	}

	private void renderMaps() {
		mapX1.getTmap().render((int) mapLayer2, (int) posY);
		mapX2.getTmap().render((int) mapLayer1, (int) posY);

	}

	private void collisionHandler(Player1 pl, BlockMap map) {
		try {
			if (entityCollisionWith(pl.getPlayerBox(), map)) {
				if (pl.getPlayerX() + 51 < collisonBlock.getX()
						&& !(pl.getPlayerY() + 51 < collisonBlock.getY())) {
					System.out.println("Wall");
					pl.setPlayerX(pl.getPlayerX() - 2 * gameSpeed);
					// mr.getPlayerBox().setX(mr.getPlayerX());
				}
				if (pl.getPlayerY() + 50 > collisonBlock.getY()) {
					System.out.println("Floor");
					pl.setOnGround(true);

					// Put Player above the collisionBlock
					pl.setPlayerY(collisonBlock.getY() - 50);

					// Reset the GravityEffect
					pl.setGravityEffect(gravity);

					// mr.getPlayerBox().setY(mr.getPlayerY());

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

	private void initPlayers() {
		mr = new Player1(200, 400, "data/Img/cat2.png", Input.KEY_UP, 3);
		mr.setCurrentAnimation(mr.getRun());
		mr.setOnGround(false);
	}

	private void initBackgrounds() throws SlickException {
		bgSky = new Image("data/Img/img_bg_sky.png");
		bgLayer1 = new Image("data/Img/img_bg_layer1.png");
		bgLayer2 = new Image("data/Img/img_bg_layer1.png");
		bgLayer3 = new Image("data/Img/img_bg_layer2.png");
		bgLayer4 = new Image("data/Img/img_bg_layer2.png");
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return ID;
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
		container.setTargetFrameRate(150);

		// maps
		mapX1 = new BlockMap("data/Img/room3.tmx");
		mapX2 = new BlockMap("data/Img/room2.tmx");

		initPlayers();
		initBackgrounds();

	}
	
	@Override
	public void leave(GameContainer container, StateBasedGame game) throws SlickException {
		if(StateHandler.bgm.playing())
			StateHandler.bgm.stop();
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
	
	public boolean gEnemyHit(Player1 pl){
		if (pl.getPlayerBox().intersects(gEnemy.getRectangle()) && !gEnemy.isCollided()) {
			gEnemy.setCollided(true);
			return true;
		} else {
			return false;
		}
	}

}