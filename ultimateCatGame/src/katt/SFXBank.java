package katt;

import java.util.HashMap;
import java.util.Collection;

public class SFXBank {

	private HashMap<String, SoundFile> soundMap;
	private boolean soundsEnabled;
	
	public SFXBank(){
		soundsEnabled = true;
		soundMap = new HashMap<String, SoundFile>();
		soundMap.put("BGMMenu", new SoundFile("file:Data\\Audio\\BGMmenu.wav"));
		soundMap.put("SFXBottleCrack", new SoundFile("file:Data\\Audio\\SFXBottleCrack.wav"));
		soundMap.put("SFXButton", new SoundFile("file:Data\\Audio\\SFXButton.wav"));
		soundMap.put("SFXCanCrush", new SoundFile("file:Data\\Audio\\SFXCanCrush.wav"));
		soundMap.put("SFXCatCrash", new SoundFile("file:Data\\Audio\\SFXCatCrash.wav"));
		soundMap.put("SFXCatJump", new SoundFile("file:Data\\Audio\\SFXCatJump.wav"));
		soundMap.put("SFXHappyCat", new SoundFile("file:Data\\Audio\\SFXHappyCat.wav"));
		soundMap.put("SFXHarp1", new SoundFile("file:Data\\Audio\\SFXHarp1.wav"));
		soundMap.put("SFXHarp2", new SoundFile("file:Data\\Audio\\SFXHarp2.wav"));
		soundMap.put("SFXShake", new SoundFile("file:Data\\Audio\\SFXShake.wav"));
		soundMap.put("SFXSnap", new SoundFile("file:Data\\Audio\\SFXSnap.wav"));
	}
	//Returnerar en ljudfil
	public SoundFile getSoundFile(String key){
		return soundMap.get(key);
	}

	//Startar ett ljud, parametern key ska motsvara namnet på ett ljud
	public void startSound(String key){
		if(soundsEnabled){
		getSoundFile(key).runSound();
		}
	}
	//Startar bakgrundsmusiken och loopar den
	public void startBackground(String key){
		if(soundsEnabled){
		getSoundFile(key).setLoop(true);
		getSoundFile(key).runSound();
		}
	}
	
	//Stoppar ett ljud
	public void stopSound(String key){
		getSoundFile(key).killSound();
	}
	
	/*Tystar/avtystar ett ljud. Bra om man vill slå på/av bakgrundsmusiken, 
	 * men vill behålla ljudeffekter
	 * */
	
	public void muteSound(String key, boolean state){
		getSoundFile(key).setMute(state);
	}
	
	//Tystar/avtystar alla ljud
	public void muteSounds(boolean state){
		Collection<SoundFile> c = soundMap.values();
		for(SoundFile soundFile : c){
			soundFile.setMute(state);
		}
		
	}
	
	public void getGainLevel(String key){
		getSoundFile(key).getGainLevel();
	}
	
	public void setVolume(String key, float level){
		getSoundFile(key).setGainLevel(level);
	}
	
	public void fadeOutSound(String key){
		getSoundFile(key).fadeOutSound();
	}
	
	public void fadeInSound(String key){
		getSoundFile(key).fadeInSound();
	}
	public void stopSounds(){
		Collection<SoundFile> c = soundMap.values();
		for(SoundFile soundFile : c){
			soundFile.stopSound();
		}
	}
	
	//Dödar alla ljud
	public void killSounds(){
		Collection<SoundFile> c = soundMap.values();
			for(SoundFile soundFile : c){
				soundFile.killSound();
			}
	}
	
	public void setDisabled(){
		soundsEnabled = false;
	}
	
	public void setEnabled(){
		soundsEnabled = true;
	}
	
	public boolean isEnabled(){
		return soundsEnabled;
	}
	
	

	}
