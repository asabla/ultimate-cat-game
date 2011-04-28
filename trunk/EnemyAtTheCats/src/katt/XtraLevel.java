package katt;


import java.util.ArrayList;

import java.util.Random;

import org.newdawn.slick.Color;


import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import org.newdawn.slick.particles.ConfigurableEmitter;
import org.newdawn.slick.particles.ParticleIO;
import org.newdawn.slick.particles.ParticleSystem;

public class XtraLevel extends TheGame {
	
	public static float normalGameSpeed;

	private Space space;

	private Database db;


	private ArrayList<FlyingEnemy> fEnemys1;
	private int previousLoop1;

	

	private ParticleSystem rocketFire;
	

	public XtraLevel(int ID) {
		super(ID);
		
	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		
		super.init(container, game);
		
		// Moving background 1
		backgrounds[0] = new Image("data/Img/Space1.png");
		backgrounds[1] = new Image("data/Img/Space2.png");
		// Moving background 2
		backgrounds[2] = new Image("data/Img/Planets1.png");
		backgrounds[3] = new Image("data/Img/Planets2.png");
		
		// BlockMaps for XtraLevel
		blockMapRow = new BlockMap[5];
		blockMapRow[0] = new BlockMap("data/Img/xtraroom1.tmx");
		blockMapRow[1] = new BlockMap("data/Img/xtraroom2.tmx");
		blockMapRow[2] = new BlockMap("data/Img/xtraroom1.tmx");
		blockMapRow[3] = new BlockMap("data/Img/xtraroom2.tmx");
		blockMapRow[4] = new BlockMap("data/Img/xtraroom1.tmx");
		players[0].setCurrentAnimation(players[0].getRocket());

		

		fEnemys1 = new ArrayList<FlyingEnemy>();
		


		

//		// Change "Enemy"-objects
//		gEnemy = new GroundEnemy(2); // SpaceEnemy (2)
//		gEnemyImage = new Image("data/Img/xtraEnemy.png");
	}
	

