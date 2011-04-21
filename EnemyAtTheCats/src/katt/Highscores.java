package katt;

import java.io.*;

public class Highscores {

	private Score high = null;
	private Score mid = null;
	private Score low = null;

	/**
	 * @param args
	 */
	public Highscores() {
		// this.high = high;
		// this.mid = mid;
		// this.low = low;

	}

	public void check(Score nytt) {
		Score highscore1 = high;
		Score highscore2 = mid;
		Score highscore3 = low;

		if (nytt.getPoints() < low.getPoints()) {
			System.out.print("Du lyckades tyvŠrr inte slŒ nŒgot nytt rekord");
		}

		else if (nytt.getPoints() < mid.getPoints()) {
			highscore3 = nytt;
		}

		else if (nytt.getPoints() < high.getPoints()) {
			highscore3 = highscore2;
			highscore2 = nytt;
		} else {
			highscore3 = highscore2;
			highscore2 = highscore1;
			highscore1 = nytt;

		}

		high = highscore1;
		mid = highscore2;
		low = highscore3;

		write();
	}

	public void write() {

		try {

			FileOutputStream fos1 = new FileOutputStream("data/score1.tmp");
			ObjectOutputStream oos1 = new ObjectOutputStream(fos1);
			FileOutputStream fos2 = new FileOutputStream("data/score2.tmp");
			ObjectOutputStream oos2 = new ObjectOutputStream(fos2);
			FileOutputStream fos3 = new FileOutputStream("data/score3.tmp");
			ObjectOutputStream oos3 = new ObjectOutputStream(fos3);

			oos1.writeObject(high);
			oos2.writeObject(mid);
			oos3.writeObject(low);

			oos1.close();
			oos2.close();
			oos3.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.print("Filen saknas");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.print("IOException");
		}
		get();

	}

	public void write(Score noll) {

		try {

			FileOutputStream fos1 = new FileOutputStream("data/score1.tmp");
			ObjectOutputStream oos1 = new ObjectOutputStream(fos1);
			FileOutputStream fos2 = new FileOutputStream("data/score2.tmp");
			ObjectOutputStream oos2 = new ObjectOutputStream(fos2);
			FileOutputStream fos3 = new FileOutputStream("data/score3.tmp");
			ObjectOutputStream oos3 = new ObjectOutputStream(fos3);

			oos1.writeObject(noll);
			oos2.writeObject(noll);
			oos3.writeObject(noll);

			oos1.close();
			oos2.close();
			oos3.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.print("Filen saknas");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.print("IOException");
		}
		get();
	}

	public void get() {

		try {

			FileInputStream fis1 = new FileInputStream("data/score1.tmp");
			ObjectInputStream ois1 = new ObjectInputStream(fis1);
			FileInputStream fis2 = new FileInputStream("data/score2.tmp");
			ObjectInputStream ois2 = new ObjectInputStream(fis2);
			FileInputStream fis3 = new FileInputStream("data/score3.tmp");
			ObjectInputStream ois3 = new ObjectInputStream(fis3);

			high = (Score) ois1.readObject();
			mid = (Score) ois2.readObject();
			low = (Score) ois3.readObject();

			ois1.close();
			ois2.close();
			ois3.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void reset() {

		Score noll = new Score(0l);

		try {
			FileOutputStream fos1 = new FileOutputStream("data/score1.tmp");
			ObjectOutputStream oos1 = new ObjectOutputStream(fos1);
			FileOutputStream fos2 = new FileOutputStream("data/score2.tmp");
			ObjectOutputStream oos2 = new ObjectOutputStream(fos2);
			FileOutputStream fos3 = new FileOutputStream("data/score3.tmp");
			ObjectOutputStream oos3 = new ObjectOutputStream(fos3);

			oos1.writeObject(noll);
			oos2.writeObject(noll);
			oos3.writeObject(noll);

			oos1.close();
			oos2.close();
			oos3.close();
		}

		catch (Exception e) {
			System.out.print("FEL");
		}
		printUt();

	}

	public void printIn() {
		System.out.println("Värden in:");
		System.out.println(high.getName() + " : " + high.getPoints());
		System.out.println(mid.getName() + " : " + mid.getPoints());
		System.out.println(low.getName() + " : " + low.getPoints());
	}

	public void printUt() {
		System.out.println("Värden ut:");
		System.out.println(high.getName() + " : " + high.getPoints());
		System.out.println(mid.getName() + " : " + mid.getPoints());
		System.out.println(low.getName() + " : " + low.getPoints());
	}

}
