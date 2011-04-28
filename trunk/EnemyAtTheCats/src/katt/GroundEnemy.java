package katt;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;

/*
 * klass för fiender som står på marken
 */
public class GroundEnemy extends EnemyObject {
	// Int = typen av fiende
	private int type;
	private Animation stand;
	private SpriteSheet sheet;
	private Animation currentAnimation;

	public GroundEnemy(int type) {
		super();
		this.type = type;
		setImgLoc("data/Img/groundEnemy2.png");
		this.stand = createAnimation("data/Img/groundEnemy2.png", 70, 58, 10, 65,0);
		this.currentAnimation = stand;
		try {
			// Objektets bild som används för att sätta storleken på rektangeln
			Image i = new Image(getImgLoc());
			// Skapar en rektangel med lite mindre storlek än bilden
			setRectangle(new Rectangle(getPosX() + 5, getPosY() + 5,
					i.getWidth() - 10, i.getHeight() - 10));
		} catch (SlickException e) {
		}
		// Sätter ut objektet på en ny random position
		newObjectPos();
	}

	public int getType() {
		return type;
	}
	private Animation createAnimation(String fileDirectory, int sSizeX, 
			int sSizeY, int frameCount, float speed, int animationYPos) {
		
		return addFramesToAnimation(createSheet(fileDirectory, sSizeX, sSizeY), frameCount, speed, animationYPos);
	}
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
	private Animation addFramesToAnimation(SpriteSheet sht, int frameCount, float speed, int animationYPos){
		Animation tempAnim = new Animation();
		for (int frame = 0; frame < frameCount; frame++) {
			tempAnim.addFrame(sht.getSprite(animationYPos, frame), (int) speed);
		}
		return tempAnim;
		
	}
	public SpriteSheet getSheet() {
		return sheet;
	}
	public Animation getCurrentAnimation() {
		return currentAnimation;
	}
	public void setCurrentAnimation(Animation currentAnimation) {
		this.currentAnimation = currentAnimation;
	}
	public void updateAnimationSpeed() {
		this.currentAnimation.setSpeed(TheGame.gameSpeed);
	}
	
	
	
	
	
	

	// Skapar en ny slumpmässigt vald position för objektet
	@Override
	public void newObjectPos() {
		setCollided(false);
		setxPos(3000 + getRandom().nextInt(500));
		setyPos(402);
	}
	
	public void newObjectPos(int i) {
		setCollided(false);
		setxPos(3000 + getRandom().nextInt(500));
		setyPos(getRandom().nextInt(480 - (int) getRectangle().getHeight()));
	}

	// Uppdaterar fiendens position i sidled
	public void upDateXPos() {
		// Spelar upp ett hundljud när fienden kommer in i bild
		if (getPosX() < 650 && getPosX() > 640) {
			StateHandler.soundBank.playSound("dog1");
		}
		// När hunden kommit ur bild sätts den ut på en ny position
		// Hitboxen flyttas efter också
		if (getPosX() > -5) {
			setxPos(getPosX() - TheGame.gameSpeed);
			getRectangle().setX(getPosX());
			getRectangle().setY(getPosY());
		} else {
			newObjectPos();
		}
	}

}
