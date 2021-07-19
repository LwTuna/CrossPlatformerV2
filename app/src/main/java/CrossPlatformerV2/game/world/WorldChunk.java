package CrossPlatformerV2.game.world;

import CrossPlatformerV2.game.entities.Player;
import CrossPlatformerV2.game.utils.Coordinate;
import CrossPlatformerV2.game.world.Tiles.Tile;
import CrossPlatformerV2.game.world.creation.ChunkCreator;
import CrossPlatformerV2.game.world.creation.CreatedChunkData;
import CrossPlatformerV2.game.persistent.Loader;
import CrossPlatformerV2.game.persistent.SerChunkData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WorldChunk {

    private List<Tile[][]> layers = new ArrayList<>();

    private final int width,height;
    private Coordinate chunkCoord;


    private HashMap<Integer, Player> players = new HashMap<>();

    public WorldChunk(String path) throws IOException {
        SerChunkData data = Loader.loadChunkData(path);
        layers = data.getLayers();
        chunkCoord = data.getChunkCoord();
        width = data.getWidth();
        height = data.getHeight();
        System.out.println("Loaded Chunk "+chunkCoord);

    }

    public WorldChunk(int width,int height,int x,int y){
        CreatedChunkData createdChunkData = ChunkCreator.createChunkData(width,height);
        layers.add(createdChunkData.getTiles());
        this.width = createdChunkData.getWidth();
        this.height = createdChunkData.getHeight();
        chunkCoord = new Coordinate(x,y);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public List<Tile[][]> getLayers() {
        return layers;
    }

    public Coordinate getChunkCoord() {
        return chunkCoord;
    }

    public void joinPlayer(int playerId,Player player){
        players.put(playerId, player);
    }
    public Player leave(int playerId){
        return players.remove(playerId);
    }
}
