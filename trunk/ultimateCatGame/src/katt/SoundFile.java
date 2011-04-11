package katt;

import javax.media.*;



public class SoundFile extends Thread
{

 
	private MediaLocator mediaLocator;
	private Player playSoundFile;
	private boolean loop;
	private float gainLevel;


	
	public SoundFile(String filePath)
	{
		this.loop = false;
		try{
			//Skapar en locator som pekar på filen
			mediaLocator = new MediaLocator(filePath);
			//Skapar en spelare för filen
			playSoundFile = Manager.createRealizedPlayer(mediaLocator);			
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		gainLevel = (float)0.4;
	}
	
	//Spelar upp ett ljud
		public void runSound()
		{
			//Sätter volymen för ljudklippet
			playSoundFile.getGainControl().setLevel(gainLevel);
		//Skapar en listener som lyssnar efter om filen har tagit slut, varvid den stoppar och stänger filen
		playSoundFile.addControllerListener(new ControllerListener()
		{
			@Override
			public void controllerUpdate(ControllerEvent e)
		{
			if(e instanceof EndOfMediaEvent)
			{
				//Filen spolas tillbaka, redo för att spelas upp
				playSoundFile.setMediaTime(new Time(0));
				//Om filen ska loopas spelas den upp igen
				if(loop == true){
				playSoundFile.start();
				}
				else{
				stopSound();
				}
			}
		}
		}
		);
		
		//Öppnar och spelar upp filen
		playSoundFile.start();
	}
		//Mutar ett ljud
		public void setMute(boolean state){
			playSoundFile.getGainControl().setMute(state);
		}
		
		//Stänger av ett ljud
		public void stopSound()
		{
			try{
			setLoop(false);
			playSoundFile.stop();
			playSoundFile.setMediaTime(new Time(0));
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
		}		
		//Dödar ljudet, görs vid avstänging a programmet, eller vid byte av bakgrundsmusik
		public void killSound()
		{
			try{
			setLoop(false);
			playSoundFile.stop();
			playSoundFile.close();
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
		}
		//Metod för att se vilken nivå volymen ligger på
		public float getGainLevel(){
			return gainLevel;
		}
		//Metod för att ändra volymen
		public void setGainLevel(float level){			
			playSoundFile.getGainControl().setLevel(level);
			gainLevel = level;
		}
		//Metod för att göra en fade out på ett ljud
		public void fadeOutSound(){
			while(playSoundFile.getGainControl().getLevel() > 0){
				playSoundFile.getGainControl().setLevel(playSoundFile.getGainControl().getLevel() - (float)0.005);
				try{
					sleep(30);
				}catch(Exception e){
					
				}
			}
			setMute(true);
			}
		//Metod för att göra fade in på ett ljud
		public void fadeInSound(){
			setMute(false);
			while(playSoundFile.getGainControl().getLevel() < gainLevel){
				playSoundFile.getGainControl().setLevel(playSoundFile.getGainControl().getLevel() + (float)0.005);
				try{
					sleep(30);
				}catch(Exception e){
					
				}
			}

		}
		//Ställer in att ljudet ska loopas
		public void setLoop(boolean loop){
			this.loop = loop;
		}
		
}
