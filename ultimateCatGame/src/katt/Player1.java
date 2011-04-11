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
        private int jumps;
        private Thread jumping;
        private boolean isOnGround;


        public Player1(int playerX, int playerY, String pngDir) {
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

        public void keyPressed(Input input) {
                
                
                // UP
                if (input.isKeyPressed(Input.KEY_UP)) {
                        setOnGround(false);
                        if (jumps < 2) {
                                jumps++;
                                jumping = new Thread(this);
                                jumping.start();
                        }
                }
                // UP-REPEAT
                if (input.isKeyDown(Input.KEY_UP)) {
                        if (!jumping.isAlive()) {
                                jumping = new Thread(this);
                                jumping.start();
                                setOnGround(false);
                                if(isOnGround){
                                        setOnGround(true);
                                        
                                }

                        }
                }
                if (input.isKeyPressed(Input.KEY_Z)){
                	Game.frame.setVisible(true);
                }
                if (input.isKeyPressed(Input.KEY_S)){
                	if(Game.soundBank.isEnabled()){
                	Game.soundBank.stopSounds();
                	Game.soundBank.setDisabled();
                	}
                	else{
                    	Game.soundBank.setEnabled();
                    	Game.soundBank.startBackground("BGMMenu");
                    }
                }
                
        }

        public void jump() {
                float jumpPower = -15f;
                float gravity = 2f;
                Game.soundBank.startSound("SFXCatJump");
                // Jumping begins
                currentAnimation = jump;
                while (!isOnGround) {
                        playerY += (jumpPower + gravity); // Increment the jump
                        jumpPower++;
                        System.out.println("HOpp");

                        try {
                                Thread.sleep(30);
                        } catch (InterruptedException e) {
                                e.printStackTrace();
                        }
                        
                }
                currentAnimation = run;
                jumps = 0; // Jump ends
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

        public float getPlayerX() {
                return playerX;
        }

        public void setPlayerX(float playerX) {
                this.playerX = playerX;
        }

        public float getPlayerY() {
                return playerY;
        }

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
}
