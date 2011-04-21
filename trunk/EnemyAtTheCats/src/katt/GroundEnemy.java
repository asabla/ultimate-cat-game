package katt;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

/*
 * klass f�r fiender som st�r p� marken
 */
public class GroundEnemy extends EnemyObject {
	// Int = typen av fiende
	private int type;

	public GroundEnemy(int type) {
		super();
		this.type = type;
		setImgLoc("data/Img/GroundEnemy" + type + ".png");

		try {
			// Objektets bild som anv�nds f�r att s�tta storleken p� rektangeln
			Image i = new Image(getImgLoc());
			// Skapar en rektangel med lite mindre storlek �n bilden
			setRectangle(new Rectangle(getPosX() + 5, getPosY() + 5,
					i.getWidth() - 10, i.getHeight() - 10));
		} catch (SlickException e) {
		}
		// S�tter ut objektet p� en ny random position
		newObjectPos();
	}

	public int getType() {
		return type;
	}

	// Skapar en ny slumpm�ssigt vald position f�r objektet
	@Override
	public void newObjectPos() {
		setCollided(false);
		setxPos(3000 + getRandom().nextInt(500));
		setyPos(400);
	}

	// Uppdaterar fiendens position i sidled
	public void upDateXPos() {
		// Spelar upp ett hundljud n�r fienden kommer in i bild
		if (getPosX() < 650 && getPosX() > 640 && StateHandler.soundsOn) {
			StateHandler.soundBank.playSound("dog1");
		}
		// N�r hunden kommit ur bild s�tts den ut p� en ny position
		// Hitboxen flyttas efter ocks�
		if (getPosX() > -5) {
			setxPos(getPosX() - TheGame.gameSpeed);
			getRectangle().setX(getPosX());
		} else {
			newObjectPos();
		}
	}

}
