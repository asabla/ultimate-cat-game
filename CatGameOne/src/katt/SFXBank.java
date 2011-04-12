package katt;
/*Den här klassen innehåller en hashmap som samlar alla ljudeffekter som används i spelet.
 *För att välja ett ljud att spela upp så använder man getSound()-metoden, på ljudet använder
 *man sedan play(). Exempelvis getSound("bottle").play(); 
 */
import java.util.HashMap;
import java.util.Random;
import java.util.ArrayList;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;


public class SFXBank {

	private HashMap<String, Sound> soundMap;
	
	public SFXBank(){

		soundMap = new HashMap<String, Sound>();
		
		try{
			//Samlar alla ljud i en ljudbank, med varsin sträng som nyckel
		soundMap.put("bottle", new Sound("Data/Audio/SFXBottleCrack.ogg"));
		soundMap.put("boing", new Sound("Data/Audio/SFXBoing.ogg"));
		soundMap.put("crush", new Sound("Data/Audio/SFXCanCrush.ogg"));
		soundMap.put("crash", new Sound("Data/Audio/SFXCatCrash.ogg"));
		soundMap.put("jump", new Sound("Data/Audio/SFXCatJump.ogg"));
		soundMap.put("happy", new Sound("Data/Audio/SFXHappyCat.ogg"));
		soundMap.put("harp1", new Sound("Data/Audio/SFXHarp1.ogg"));
		soundMap.put("harp2", new Sound("Data/Audio/SFXHarp2.ogg"));
		soundMap.put("shake", new Sound("Data/Audio/SFXShake.ogg"));
		soundMap.put("click", new Sound("Data/Audio/SFXClick.ogg"));
		}catch (SlickException e) {
		}
	}
	/*
	 * returnerar ett ljud beroende på vilken nyckel som är inmatad
	 * */
	public Sound getSound(String key){		
		//Returnerar ett ljud med vald nyckel. Nyckeln görs om till lower case för att undvika nullPointer
		return soundMap.get(key.toLowerCase());
	}
	
	public void playSound(String key){
		getSound(key).play();
	}
	
	/*onödig funktion, kan dock komma till pass om man t.ex.
	 *  vill alternera mellan två olika hoppljud.
	 *   metoden returnerar ett slumpmässigt valt ljud
	 */
	public Sound getRandomSound(){
		Random r = new Random();
		ArrayList<String> al = new ArrayList<String>();
		al.add("bottle");
		al.add("boing");
		al.add("jump");
		al.add("happy");

		return getSound(al.get(r.nextInt(4)));
		
	}
}
