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
			//Skapar en locator som pekar p� filen
			mediaLocator = new MediaLocator(filePath);
			//Skapar en spelare f�r filen
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
			//S�tter volymen f�r ljudklippet
			playSoundFile.getGainControl().setLevel(gainLevel);
		//Skapar en listener som lyssnar efter om filen har tagit slut, varvid den stoppar och st�nger filen
		playSoundFile.addControllerListener(new ControllerListener()
		{
			@Override
			public void controllerUpdate(ControllerEvent e)
		{
			if(e instanceof EndOfMediaEvent)
			{
				//Filen spolas tillbaka, redo f�r att spelas upp
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
		
		//�ppnar och spelar upp filen
		playSoundFile.start();
	}
		//Mutar ett ljud
		public void setMute(boolean state){
			playSoundFile.getGainControl().setMute(state);
		}
		
		//St�nger av ett ljud
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
		//D�dar ljudet, g�rs vid avst�nging a programmet, eller vid byte av bakgrundsmusik
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
		//Metod f�r att se vilken niv� volymen ligger p�
		public float getGainLevel(){
			return gainLevel;
		}
		//Metod f�r att �ndra volymen
		public void setGainLevel(float level){			
			playSoundFile.getGainControl().setLevel(level);
			gainLevel = level;
		}
		//Metod f�r att g�ra en fade out p� ett ljud
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
		//Metod f�r att g�ra fade in p� ett ljud
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
		//St�ller in att ljudet ska loopas
		public void setLoop(boolean loop){
			this.loop = loop;
		}
		
}
