package katt;
/*
 * klass för fiender som står på marken
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
	
	//Skapar en ny slumpmässigt vald position för objektet
	@Override
	public void newObjectPos(){
		setCollided(false);
    	setxPos(3000 + getRandom().nextInt(500));
    	setyPos(400); 
	}
	//Uppdaterar fiendens position i sidled
	public void upDateXPos()
	{
		//Spelar upp ett hundljud när fienden kommer in i bild
		if(getxPos() < 650 && getxPos() > 640 && StateHandler.soundsOn){
			StateHandler.soundBank.playSound("dog1");
		}
		//När hunden kommit ur bild sätts den ut på en ny position
		if(getxPos() > - 5){
			setxPos(getxPos() - TheGame.gameSpeed);
		}else{			
			newObjectPos();
		}
	}	
	
	
}
