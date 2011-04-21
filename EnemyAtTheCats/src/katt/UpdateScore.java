package katt;

public class UpdateScore {

	private Highscores hs;

	public UpdateScore() {
		hs = new Highscores();
		hs.get();
	}

	public void update(String name, Long currentScore) {
		Score ny = new Score(currentScore);
		ny.setName(name);
		// high = new Score(10L);
		// high.setName("Joppe");
		// mid = new Score(6L);
		// mid.setName("Klas");
		// low = new Score(3L);
		// low.setName("Emil");

		hs.printIn();
		hs.check(ny);
		hs.printUt();
	}

	public void reset() {
		Score noll = new Score(0l);
		hs.write(noll);
		hs.printUt();
	}

}
