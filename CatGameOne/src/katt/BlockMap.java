package katt;
import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class BlockMap {
        private static TiledMap tmap;

        private static int mapWidth;
        private static int mapHeight;
        private int square[] = { 1, 1, 15, 1, 15, 15, 1, 15 }; // square shaped tile
        //private int square[] = { 1, 1, 31, 1, 31, 31, 1, 31 }; // square shaped tile
        private static ArrayList<Block> entities;
        private static int tileSize;

        public BlockMap(String ref) throws SlickException {
                entities = new ArrayList<Block>();
                tmap = new TiledMap(ref, "data/Img");
                mapWidth = tmap.getWidth() * tmap.getTileWidth();
                mapHeight = tmap.getHeight() * tmap.getTileHeight();
                tileSize = tmap.getTileWidth();

                loadBlocks();   
        }
        
        /*
         * Reload map
         * move every polygon to the left
         * */
        public void updateBlockMap(float currentX){
                for (int x = 0; x < entities.size(); x++) {
                        //Hitta ett bra sätt att nollställa denna
                        if(currentX >= 640){
                                clearBlocks();
                                loadBlocks();
                        }
                        entities.get(x).getPoly().setX(entities.get(x).getPoly().getX()-1*Game.gameSpeed);
                }
        }

        /* For testing only
         * Draws the hitboxes, in the game
         * */
        public void drawHitBox(Graphics g, float currentX) {
                for (int x = 0; x < entities.size(); x++) {
                        g.draw(entities.get(x).getPoly());
                }
        }

        /*
         * reade the tmx file
         * Create a hitbox
         * Insert it in to an arraylist 
         * */
        public void loadBlocks(){
                for (int x = 0; x < tmap.getWidth(); x++) {
                        for (int y = 0; y < tmap.getHeight(); y++) {
                        //System.out.println(x + ":" + y + " collision: " +(tmap.getTileId(x, y, 0) == 1));
                                if (tmap.getTileId(x, y, 0) == 1) {
                                        entities.add(new Block(x * tileSize, y * tileSize, square, "square"));
                                }
                        }
                }
        }

        public void clearBlocks(){
                        entities.clear();
        }
        
        public static TiledMap getTmap() {
                return tmap;
        }

        public static void setTmap(TiledMap tmap) {
                BlockMap.tmap = tmap;
        }

        public static int getMapWidth() {
                return mapWidth;
        }

        public static void setMapWidth(int mapWidth) {
                BlockMap.mapWidth = mapWidth;
        }

        public static int getMapHeight() {
                return mapHeight;
        }

        public static void setMapHeight(int mapHeight) {
                BlockMap.mapHeight = mapHeight;
        }

        public int[] getSquare() {
                return square;
        }

        public void setSquare(int[] square) {
                this.square = square;
        }

        public static ArrayList<Block> getEntities() {
                return entities;
        }

        public static void setEntities(ArrayList<Block> entities) {
                BlockMap.entities = entities;
        }
}
