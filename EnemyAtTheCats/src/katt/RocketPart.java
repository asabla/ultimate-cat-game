package katt;

public class RocketPart extends PickupObject {
	
	public boolean taken;
	
	/*
	 * konstruktor för rocketPart typ ska vara:
	 * 7 = helmet
	 * 8 = rocket
	 * 9 = boots
	 */
	public RocketPart(int type, float xPos, float yPos){
		super(type, xPos, yPos);
		taken = false;
	}
	
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
