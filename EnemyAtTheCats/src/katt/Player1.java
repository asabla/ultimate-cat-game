package katt;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;

public class Player1 implements Runnable {
	private SpriteSheet sheet;
	private Polygon playerBox;
	private float playerX, playerY;
	private int spriteSizeX, spriteSizeY;
	private Animation run, jump, fall;
	private Animation currentAnimation;
	private int jumps;
	private Thread jumping;
	private boolean isOnGround;
	private int jumpControl;
	private long playerScore;
	private int playerlife;
	// private static boolean threadDone = false;
	// private int score;
	private Thread falling;
	private Rectangle topHitBox, bottomHitBox, frontHitBox;
	private float gravityEffect;
	boolean print = true;

	public Player1(int playerX, int playerY, String pngDir, int jumpControl,
			int plife, long playerScore) {
		super();
		this.playerScore = playerScore;
		this.spriteSizeX = 50;
		this.spriteSizeY = 50;
		this.isOnGround = false;
		this.playerlife = plife;
		gravityEffect = TheGame.gravity;
		jumping = new Thread(this);
		falling = new Thread(this);
		
		this.run = createAnimation(pngDir, spriteSizeX, spriteSizeY, 6, 40,0);
		this.jump = createAnimation(pngDir, spriteSizeX, spriteSizeY + 10, 1, 40, 1);
		this.fall = createAnimation(pngDir, spriteSizeX, spriteSizeY + 10, 1, 40, 2);
		
		this.jumpControl = jumpControl;
		this.currentAnimation = run;

		// Set player start coordinate
		this.playerX = playerX;
		this.playerY = playerY;

		// Set player hitbox
		playerBox = new Polygon(new float[] { playerX, playerY,
				playerX + spriteSizeX, playerY, playerX + spriteSizeX,
				playerY + spriteSizeY, playerX, playerY + spriteSizeY });

		this.topHitBox = createRectangle(playerX, playerY, 25, 5);
		this.bottomHitBox = createRectangle(playerX, 35 + playerY, 25, 20);
		this.frontHitBox = createRectangle(spriteSizeX - 5, 25 + playerY, 5, 30);
	}

	public Player1(int playerX, int playerY, String pngDir, int jumpControl,
			int plife) {
		super();
		this.playerScore = 0;
		this.spriteSizeX = 50;
		this.spriteSizeY = 50;
		this.isOnGround = false;
		this.playerlife = plife;
		gravityEffect = TheGame.gravity;
		jumping = new Thread(this);
		falling = new Thread(this);
		
		// Load all animations
		this.run = createAnimation(pngDir, spriteSizeX, spriteSizeY, 6, 40,0);
		this.jump = createAnimation(pngDir, spriteSizeX, spriteSizeY + 10, 1, 40, 1);
		this.fall = createAnimation(pngDir, spriteSizeX, spriteSizeY + 10, 1, 40, 2);
		this.jumpControl = jumpControl;
		this.currentAnimation = run;

		// Set player start coordinate
		this.playerX = playerX;
		this.playerY = playerY;

		// Set player hitbox
		playerBox = new Polygon(new float[] { playerX, playerY,
				playerX + spriteSizeX, playerY, playerX + spriteSizeX,
				playerY + spriteSizeY, playerX, playerY + spriteSizeY });

		this.topHitBox = createRectangle(playerX, playerY, 25, 5);
		this.bottomHitBox = createRectangle(playerX, 35 + playerY, 25, 20);
		this.frontHitBox = createRectangle(spriteSizeX - 5, 25 + playerY, 5, 30);
	}

	/**
	 *Create a player hitbox
	 *@param xPos Set shape start x-coordinate
	 *@param yPos Set shape start y-coordinate
	 *@param xWidth Set shape width
	 *@param yWidth Set shape height
	 *@return new shape
	 *@author Jonathan B, Henrik
	 */
	private Rectangle createRectangle(int xPos, int yPos, int xWidth, int yWidth) {
		return new Rectangle(xPos, yPos, xWidth, yWidth);
	}


	/**
	 *Reads image and split it into frames that is added to an Animation object and returned
	 *@param fileDirectory The directory of the spritesheet
	 *@param sSizeX The width of each animation frame
	 *@param sSizeY The height of each animation frame
	 *@param frameCount Number of frames in target animation
	 *@param speed The delay between frames
	 *@param animationYPos Sets where in the image the animation starts
	 *@return new Animation
	 *@author Jonathan B
	 */
	private Animation createAnimation(String fileDirectory, int sSizeX, 
			int sSizeY, int frameCount, float speed, int animationYPos) {
		
		return addFramesToAnimation(createSheet(fileDirectory, sSizeX, sSizeY), frameCount, speed, animationYPos);
	}
	
	/**
	 *Loads each frame in the SpriteSheet and store it each fram in a animation Object
	 *@param sht Target SpriteSheet
	 *@param frameCount Number of frames in animation
	 *@param speed The delay between frames
	 *@return animationYPos where in the SpriteSheet the animation starts
	 *@author Jonathan B  
	 */
	private Animation addFramesToAnimation(SpriteSheet sht, int frameCount, float speed, int animationYPos){
		Animation tempAnim = new Animation();
		for (int frame = 0; frame < frameCount; frame++) {
			tempAnim.addFrame(sht.getSprite(animationYPos, frame), (int) speed);
		}
		return tempAnim;
		
	}
	
