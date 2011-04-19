package katt;

/*
 * klass som h�ller luftburna fiender
 */
public class FlyingEnemy extends EnemyObject{
	/*
	 * type = typ av flygande fiende
	 * maxYpos, minYpos = kordinater f�r h�gsta och l�gsta positionen som f�geln ska ha
	 * (som om den flyger upp och ner)
	 * upwards = vilket h�ll f�geln flyger f�r tillf�llet
	 */
	private int type;
	private float maxYpos;
	private float minYpos;
	private boolean upwards;
	
	public FlyingEnemy(int type){
		this.type = type;
		setImgLoc("data/Img/flyingEnemy" + type + ".png");
		maxYpos = getPosY() + 40;
		minYpos = getPosY() - 40;
		upwards = true;
	}
	
	
	
	public int getType() {
		return type;
	}



	public void setType(int type) {
		this.type = type;
	}



	//Skapar en ny slumpm�ssigt vald position f�r objektet
	@Override
	public void newObjectPos(){
			setCollided(false);
	    	setxPos(3000 + getRandom().nextInt(500));
	    	setyPos(250);
	}
	
	public void upDateXPos()
	{
		//Spelar upp ett ljud n�r  fienden kommer in i bild
		if(getPosX() > 640 && getPosX()< 650){
			StateHandler.soundBank.playSound("harp1");
		}
		if(getPosX() > - 5){
			setxPos(getPosX() - TheGame.gameSpeed);

		}else{			
			newObjectPos();
		}
	}	
	

}
