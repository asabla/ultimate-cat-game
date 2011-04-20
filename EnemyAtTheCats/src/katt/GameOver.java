package katt;

import java.awt.Font;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.Game;
import org.newdawn.slick.util.InputAdapter;

public class GameOver extends BasicGameState implements ComponentListener{
        
        private Image back = null;
        private Image newgame = null;
        private Image newgameOver = null;
        private Image backOver = null;
        private MouseOverArea[] areas = new MouseOverArea[3];
        private GameContainer gameContainer;
        private Game ettGame;
        private StateBasedGame game;
        private int ID = -1;
        private UnicodeFont myFont;
        private TextField f_namn;
        private TextField f_email;
        private TextField f_telefon;
        private TextField f_points;
        private String message = "google";
        private static String pscore;
        private Image sendScore;
        private Database db;
        
        public GameOver(int ID)
        {
                super();
                this.ID = ID;
        }

        @Override
        public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException 
        {
                g.drawString("Game Over", 300, 50);
                g.setBackground(Color.magenta);
                
                areas[0].render(container, g);
                areas[1].render(container, g);
                areas[2].render(container, g);
                
                //myFont.drawString(0,0,"test");  //Går att använda fonten till att skriva ut 
                
                f_namn.render(container, g);
                f_email.render(container, g);
                f_telefon.render(container, g);
                f_points.render(container, g);
                
                g.setFont(myFont);
                g.drawString(message, 200, 550);
        }

        @Override
        public void componentActivated(AbstractComponent source) 
        {
                if(source == areas[0])
                {
                        StateHandler.paused = false;
                        StateHandler.dead = false;
                        game.enterState(StateHandler.theGame);
                }
                if(source == areas[1])
                {
                        game.enterState(StateHandler.menu);
                }
                if(source == areas[2])
                {
                        int tmp = Integer.parseInt(f_points.getText());  //Parsar om texten ifrån highscore till en int
                        db.sendHighscore(f_namn.getText(), tmp, f_email.getText(), f_telefon.getText()); //Testar att skicka in resultatet
                }
                
                if(source == f_namn)
                {
                        f_email.setFocus(true);
                }
                if(source == f_email)
                {
                        f_telefon.setFocus(true);
                }
                if(source == f_telefon)
                {
                        //setPoints
                }
        }

        @SuppressWarnings("deprecation")
        @Override
        public void init(GameContainer container, StateBasedGame game) throws SlickException 
        {
                this.game = game;
                
                back = new Image("data/Img/Back.png");
                newgame = new Image("data/Img/nyttspel1.png");
                newgameOver = new Image("data/Img/nyttspel2.png");
                back = new Image("data/Img/tillbaka1.png");
                backOver = new Image("data/Img/tillbaka2.png");
                sendScore = new Image("data/Img/object1.png");
                
                //container.setMouseCursor("data/Img/cursor.png", 0, 0);
                
                for(int i = 0; i < 3; i++)
                {
                        if(i == 0)
                        {
                                areas[i] = new MouseOverArea(container, newgame, 130, 100, 400, 52, this);
                                areas[i].setMouseOverImage(newgameOver);
                        }
                        if(i == 1)
                        {
                                areas[i] = new MouseOverArea(container, back, 135, 200, 400, 52, this);
                                areas[i].setMouseOverImage(backOver);
                        }
                        if(i == 2)
                        {
                                areas[i] = new MouseOverArea(container, sendScore, 150,410, 100, 52, this);
                        }
                }
                
                myFont = getNewFont("Arial", 24);
        }

        @Override
        public void update(GameContainer container,StateBasedGame game, int delta) throws SlickException 
        {
                myFont.loadGlyphs();
        }

        @Override
        public int getID() 
        {
                // TODO Auto-generated method stub
                return ID;
        }
        
        public static void setPScore(String score)
        {
                pscore = score;
        }
        
        public void enter(GameContainer container, StateBasedGame game) throws SlickException
        {
                f_namn = new TextField(container, myFont, 150,270,200,35);
                f_email = new TextField(container, myFont, 150,310,200,35);
                f_telefon = new TextField(container, myFont, 150,350,200,35);
                f_points = new TextField(container, myFont, 150,390,200,35);
                db = new Database();
                
                f_namn.setText("Gurkmannen");  //Sätter spelarens namn fördefinerat
                f_email.setText("enMain@mm.se");  //Sätter spelarens email
                f_telefon.setText("324-234234");  //Sätter spelarens telefon
                f_points.setText(GameOver.pscore);  //Hämtar spelarens poäng när denne förlorar
                f_namn.setFocus(true);
        }
        
        @Override
        public void leave(GameContainer container, StateBasedGame game)throws SlickException 
        {
                
        }
        public UnicodeFont getNewFont(String fontName, int fontSize) 
        { 
              UnicodeFont font = new UnicodeFont(new Font(fontName, Font.PLAIN, fontSize)); 
              font.addGlyphs("@");
              font.getEffects().add(new ColorEffect(java.awt.Color.white)); //Måste vara minst en effekt aktiverad för att det ska fungera
              return (font); 
        } 
}