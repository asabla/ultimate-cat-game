package katt;

import java.util.ArrayList;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class BlockMap {
	private TiledMap tmap;

	private int mapWidth;
	private int mapHeight;
	private int square[] = { 1, 1, 15, 1, 15, 15, 1, 15 };
	// private int square[] = { 1, 1, 31, 1, 31, 31, 1, 31 };
	private ArrayList<Block> blockList;
	private int tileSize;

	public BlockMap(String ref) throws SlickException {
		this.blockList = new ArrayList<Block>();
		this.tmap = new TiledMap(ref, "data/Img");
		this.mapWidth = this.tmap.getWidth() * this.tmap.getTileWidth();
		this.mapHeight = this.tmap.getHeight() * this.tmap.getTileHeight();
		this.tileSize = this.tmap.getTileWidth();

		loadBlocks();
	}

	public void moveBlockMap() {
		for (Block block : blockList) {
			block.getPoly().setX // Set the position of the Block
					(block.getPoly().getX() // Get the current position
							- TheGame.gameSpeed); // Add the map position
		}
	}

	/**
	 * Reload map Move every polygon to the left
	 */
	public void updateBlockMap(float currentX, boolean buffer) {
		clearBlocks();
		loadBlocks();
		if (buffer) {
			for (Block block : blockList) {
				block.getPoly().setX // Set the position of the Block
						(block.getPoly().getX() // Get the current position
								+ currentX); // Add the map position
			}
		}
	}

	public void jumpMap(int length) {
		for (int x = 0; x < this.blockList.size(); x++) {
			this.blockList.get(x).getPoly()
					.setX(this.blockList.get(x).getPoly().getX() + length);
		}
	}

	/**
	 * For testing only Draws the hitboxes, in the game
	 */
	public void drawHitBox(Graphics g, float currentX) {
		for (int x = 0; x < this.blockList.size(); x++) {
			g.draw(this.blockList.get(x).getPoly());
		}
	}

	/**
	 * reades the tmx file Create a hitbox Insert it in to an arraylist
	 */
	public void loadBlocks() {
		for (int x = 0; x < this.tmap.getWidth(); x++) {
			for (int y = 0; y < this.tmap.getHeight(); y++) {
				if (this.tmap.getTileId(x, y, 0) == 1) {
					this.blockList.add(new Block(x * this.tileSize, y
							* this.tileSize, this.square, "square"));
				}
			}
		}
	}

	public void clearBlocks() {
		this.blockList.clear();
	}

	public TiledMap getTmap() {
		return tmap;
	}

	public int[] getSquare() {
		return square;
	}

	public void setSquare(int[] square) {
		this.square = square;
	}

	public ArrayList<Block> getEntities() {
		return blockList;
	}

	public void setEntities(ArrayList<Block> entities) {
		this.blockList = entities;
	}

	public int getMapWidth() {
		return mapWidth;
	}

	public void setMapWidth(int mapWidth) {
		this.mapWidth = mapWidth;
	}

	public int getMapHeight() {
		return mapHeight;
	}

	public void setMapHeight(int mapHeight) {
		this.mapHeight = mapHeight;
	}

}
