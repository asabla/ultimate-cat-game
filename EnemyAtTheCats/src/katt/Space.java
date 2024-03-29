package katt;

import java.io.IOException;

import org.newdawn.slick.Color;
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
	private ParticleSystem rocketFire;
	private Player1[] players;
	private static int time;
	private Image cat;
	private boolean gogo;
	private Image[] bonusGame;
	private Image[] go;
	private float catPosy;
	private float catPosx;
	private int count = 0;
	private boolean introSpace;
	private boolean inSpace;
	private boolean outSpace;
	private boolean sky;
	private int goTestMove = 0;
	float startPy;// start pos Y f�r i v�g flygande objekt
	float startPx;// start pos X f�r i v�g flygande objekt
	float slutPXv;// gr�ns f�r flygande objekt v�nster
	float slutPXh;// gr�ns f�r flygande objekt h�ger
	float slutPy;// gr�ns f�r flygande objekt p� Y axel

	public Space(int ID) {

		super();
		this.ID = ID;

		// TODO Auto-generated constructor stub
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game)
			throws SlickException {

		try {
			rocketFire = ParticleIO
					.loadConfiguredSystem("data/rocketSystem.xml");
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
		introSpace = false;
		inSpace = false;
		
		
		try {
			bonusGame = new Image[3];
			bonusGame[0] = new Image("data/Img/bonusgame.png");

			bonusGame[1] = new Image("data/Img/bonusgame2.png");
			bonusGame[2] = new Image("data/Img/bonusgame3.png");
			
			go = new Image[3];
			go[0] = new Image("data/Img/go1.png");
			go[1] = new Image("data/Img/go2.png");
			go[2] = new Image("data/Img/go3.png");
		} catch (SlickException e) {
		
			

			e.printStackTrace();
		}

		time = 1;
		startPy = 400;
		startPx = 200;
		slutPy = startPy - 500f;
		slutPXh = startPx + 40f;
		slutPXv = startPx - 40f;
		
	}

	@Override
	public void leave(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {

	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {

		cat = new Image("data/Img/catRocket.png");
		if(outSpace){ 
			if(!sky){
			Image heavenLayer1 =  new Image("data/Img/img_bg_sky.png");
			Image heavenLayer2 =  new Image("data/Img/cloud1.png");
			g.drawImage(heavenLayer1, 0, 0);
			g.drawImage(heavenLayer2, 0, 0);
			g.drawImage(cat, startPx++, startPy++);
			if(startPy == 400){
				sky = true;
				startPx = 0;
				startPy = 0;
			}
			
		}
			if(sky){
				game.getState(StateHandler.THEGAME).render(container, game, g);
				g.drawImage(cat, startPx++, startPy++);
				if(startPy == 300){
					game.enterState(StateHandler.THEGAME);
				}
//				players[1].getPlayerX();
//				players[1].getPlayerY();
			}
		}

		else{
		if (outSpace) {
			if (!sky) {
				Image heavenLayer1 = new Image("data/Img/img_bg_sky.png");
				Image heavenLayer2 = new Image("data/Img/cloud1.png");
				g.drawImage(heavenLayer1, 0, 0);
				g.drawImage(heavenLayer2, 0, 0);
				g.drawImage(cat, startPx++, startPy++);
				if (startPy == 400) {
					sky = true;
					startPx = 0;
					startPy = 0;
				}

			}
			if (sky) {
				game.getState(StateHandler.THEGAME).render(container, game, g);
				g.drawImage(cat, startPx++, startPy++);
				if (startPy == 300) {
					game.enterState(StateHandler.THEGAME);
				}
				// players[1].getPlayerX();
				// players[1].getPlayerY();
			}
		}

		else {

			if (!introSpace && !inSpace) {
				game.getState(StateHandler.THEGAME).render(container, game, g);
				g.drawImage(bonusGame[count], 200, 150);

				if (startPy == slutPy) {
					introSpace = true;
					startPx = 200;
					startPy = 500;

				}
				// Kollar att den den inte g�tt f�r mycket �t h�ger, k�rs tills
				// den
				// n�r slutPXH = den h�gra gr�nsen p� X
				else if (startPx > slutPXh || (gogo)) {

					((ConfigurableEmitter) rocketFire.getEmitter(0))
							.setPosition((startPx + 20), startPy + 25);
					rocketFire.render();
					g.drawImage(cat, startPx++, startPy--);
					if (startPx > slutPXv) {
						gogo = true;
					} else {
						gogo = false;
					}
				}
				// Kollar att den den inte g�tt f�r mycket �t v�nster, k�rs
				// tills
				// den n�r slutPXv = den v�nstra gr�nsen p� X
				else if (startPx < slutPXv || (!gogo)) {
					((ConfigurableEmitter) rocketFire.getEmitter(0))
							.setPosition((startPx + 20), startPy + 25);
					rocketFire.render();
					g.drawImage(cat, startPx++, startPy--);
					if (startPx < slutPXh) {
						gogo = false;
					} else {
						gogo = true;
					}
				}
			}
			if (introSpace) {
				if (startPy == -40) {
					startPx = -50;
					startPy = 500;
					inSpace = true;
					introSpace = false;
				} else {
					Image heavenLayer1 = new Image("data/Img/img_bg_sky.png");
					Image heavenLayer2 = new Image("data/Img/cloud1.png");
					g.drawImage(heavenLayer1, 0, 0);
					g.drawImage(heavenLayer2, 0, 0);

					((ConfigurableEmitter) rocketFire.getEmitter(0))
							.setPosition((startPx + 20), startPy + 25);
					rocketFire.render();
					g.drawImage(cat, startPx++, startPy--);

				}
			}
			if (inSpace) {
				if (startPy == 200) {
					startPx = 0;
					startPy = 0;

					game.enterState(StateHandler.XTRALEVEL);
					setOutSpace(true);
				} else {
					// Imgage goText = new Image("")
					Image spaceLayer1 = new Image("data/Img/space1.png");
					Image spaceLayer2 = new Image("data/Img/planets1.png");
					//g.drawImage(goText, 0, 0);
					g.drawImage(spaceLayer1, 0, 0);
					g.drawImage(spaceLayer2, 0, 0);

					((ConfigurableEmitter) rocketFire.getEmitter(0))
							.setPosition((startPx + 20), startPy + 25);
					rocketFire.render();

					g.drawImage(cat, startPx++, startPy--);	
		}
		   }
			
		}
		}


	}

	// TODO Auto-generated method stub

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		count++;
		if (count > 2) {
			count = 0;
		}
		rocketFire.update(delta);
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

	public void sound() {
		StateHandler.soundBank.playSound("spaceflight");
	}

	public void setOutSpace(boolean outSpace) {
		this.outSpace = outSpace;
	}

	public boolean isOutSpace() {
		return outSpace;
	}

}