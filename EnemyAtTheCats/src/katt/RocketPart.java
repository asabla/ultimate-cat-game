package katt;

public class RocketPart extends PickupObject {
	
	public boolean taken;
	
	/**
	 * Konstruktor f�r RocketPart
	 * @param type typ av raketdel 7 = helmet, 8 = rocket 9 = boots
	 * @param xPos Objektets startposition i sidled
	 * @param yPos objektets startposition i h�jdled
	 */
	public RocketPart(int type, float xPos, float yPos){
		super(type, xPos, yPos);
		taken = false;
	}
	
	/**
	 * uppdaterar xPositionen f�r objektet s� l�nge objektet �r otaget	
	 */
	public void upDatePartPos(){
			if(getxPos() > -getWidth()){
		setxPos(getxPos() - TheGame.gameSpeed);
		getRectangle().setX(getxPos());
		}
			else{
				if(!taken){
				newObjectPosLong();
				}
			}
		}
	@Override
	public void newObjectPosLong(){
		setxPos(7000);
	}

	public boolean isTaken() {
		return taken;
	}

	public void setTaken(boolean taken) {
		this.taken = taken;
	}
}
