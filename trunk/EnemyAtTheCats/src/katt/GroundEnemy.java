package katt;
/*
 * klass f�r fiender som st�r p� marken
 */
public class GroundEnemy extends EnemyObject {
	//Int = typen av fiende
	private int type;
	

	public GroundEnemy(int type){
		super();
		this.type = type;
		setImgLoc("data/Img/GroundEnemy" + type + ".png");
	}
	
	
	public int getType(){
		return type;
	}
	
	//Skapar en ny slumpm�ssigt vald position f�r objektet
	@Override
	public void newObjectPos(){
		setCollided(false);
    	setxPos(3000 + getRandom().nextInt(500));
    	setyPos(400); 
	}
	//Uppdaterar fiendens position i sidled
	public void upDateXPos()
	{
		//Spelar upp ett hundljud n�r fienden kommer in i bild
		if(getxPos() < 650 && getxPos() > 640 && StateHandler.soundsOn){
			StateHandler.soundBank.playSound("dog1");
		}
		//N�r hunden kommit ur bild s�tts den ut p� en ny position
		if(getxPos() > - 5){
			setxPos(getxPos() - TheGame.gameSpeed);
		}else{			
			newObjectPos();
		}
	}	
	
	
}
