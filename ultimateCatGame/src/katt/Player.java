package katt;
import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Polygon;
import java.util.HashMap;

public class Player {
	private SpriteSheet sheet;
	private Polygon playerBox;
	private float playerX, playerY;
	private int spriteSizeX, spriteSizeY;
	private Animation run, jump, die;
	private Animation currentAnimation;

	public Player(int playerX, int playerY, String pngDir) {
		super();
		spriteSizeX = 50;
		spriteSizeY = 70;

		
		//Load all animations
		this.run = createAnimation(pngDir, spriteSizeX, spriteSizeY, 0, 12, 40, 0);
		this.jump = createAnimation(pngDir, spriteSizeX, spriteSizeY, 0, 11, 120, 1);
		
		//Set player start coordinate
		this.playerX = playerX;
		this.playerY = playerY;
		
		//Set player hitbox
		playerBox = new Polygon(new float[]{
			playerX,playerY,
			playerX+spriteSizeX,playerY,
			playerX+spriteSizeX,playerY+spriteSizeY,
			playerX,playerY+spriteSizeY
		});
		
	}

	private Animation createAnimation(String fileDirectory, int sSizeX, int sSizeY, int aniStart, int aniStop, float speed, int count){
		SpriteSheet sheet = null;
		Animation tempAnim = new Animation();
		try {
			//load sprite sheet and put in list
			sheet = new SpriteSheet(fileDirectory, sSizeX, sSizeY);
		} 
		catch (SlickException e) {
			e.printStackTrace();
		}
		for (int frame=aniStart;frame<aniStop;frame++) {
			//add frames to the animation object
			tempAnim.addFrame(sheet.getSprite(count,frame),(int) speed);
		}
		return tempAnim;		
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
	public void setCurrentAnimation(Animation currentAnimation) {
		this.currentAnimation = currentAnimation;
	}
	public void updateAnimation(){
		this.currentAnimation.setSpeed(Game.gameSpeed);
	}
}
