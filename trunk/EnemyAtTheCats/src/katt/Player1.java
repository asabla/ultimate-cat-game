package katt;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Polygon;

public class Player1 implements Runnable {
	private SpriteSheet sheet;
	private Polygon playerBox;
	private float playerX, playerY;
	private int spriteSizeX, spriteSizeY;
	private Animation run, jump, die;
	private Animation currentAnimation;
	private int jumps;
	private Thread jumping;
	private boolean isOnGround;
	private int jumpControl;
	private long playerScore;
	private int playerlife;
	//private int score;
	private Thread falling;

	private float gravityEffect;

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
		this.run = createAnimation(pngDir, spriteSizeX, spriteSizeY, 0, 6, 40,
				0);
		this.jump = createAnimation(pngDir, spriteSizeX, spriteSizeY, 0, 6, 40,
				0);
		this.jumpControl = jumpControl;

		// Set player start coordinate
		this.playerX = playerX;
		this.playerY = playerY;

		// Set player hitbox
		playerBox = new Polygon(new float[] { playerX, playerY,
				playerX + spriteSizeX, playerY, playerX + spriteSizeX,
				playerY + spriteSizeY, playerX, playerY + spriteSizeY });
	}

	private Animation createAnimation(String fileDirectory, int sSizeX,
			int sSizeY, int aniStart, int aniStop, float speed, int count) {
		SpriteSheet sheet = null;
		Animation tempAnim = new Animation();
		try {
			// load sprite sheet and put in list
			sheet = new SpriteSheet(fileDirectory, sSizeX, sSizeY);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		for (int frame = aniStart; frame < aniStop; frame++) {
			// add frames to the animation object
			tempAnim.addFrame(sheet.getSprite(count, frame), (int) speed);
		}
		return tempAnim;
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
			if (!jumping.isAlive()) {
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
		
		//Slår av musik
		if (input.isKeyPressed(Input.KEY_M)) {
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
					
		//Slår av ljudeffekter
		if (input.isKeyPressed(Input.KEY_S)) {
			if (StateHandler.soundsOn) {
				StateHandler.soundsOn = false;

			} else {
				StateHandler.soundsOn = true;				
			}
		}
		if(input.isKeyPressed(Input.KEY_R)){
			TheGame.smokeOn = !TheGame.smokeOn;
		}

	}

	public void jump() {
		float jumpPower = -15f;
		float sleep = 21f - TheGame.gameSpeed;
		// Jumping begins
		currentAnimation = jump;
		while (!isOnGround) {
			playerY += jumpPower; // Increment the jump
			jumpPower++;

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

	public void beginFall() {
		falling = new Thread(this);
		falling.start();
	}

	public void fall() {
		playerY += (gravityEffect / 6) * TheGame.gameSpeed;
		gravityEffect++;

		System.out.println("Falling");

		try {
			Thread.sleep(20);
		} catch (InterruptedException e) {}
	}

//	public void setScore(int p) {
//		this.score += p / 2;
//	}
//
//	public int getScore() {
//		return this.score;
//	}

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
		this.loosePlayerLife();
		this.setPlayerX(200);
		this.setPlayerY(200);
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

	public Animation getDie() {
		return die;
	}

	public void setDie(Animation die) {
		this.die = die;
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

}
