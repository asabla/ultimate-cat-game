package katt;

import java.util.Random;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

/*Klass som h�ller ett fiendeobjekt. Klassen �r abstrakt och subklasserna �r 
 * GroundEnemy(F�r fiender p� marken)
 * FlyingEnemy(f�r fiender i luften)
 */
abstract public class EnemyObject {
/*xPos, yPos = kordinater f�r objektet
 * r = en randomgenerator
 * imgLoc = s�kv�gen d�r bilden f�r objektet ligger
 * collided = variabel som h�ller reda p� om spelarobjektet krockat med fiendeobjektet 
 * eller inte
 */
	private float xPos;
	private float yPos;
	private Random r;
	private String imgLoc;
	private boolean collided;
	
	public EnemyObject(){
		r = new Random();
		newObjectPos();
		collided = false;
	}

	public float getxPos() {
		return xPos;
	}

	public float getyPos() {
		return yPos;
	}

	//Skapar en rektangel som �r n�dv�ndig f�r kollisionskontroll
	public Rectangle getRectangle(){
		try{
			//Objektets bild som anv�nds f�r att s�tta storleken p� rektangeln
		Image i = new Image(imgLoc);

		//Skapar en rektangel med lite mindre storlek �n bilden
		Rectangle r = new Rectangle(xPos + 5, yPos + 5, i.getWidth()-10, i.getHeight()-10);
		return r;
		}
		catch(SlickException e){}
		return null;
	}
	
	public void setImgLoc(String loc){
		imgLoc = loc;
	}
	
	public String getImgLoc(){
		return imgLoc;
	}
	
	public void setxPos(float xPos) {
		this.xPos = xPos;
	}

	public void setyPos(float yPos) {
		this.yPos = yPos;
	}
	

	public Random getRandom(){
		return r;
	}
	
	//Skapar en ny slumpm�ssigt vald position f�r objektet
	public void newObjectPos(){
		collided = false;
    	setxPos(3000 + r.nextInt(500));
    	setyPos(410); 

	}

	public boolean isCollided() {
		return collided;
	}

	public void setCollided(boolean collided) {
		this.collided = collided;
	}
	
	public boolean getCollided(){
		return collided;
	}
}
