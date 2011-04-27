package katt;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

/*
 * klass för fiender som står på marken
 */
public class GroundEnemy extends EnemyObject {
	// Int = typen av fiende
	private int type;

	public GroundEnemy(int type) {
		super();
		this.type = type;
		setImgLoc("data/Img/GroundEnemy" + type + ".png");

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

	// Skapar en ny slumpmässigt vald position för objektet
	@Override
	public void newObjectPos() {
		setCollided(false);
		setxPos(3000 + getRandom().nextInt(500));
		setyPos(400);
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
