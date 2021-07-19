package CrossPlatformerV2.game.world.creation;

import CrossPlatformerV2.game.world.Tiles.Tile;

public class CreatedChunkData {

    int width,height;
    Tile[][] tiles;

    public CreatedChunkData(int width, int height, Tile[][] tiles) {
        this.width = width;
        this.height = height;
        this.tiles = tiles;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Tile[][] getTiles() {
        return tiles;
    }
}
