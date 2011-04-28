package katt;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

/*
 * klass som h�ller luftburna fiender
 */
public class FlyingEnemy extends EnemyObject {
	/*
	 * type = typ av flygande fiende maxYpos, minYpos = kordinater f�r h�gsta
	 * och l�gsta positionen som f�geln ska ha (som om den flyger upp och ner)
	 * upwards = vilket h�ll f�geln flyger f�r tillf�llet
	 */
	private int type;
	private float maxYpos;
	private float minYpos;
	private boolean upwards;
	private Image fEnemyImage;

	public FlyingEnemy(int type) {
		super();
		this.type = type;
		setImgLoc("data/Img/flyingEnemy" + type + ".png");
		try {
			setfEnemyImage(new Image("data/Img/flyingEnemy" + type + ".png"));
		} catch (SlickException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		maxYpos = getPosY() + 40;
		minYpos = getPosY() - 40;
		upwards = true;

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

	public void setType(int type) {
		this.type = type;
	}

	// Skapar en ny slumpm�ssigt vald position f�r objektet
	@Override
	public void newObjectPos() {
		setCollided(false);
		setxPos(3000 + getRandom().nextInt(500));
		setyPos(getRandom().nextInt(400));
	}

	public void upDateXPos() {
//		// Spelar upp ett ljud n�r fienden kommer in i bild
//		if (getPosX() > 640 && getPosX() < 650) {
//			StateHandler.soundBank.playSound("harp1");
//		}
		if (getPosX() > -5) {
			setxPos(getPosX() - TheGame.gameSpeed);
			getRectangle().setX(getPosX());
			getRectangle().setY(getPosY());

		} else {
			newObjectPos();
		}
	}

	public void setfEnemyImage(Image fEnemyImage) {
		this.fEnemyImage = fEnemyImage;
	}

	public Image getfEnemyImage() {
		return fEnemyImage;
	}

}
