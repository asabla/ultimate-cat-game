package katt;

import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.particles.ConfigurableEmitter;
import org.newdawn.slick.particles.ParticleIO;
import org.newdawn.slick.particles.ParticleSystem;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;


public class Space extends BasicGameState {
	private int ID = -1;
	private StateBasedGame game;
	private ParticleSystem smoke;
	private Player1[] players;
	private static int time;
	private Image cat;
	private boolean gogo;
	
	private float catPosy;
	private float catPosx;
	
	float startPy;// start pos Y för i väg flygande objekt
	float startPx;// start pos X för i väg flygande objekt
	float slutPXv;// gräns för flygande objekt vänster
	float slutPXh;// gräns för flygande objekt höger
	float slutPy;// gräns för flygande objekt på Y axel

	
	public Space(int ID) {
	
		super();
		this.ID = ID;
		
		// TODO Auto-generated constructor stub
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game)
			throws SlickException {
		try {
			smoke = ParticleIO.loadConfiguredSystem("data/smokeSystem.xml");
		} catch (IOException e) {
			throw new SlickException("Failed to load particle systems", e);
		}

		
		// TODO Auto-generated method stub

	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return ID;
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		time = 1;
		startPy = 400;
		startPx = 200; 
		slutPy = startPy - 500f;
		slutPXh = startPx + 40f;
		slutPXv = startPx - 40f;
		// TODO Auto-generated method stub

	}

	@Override
	public void leave(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		
		cat = new Image("data/Img/cat3.png");
		game.getState(StateHandler.theGame).render(container, game, g);
		//g.drawImage(cat, catPosx, catPosy);
			// ((ConfigurableEmitter)
				//	 smoke.getEmitter(0)).setPosition((startPx + 30),
					//			slutPy + 15);
 //****************************************************************************************
			 if (startPy == slutPy) {

//					g.drawImage(cat, startPx, slutPy);
//					 ((ConfigurableEmitter)
//					 smoke.getEmitter(0)).setPosition((startPx + 30),
//					 slutPy + 15);
					 
//					startPy = 400f;
//					startPx = 200f;

				}
				// Kollar att den den inte gått för mycket åt höger, körs tills den
				// når slutPXH = den högra gränsen på X
				else if (startPx > slutPXh || (gogo)) {
					
					g.drawString("Bonus GAAAAAAAAAAMMMEEE", 200, 200);
					

					g.drawImage(cat, startPx++, startPy--);
					 ((ConfigurableEmitter)
					 smoke.getEmitter(0)).setPosition((startPx + 25),
					startPy + 40);
					 smoke.render();
					if (startPx > slutPXv) {
						gogo = true;
					} else {
						gogo = false;
					}
				}
				// Kollar att den den inte gått för mycket åt vänster, körs tills
				// den når slutPXv = den vänstra gränsen på X
				else if (startPx < slutPXv || (!gogo)) {
					g.drawString("Bonus GAAAAAAAAAAMMMEEE", 200, 200);
					g.drawImage(cat, startPx++, startPy--);
					 ((ConfigurableEmitter)
					 smoke.getEmitter(0)).setPosition((startPx + 25),
				     startPy + 40);
					smoke.render();
					if (startPx < slutPXh) {
						gogo = false;
					} else {
						gogo = true;
					}
				}
		}
	
		// TODO Auto-generated method stub

	

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		
		smoke.update(delta);
		time += delta;
		// TODO Auto-generated method stub

	}

	public void setCat(Image cat) {
		this.cat = cat;
	}

	public float getCatPosy() {
		return catPosy;
	}

	public void setCatPosy(float catPosy) {
		this.catPosy = catPosy;
	}

	public float getCatPosx() {
		return catPosx;
	}

	public void setCatPosx(float catPosx) {
		this.catPosx = catPosx;
	}
	public Image getCat() {
		return cat;
	}

	

	
	
}