	/**
	 *Convert png image to a SpriteSheet object
	 *@param fileDir The directory of the image
	 *@param frameWidth The width of each frame in the sheet
	 *@param frameHeight The height of each frame in the sheet
	 *@return New SpriteSheet object
	 *@author Jonathan B  
	 */
	private SpriteSheet createSheet(String fileDir, int frameWidth, int frameHeight){
		// load sprite sheet and put in list
		try {
			return sheet = new SpriteSheet(fileDir, frameWidth, frameHeight);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public void keyPressed(Input input) {

		// UP

		if (input.isKeyPressed(jumpControl)) {
			setOnGround(false);
			if (jumps < 2) {
				jumps++;
				jumping = new Thread(this);
				jumping.start();
				if (StateHandler.soundsOn) {
					StateHandler.soundBank.playSound("jump");
				}

			}
		}
		// UP-REPEAT
		if (input.isKeyDown(jumpControl)) {
			if (!jumping.isAlive() && jumps < 2) {
				jumps++;
				jumping = new Thread(this);
				jumping.start();
				if (StateHandler.soundsOn) {
					StateHandler.soundBank.playSound("jump");
				}
				setOnGround(false);
				if (isOnGround) {
					setOnGround(true);

				}

			}
		}

		if (input.isKeyPressed(Input.KEY_Z)) {
			TheGame.frame.setVisible(true);
		}

		if (input.isKeyPressed(Input.KEY_S)) {
			if (StateHandler.musicOn) {
				StateHandler.musicOn = false;
				if (StateHandler.bgm.playing()) {
					StateHandler.bgm.stop();
				}
			} else {
				StateHandler.musicOn = true;
				StateHandler.bgm.loop();
			}
		}

	}

	public void jump() {
		float jumpPower = -15f;
		float sleep = 21f - TheGame.gameSpeed;
		// Jumping begins

		currentAnimation = jump;
		while (!isOnGround) { // && !threadDone

			playerY += jumpPower; // Increment the jump
			jumpPower++;

			if (jumpPower < 5)
				currentAnimation = jump;
			else
				currentAnimation = fall;

			try {
				Thread.sleep((long) sleep);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
		// Jump ends
		currentAnimation = run;
		jumps = 0;
	}

	/**
	 *Starts a new falling thread
	 *@author Oskar, Thomas
	 */
	public void beginFall() {
		falling = new Thread(this);
		falling.start();

	}

	/**
	 *Method for falling
	 *@author Oskar, Thomas
	 */
	public void fall() {
		playerY += (gravityEffect / 2) * TheGame.gameSpeed;
		gravityEffect++;

		System.out.println("Falling");

		try {
			Thread.sleep(20);
		} catch (InterruptedException e) {
		}
	}


	public long getScore() {
		return this.playerScore;
	}

	public void setPlayerLife(int life) {
		this.playerlife = life;
	}

	public void loosePlayerLife() {
		if (this.playerlife > 0) {
			this.playerlife--;
		}
	}

	public void gainPlayerLife() {
		if (this.playerlife < 9) {
			this.playerlife = this.playerlife + 1;
		}
	}

	public void deadPlayer() {
		// threadDone = true;
		this.loosePlayerLife();
		this.setPlayerX(200);
		this.setPlayerY(200);
	}

	public static void threadDone() {
		// threadDone= false;
	}

	public int getPlayerLife() {
		return this.playerlife;
	}

	public SpriteSheet getSheet() {
		return sheet;
	}

	public Polygon getPlayerBox() {
		return playerBox;
	}

	public void setPlayerBox(Polygon playerBox) {
		this.playerBox = playerBox;
	}

	public void setSheet(SpriteSheet sheet) {
		this.sheet = sheet;
	}

	public float getPlayerX() {
		return playerX;
	}

	public void setPlayerX(float playerX) {
		this.playerX = playerX;
	}

	public float getPlayerY() {
		return playerY;
	}

	public void setPlayerY(float playerY) {
		this.playerY = playerY;
	}

	public Animation getRun() {
		return run;
	}

	public void setRun(Animation run) {
		this.run = run;
	}

	public Animation getJump() {
		return jump;
	}

	public void setJump(Animation jump) {
		this.jump = jump;
	}

	public Animation getCurrentAnimation() {
		return currentAnimation;
	}

	public boolean isOnGround() {
		return isOnGround;
	}

	public void setOnGround(boolean isOnGround) {
		this.isOnGround = isOnGround;
	}

	public void setCurrentAnimation(Animation currentAnimation) {
		this.currentAnimation = currentAnimation;
	}

	public Thread getJumping() {
		return jumping;
	}

	public Thread getFalling() {
		return falling;
	}

	public void setJumping(Thread jumping) {
		this.jumping = jumping;
	}

	public void updateAnimationSpeed() {
		this.currentAnimation.setSpeed(TheGame.gameSpeed);
	}

	public long getPlayerScore() {
		return playerScore;
	}

	public void setPlayerScore(long playerScore) {
		this.playerScore = playerScore;
	}

	public void setLife(int life) {
		// this.life = life;
		try {
			Thread.sleep(30);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		if (!isOnGround)
			jump();
		else
			fall();
	}

	public void setGravityEffect(float gravity) {
		this.gravityEffect = gravity;

	}

	public int getPlayerlife() {
		return playerlife;
	}

	public void setPlayerlife(int playerlife) {
		this.playerlife = playerlife;
	}
	
	public int getJumps(){
		return jumps;
	}

	public Rectangle getTopHitBox() {
		return topHitBox;
	}

	public void setTopHitBox(Rectangle topHitBox) {
		this.topHitBox = topHitBox;
	}

	public Rectangle getBottomHitBox() {
		return bottomHitBox;
	}

	public void setBottomHitBox(Rectangle bottomHitBox) {
		this.bottomHitBox = bottomHitBox;
	}

	public Rectangle getFrontHitBox() {
		return frontHitBox;
	}

	public void setFrontHitBox(Rectangle frontHitBox) {
		this.frontHitBox = frontHitBox;
	}

}
