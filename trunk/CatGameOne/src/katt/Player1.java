package katt;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Polygon;

public class Player1 implements Runnable {
        private SpriteSheet sheet;
        private Polygon playerBox;
        private float playerX, playerY;
        private int spriteSizeX, spriteSizeY;
        private Animation run, jump, die;
        private Animation currentAnimation;
        private int jumps; //Räknar antalet genomförda hopp, max 2 tillåtet 
        private Thread jumping;
        private boolean isOnGround;
        


        public Player1(int playerX, int playerY, String pngDir) throws SlickException{
                super();
                
					
				
                spriteSizeX = 50;
                spriteSizeY = 50;
                isOnGround = false;
                // Load all animations
                this.run = createAnimation(pngDir, spriteSizeX, spriteSizeY, 0, 6, 40, 0);
                this.jump = createAnimation(pngDir, spriteSizeX, spriteSizeY, 0, 6, 40, 0);

                // Set player start coordinate
                this.playerX = playerX;
                this.playerY = playerY;

                // Set player hitbox
                playerBox = new Polygon(new float[] { playerX, playerY,
                                playerX + spriteSizeX, playerY, playerX + spriteSizeX,
                                playerY + spriteSizeY, playerX, playerY + spriteSizeY });
        }

        private Animation createAnimation(String fileDirectory, int sSizeX,
                        int sSizeY, int aniStart, int aniStop, float speed, int count) {
                SpriteSheet sheet = null;
                Animation tempAnim = new Animation();
                try {
                        // load sprite sheet and put in list
                        sheet = new SpriteSheet(fileDirectory, sSizeX, sSizeY);
                } catch (SlickException e) {
                        e.printStackTrace();
                }
                for (int frame = aniStart; frame < aniStop; frame++) {
                        // add frames to the animation object
                        tempAnim.addFrame(sheet.getSprite(count, frame), (int) speed);
                }
                return tempAnim;
        }
     // Metod för keylistener för knapp upp
        public void keyPressed(Input input) {
        	
        	if(input.isKeyPressed(Input.KEY_R)){
        		setPlayerX(200);
        		setPlayerY(400);
        	}
                
                
                //Om knapp upp är nedtryckt en gång
                if (input.isKeyPressed(Input.KEY_UP)) {
                		//Sätt false(Spelare är i luften)
                        setOnGround(false);
                        //Koll hur många hopp, mer än två så körs ej If-blocket
                        if (jumps < 2) {
                        		//Räkna upp hopp
                                jumps++;
                                //Skapa och starta ny tråd för hopp
                                jumping = new Thread(this);
                                jumping.start();
                                //Spela hoppljud om ljudet är påslaget
                                if(Game.soundsOn)
                                Game.soundBank.playSound("jump");
                        }
                }
                //Om knapp upp är konstant nedtryckt 
                if (input.isKeyDown(Input.KEY_UP)) {
                		//Om tråden jumping inte är aktiv gör
                        if (!jumping.isAlive()) {
                        		//Skapa ny tråd för hopp, starta tråd
                                jumping = new Thread(this);
                                jumping.start();
                                //Spela hoppljud om ljud påslaget
                                if(Game.soundsOn)
                                	Game.soundBank.playSound("jump");
                                //Spelare är inte på marken
                                setOnGround(false);
                                //Om spelare är på marken
//                                if(isOnGround){		/////Om isOnGround redan är true, varför sätta isOnGround true?
//                                        setOnGround(true);    //Borde kunna tas bort                                    
//                                }

                        }
                }
                //Keylistener, visa frame om knapp z är tryckt
                if (input.isKeyPressed(Input.KEY_Z)){
                	Game.frame.setVisible(true);
                }
                //Slår på eller av ljudeffekterna
                if (input.isKeyPressed(Input.KEY_S)){

                	if(Game.soundsOn == true){
                		Game.soundsOn = false;

                	}
                	else{
                		Game.soundsOn = true;
                	}

                }
                //Slår på eller av bakgrundsmusiken
                if (input.isKeyPressed(Input.KEY_M)){
                	//Kollar om musiken är på eller inte
                	if(Game.musicOn == true){
                		Game.musicOn = false;
                		//Stoppar musiken, om den spelas
                	if(Game.bgm.playing()){
                		Game.bgm.stop();
                		}
                	}
                	else
                	{
                		Game.musicOn = true;
                		//Slår på musiken igen
                		Game.bgm.loop();
                	}
                }
                
        }

        public void jump() {
        		//Spelares hoppkraft och gravitation, vid hopp flyttas spelaren antalet pixlar som jumppower och 
        		//gravity är tillsammans. jumpPower räknas upp värde 1 varje loop  
                float jumpPower = -15f;
                float gravity = 2f;
   
                // Hopp börjar, nuvarande animation av spelare sätts till jump-animation
                currentAnimation = jump;
                //Loopar hoppet, så länge spelare inte är på marken, addera spelares position med jumpPower+gravity och räkna upp jumpPower 1
                while (!isOnGround) {
                        playerY += (jumpPower + gravity); // Increment the jump
                        jumpPower++;
                        System.out.println("Hopp  " + jumpPower);

                        try {
                        	//Pausa hopp-tråden 30 millisekunder
                                Thread.sleep(30);
                        } catch (InterruptedException e) {
                                e.printStackTrace();
                        }
                        
                }
                //Sätter nuvarande animation till spring-versionen igen.
                currentAnimation = run;
                //Nollställer antalet hopp, möjligt med dubbelhopp igen
                jumps = 0;
        }

        public SpriteSheet getSheet() {
                return sheet;
        }

        public Polygon getPlayerBox() {
                return playerBox;
        }

        public void setPlayerBox(Polygon playerBox) {
                this.playerBox = playerBox;
        }

        public void setSheet(SpriteSheet sheet) {
                this.sheet = sheet;
        }
        //returnerar spelares x-position
        public float getPlayerX() {
                return playerX;
        }
        //Sätter spelares x-position till detta
        public void setPlayerX(float playerX) {
                this.playerX = playerX;
        }
      //returnerar spelares y-position
        public float getPlayerY() {
                return playerY;
        }
      //Sätter spelares y-position till detta
        public void setPlayerY(float playerY) {
                this.playerY = playerY;
        }

        public Animation getRun() {
                return run;
        }

        public void setRun(Animation run) {
                this.run = run;
        }

        public Animation getJump() {
                return jump;
        }

        public void setJump(Animation jump) {
                this.jump = jump;
        }

        public Animation getDie() {
                return die;
        }

        public void setDie(Animation die) {
                this.die = die;
        }

        public Animation getCurrentAnimation() {
                return currentAnimation;
        }
        public boolean isOnGround() {
                return isOnGround;
        }

        public void setOnGround(boolean isOnGround) {
                this.isOnGround = isOnGround;
        }

        public void setCurrentAnimation(Animation currentAnimation) {
                this.currentAnimation = currentAnimation;
        }

        public Thread getJumping() {
                return jumping;
        }

        public void setJumping(Thread jumping) {
                this.jumping = jumping;
        }

        public void updateAnimation() {
                this.currentAnimation.setSpeed(Game.gameSpeed);
        }

        @Override
        public void run() {
                jump();
                // TODO Auto-generated method stub
        }
        public int getJumps(){
        	return jumps;
        }
}