	private void generateEnemys() {
		Random randomGenerator = new Random();
		
		int numberOfEnemys = getCurrentLevel(); // Minimum number of Enemys equal to level
		int numberOfExtraEnemys = randomGenerator.nextInt(getCurrentLevel()); // Extra Enemys
		int totalEnemys = numberOfEnemys + numberOfExtraEnemys; // Total number of Enemys to add
		
		for (int i = 0; i < totalEnemys; i++) {
			fEnemys1.add(new FlyingEnemy(randomGenerator.nextInt(13))); // Add random type of Enemy

		}
		
	}

	
	
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) {
		Input input = container.getInput();
		rocketFire.update(delta);
		
		if(getCurrentLevel() == 2) { 
			StateHandler.paused = true;
			StateHandler.bonusCompleted = true;
			StateHandler.bonus = false;
			game.enterState(StateHandler.PAUSE);
			db.sendUserValues("Joppe@mm.se", "Jihooo");
		}
		

		if (input.isKeyPressed(Input.KEY_ESCAPE)) {
			players[0].setOnGround(true);
			game.enterState(StateHandler.PAUSE);
		}
		if (input.isKeyPressed(Input.KEY_I)) {
			players[0].setOnGround(true);
			if(game.getCurrentState() == game.getState(StateHandler.THEGAME)){
				game.enterState(StateHandler.XTRALEVEL);
				players[0].setSpaceControl(true);
			}
			if(game.getCurrentState() == game.getState(StateHandler.XTRALEVEL)){
				game.enterState(StateHandler.THEGAME);
				players[0].setSpaceControl(false);
			}
		}

		setTime(getTime() + delta); // Tilldelar tid till variabeln

		// Enemys Generation if new loop detected
		if(getLoopCount() != previousLoop1) {
			generateEnemys();
		}
		
			previousLoop1 = getLoopCount();
		
		// Enemy Update Positions
		for (FlyingEnemy fEnemy : fEnemys1) {
			fEnemy.upDateXPos();
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
		
		for (int x = 0; x < getPlayerCount(); x++) {
			players[x].keyPressed(input);

			// Update hitbox location
			players[x].getPlayerBox().setY(players[x].getPlayerY());
			players[x].getPlayerBox().setX(players[x].getPlayerX());
			
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

				StateHandler.bonus = false;
				
				StateHandler.soundBank.playSound("spacedrop");
				game.enterState(StateHandler.SPACE); // Return to game
				
				
				

			}


			for (FlyingEnemy fEnemy : fEnemys1) {

				if (objectCollide(players[x],fEnemy.getRectangle())) {
					input.clearKeyPressedRecord();
					if (StateHandler.soundsOn) {
						StateHandler.soundBank.playSound("crash");
					}
					System.out.println("Träffade fiende");
					players[x].setOnGround(true);
					StateHandler.bonus = false;
					StateHandler.soundBank.playSound("spacedrop");
					game.enterState(StateHandler.SPACE);

				}

			}
			
		}
		
	}

	@Override
	protected void collisionHandler(Player1 pl, BlockMap map) {
		try {
			if (entityCollisionWith(pl.getBottomHitBox(), map)) {
				System.out.println("Floor");
				pl.setPlayerY(pl.getPlayerY()-100);
				pl.setOnGround(true);
				//pl.deadPlayer();
			}

			if(entityCollisionWith(pl.getTopHitBox(), map)) {
				System.out.println("Roof");
				pl.deadPlayer();
			}
			
		} catch (SlickException e1){}
		
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) {
				g.setBackground(Color.black);
		drawBackgrounds();
		
		if (game.getState(StateHandler.XTRALEVEL) == game.getCurrentState()) {
			blockMapRow[currentMap].getTmap().render((int) currentMapX,
					(int) posY);
			blockMapRow[neighbourMap].getTmap().render((int) neighbourMapX,
					(int) posY);

			blockMapRow[currentMap].drawHitBox(g, currentMapX);
			blockMapRow[neighbourMap].drawHitBox(g, neighbourMapX);
		}
		
		if (game.getState(StateHandler.XTRALEVEL) == game.getCurrentState()) {
			for (Player1 pl : players) {
				pl.updateAnimationSpeed();
				((ConfigurableEmitter)
						rocketFire.getEmitter(0)).setPosition(( pl.getPlayerX() + 15),
								pl.getPlayerY() + 35);
								rocketFire.render();
				g.drawAnimation(pl.getCurrentAnimation(), pl.getPlayerX(),
						pl.getPlayerY());
		
				
			}
			
			// Draw Enemy-objects
			for (FlyingEnemy fEnemy : fEnemys1) {
			g.drawImage(fEnemy.getfEnemyImage(), fEnemy.getPosX(), fEnemy.getPosY());
			}
			g.drawString("No. of Enemys: " + fEnemys1.size(), 20, 40);
			g.drawString("Level: " + getCurrentLevel(), 20, 70);
			g.getFont().drawString(20, 90, "HejHej", Color.yellow, 20, 100);

			
			
		
			
		}
		
	}




	@Override
	public void enter(GameContainer container, StateBasedGame game)
			throws SlickException {
		
		normalGameSpeed = gameSpeed;
		StateHandler.bonus = true;
		
		db = new Database();
		
		try {
			rocketFire = ParticleIO.loadConfiguredSystem("data/rocketSystem.xml");
		} catch (IOException e) {
			throw new SlickException("Failed to load particle systems", e);
		}
		
		
		// Stop playing CatGame normal music
		if (StateHandler.bgm.playing()) {
			StateHandler.bgm.stop();
		}
		// Start playing SpaceMusic
		if (StateHandler.musicOn) {
			StateHandler.spaceMusic.loop(); 
		}
		
		if(StateHandler.paused && StateHandler.bonusCompleted) {
			game.enterState(StateHandler.THEGAME);
		}
		
		if (StateHandler.paused) {
			StateHandler.paused = false;
			
			int playerX = (int) players[0].getPlayerX();
			int playerY = (int) players[0].getPlayerY();
			int playerLife = players[0].getPlayerlife();
					

			blockMapRow[currentMap].updateBlockMap(currentMapX, true);

			players = new Player1[getPlayerCount()];
			
			players[0] = new Player1(playerX, playerY, "data/Img/cat.png",
					Input.KEY_UP, playerLife);
			players[0].setCurrentAnimation(players[0].getRocket());
			players[0].setSpaceControl(true);
			
		} 
		else { // Entering the XtraLevel
			
			setCurrentLevel(1);
			setLevelLength(5);
			setLoopCount(0);

			previousLoop1 = 0;
			
			generateEnemys();



		
			TheGame.gameSpeed = 2f;
			currentMap = 0;
			neighbourMap = 1;

			currentMapX = 0;
			neighbourMapX = getMapWidth();

			setPlayerCount(1);

			setTime(1); // Startar spelet med 1sekund

			players = new Player1[getPlayerCount()];
			
			players[0] = new Player1(200, 400, "data/Img/cat.png", Input.KEY_UP, 3); // TODO Change player sheet
			blockMapRow[currentMap].updateBlockMap(currentMapX, true);
			players[0].setSpaceControl(true);
			players[0].setCurrentAnimation(players[0].getRocket());
			// players[1] = new Player1(200, 400, "data/Img/cat2.png",
			// Input.KEY_W, 3);
		}
		players[0].setPlayerX(240);
		players[0].setPlayerY(200);
	}

	@Override
	public void leave(GameContainer container, StateBasedGame game)
			throws SlickException {		
		
		if (StateHandler.spaceMusic.playing()) {
			StateHandler.spaceMusic.stop();
		}
//		StateHandler.bonus = true;
		players[0].setSpaceControl(false);
		
	}
	
	
	
	

}
