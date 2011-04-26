package katt;

import org.newdawn.slick.geom.Rectangle;
import java.util.Random;
import org.newdawn.slick.Image;

/*klass som representerar ett objekt som man kan ta p� banan.
 * 
 */

public class PickupObject{
	/*
	 * objectType = typ av objekt. Ett heltal mellan 0-9 d�r 0=livobjekt 1-6 =
	 * po�ngobjekt av olika v�rden (*1000)
	 * 7-9 = raketdelar
	 * xPos = objektets position i v�gl�ngd
	 * yPos = objektets position i h�jdl�ngdvalue = objektets v�rde
	 */
	private int objectType;
	private float xPos;
	private float yPos;
	private int value;
	private Random r;
	private String imgLoc;
	private Rectangle rectangle;
	private float width;

	// Skapar ett objekt av slumpm�ssig typ(ej livobjekt) p� en slumpm�ssig
	// plats
	public PickupObject() {

		r = new Random();

		objectType = r.nextInt(5);
		if (objectType == 0) {
			objectType = 1;
		}
		value = objectType * 1000;

		imgLoc = "data/Img/object" + objectType + ".png";
		// Skapar en rektangel f�r objektet med samma storlek som bilden f�r
		// objektet
		try {
			Image i = new Image((String) imgLoc);
			rectangle = new Rectangle(xPos + 4, yPos + 4, i.getWidth() - 8,
					i.getHeight() - 8);
			width = i.getWidth();
		} catch (Exception e) {
		}

		newObjectPos();
	}

	/*
	 * Skapar ett objekt av best�md typ och best�md plats
	 * 
	 * @param type = ett heltal mellan 0-9
	 * 
	 * @param xPos = objektets position i sidled
	 * 
	 * @param yPos = objektets position i h�jdled
	 */

	public PickupObject(int type, float xPos, float yPos) {
		r = new Random();
		this.xPos = xPos;
		this.yPos = yPos;

		objectType = type;


		if (objectType > 0) {
			value = objectType * 1000;
		} else if (objectType == 0) {
			value = 1;
		}
		imgLoc = "data/Img/object" + objectType + ".png";
		try {
			Image i = new Image((String) imgLoc);
			rectangle = new Rectangle(xPos + 4, yPos + 4, i.getWidth() - 8,
					i.getHeight() - 8);
		} catch (Exception e) {
		}
	}

	// Returnerar en str�ng som representerar bildens s�kv�g
	public String getImgLoc() {
		return imgLoc;
	}

	// returnerar objekttypen
	public int getObjectType() {
		return objectType;
	}

	public float getxPos() {
		return xPos;
	}

	public float getyPos() {
		return yPos;
	}

	public int getValue() {
		return value;
	}

	// Skapar en rektangel som �r n�dv�ndig f�r kollisionskontroll
	public Rectangle getRectangle() {

		return rectangle;

	}

	public void setxPos(float xPos) {
		this.xPos = xPos;
	}

	public void setyPos(float yPos) {
		this.yPos = yPos;
	}

	public void setValue(int value) {
		this.value = value;
	}

	// Skapar en ny slumpm�ssigt vald position f�r objektet
	public void newObjectPos() {
		setxPos(1000 + r.nextInt(500));
		setyPos(250 + r.nextInt(150));
		rectangle.setLocation(xPos, yPos);
	}

	public void newObjectPosLong() {
		setxPos(25000 + r.nextInt(1000));
		setyPos(250 + r.nextInt(150));
		rectangle.setLocation(xPos, yPos);
	}

	// Slumpar en ny typ av objekt, och �ndrar v�rdet
	public void newObjectType() {
		objectType = r.nextInt(9);
		value = objectType * 1000;
	}

	public void upDateXPos() {
		//Om po�ngobjekt �ker utanf�r sk�rmen s� ska de s�ttas p� en ny position
		if (xPos > -width) {
			xPos -= TheGame.gameSpeed;
			rectangle.setX(xPos);

		} else{
			//Kontrollerar om objektet inte �r en raketdel
			 if(objectType < 7){
			newObjectPos();
			 }
		}
		
		
	}
	
	public void upDatePartPos(){
		xPos -= TheGame.gameSpeed;
		rectangle.setX(xPos);
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getWidth() {
		return width;
	}
}
