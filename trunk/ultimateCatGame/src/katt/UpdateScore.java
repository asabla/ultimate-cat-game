package katt;

public class UpdateScore {
	private static Score ny;
	private static Highscores hs;

	public UpdateScore(){
		
	}
	
	public void update(String name, Long currentScore){
		ny = new Score(currentScore);
		ny.setName(name);
		//high = new Score(10L);
		//high.setName("Joppe");
		//mid = new Score(6L);
		//mid.setName("Klas");
		//low = new Score(3L);
		//low.setName("Emil");
		hs = new Highscores();
		hs.get();
		
		hs.write();
		hs.check(ny);
	}
}
