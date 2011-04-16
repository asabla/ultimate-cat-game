package katt;

import java.util.Random;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

/*Klass som håller ett fiendeobjekt. Klassen är abstrakt och subklasserna är 
 * GroundEnemy(För fiender på marken)
 * FlyingEnemy(för fiender i luften)
 */
abstract public class EnemyObject {
/*xPos, yPos = kordinater för objektet
 * r = en randomgenerator
 * imgLoc = sökvägen där bilden för objektet ligger
 * collided = variabel som håller reda på om spelarobjektet krockat med fiendeobjektet 
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

	//Skapar en rektangel som är nödvändig för kollisionskontroll
	public Rectangle getRectangle(){
		try{
			//Objektets bild som används för att sätta storleken på rektangeln
		Image i = new Image(imgLoc);

		//Skapar en rektangel med lite mindre storlek än bilden
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
	
	//Skapar en ny slumpmässigt vald position för objektet
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
