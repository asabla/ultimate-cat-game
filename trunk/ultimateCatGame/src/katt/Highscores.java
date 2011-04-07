package katt;
import java.io.*;



public class Highscores {
	
	
	private Score high = null;
	private Score mid = null;
	private Score low = null;
	
	/**
	 * @param args
	 */
	public Highscores(){
		//this.high = high;
		//this.mid = mid;
		//this.low = low;
		
	}
	

	
	public void check(Score nytt)
	{
			Score highscore1 = high;
			Score highscore2 = mid;
			Score highscore3 = low;
			
			if(nytt.getPoints() < low.getPoints())
				{
					System.out.print("Du lyckades tyvŠrr inte slŒ nŒgot nytt rekord");
				}
			
			else if(nytt.getPoints() < mid.getPoints())
				{
					highscore3 = nytt;
				}
			
			else if(nytt.getPoints() < high.getPoints())
				{
					highscore3 = highscore2;
					highscore2 = nytt;
				}
			else
				{
					highscore3 = highscore2;
					highscore2 = highscore1;
					highscore1 = nytt;
				
				}
				
			high = highscore1;
			mid = highscore2;
			low = highscore3;
			
			write();
	}
	
	public void write(){
		
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
		}
        catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.print("Filen saknas");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.print("IOException");
		}
		System.out.println("Värden ut:");
		System.out.println(high.getName() + " : " + high.getPoints());
		System.out.println(mid.getName() + " : " + mid.getPoints());
		System.out.println(low.getName() + " : " + low.getPoints());
	}
	
	
	/* public void write()
	{
					
			String hs1 = Long.toHexString(high);
			String hs2 = Long.toHexString(mid);
			String hs3 = Long.toHexString(low);			
			
			
			try{
				FileWriter fw1 = new FileWriter("highscore1.hs");
				FileWriter fw2 = new FileWriter("highscore2.hs");
				FileWriter fw3 = new FileWriter("highscore3.hs");
				BufferedWriter out1 = new BufferedWriter(fw1);
				BufferedWriter out2 = new BufferedWriter(fw2);
				BufferedWriter out3 = new BufferedWriter(fw3);
				out1.write(hs1);
				out1.close();
				out2.write(hs2);
				out2.close();
				out3.write(hs3);
				out3.close();
				}
				
				catch(Exception e)
			{
					System.out.print("FEL");
			}
				System.out.println("Värden ut:\n" + high + "\n" + mid + "\n" + low );
	}
	
	//Get metod som hamtar en hexString frŒn en fil och gšr om den till en decString.
	 */
	 public void get(){
		 	
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
			System.out.println("Värden in:");
			System.out.println(high.getPoints());
			System.out.println(mid.getPoints());
			System.out.println(low.getPoints());
			
	 }
	 
	 /* 
	public void get()
	{
		//HŠmtar filen frŒn highscore.hs som Šr en txt-fil med ett lite klurigt namn.
		
			FileReader fr1;
			FileReader fr2;
			FileReader fr3;
			try {
				fr1 = new FileReader("highscore1.hs");
				fr2 = new FileReader("highscore2.hs");
				fr3 = new FileReader("highscore3.hs");
			
			
			BufferedReader in;
			String ut = "";
			
			for(int i = 1; i < 4; i++)
			{
				
			if(i == 1){
				in = new BufferedReader(fr1);
			}
			else if(i == 2){
				in = new BufferedReader(fr2);

			}
			else{
				in = new BufferedReader(fr3);
			}
			//lŠgger hexStringen frŒn highscore.hs i en tom string(ut).
			
			
			
				//LŠser in ut.
				try {
					ut = in.readLine();
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//Gšr om hexStringen i ut till en decString.
				Long t = Long.valueOf(ut, 16);
				//Skriver ut i consolen
				
				if(i == 1){
					high = t;
				}
				else if(i == 2){
					mid = t;

				}
				else{
					low = t;
				}
				
				
				
			} 
} 
			
			catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Värden in:\n" + high + "\n" + mid + "\n" + low );
			
		
		
		
			}	
	
	public void reset()
	{
		
		Long n = 0l;

		
		String hs1 = n.toString();
		String hs2 = n.toString();
		String hs3 = n.toString();			
		
		
		try{
			FileWriter fw1 = new FileWriter("highscore1.hs");
			FileWriter fw2 = new FileWriter("highscore2.hs");
			FileWriter fw3 = new FileWriter("highscore3.hs");
			BufferedWriter out1 = new BufferedWriter(fw1);
			BufferedWriter out2 = new BufferedWriter(fw2);
			BufferedWriter out3 = new BufferedWriter(fw3);
			out1.write(hs1);
			out1.close();
			out2.write(hs2);
			out2.close();
			out3.write(hs3);
			out3.close();
			}
			
			catch(Exception e)
		{
				System.out.print("FEL");
		}
		
	} */
	}	
	
		


