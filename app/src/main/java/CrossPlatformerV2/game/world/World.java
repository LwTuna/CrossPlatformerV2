package CrossPlatformerV2.game.world;

import CrossPlatformerV2.game.utils.Coordinate;
import CrossPlatformerV2.game.persistent.Loader;
import CrossPlatformerV2.game.persistent.SerChunkData;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class World {

    public static final String worldsPath = "save/worlds/";

    private Map<Coordinate,WorldChunk> worldChunkList;

    private int id;
    private String name;


    /**
     * Creates a new World with given properties
     *
     */
    public World(int id,String name) {
        this.id = id;
        this.name = name;
        worldChunkList = new HashMap<>();
        try {
            saveWorld();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveWorld() throws IOException {
        JSONObject worldData = new JSONObject();
        worldData.put("id",id);
        worldData.put("name",name);
        File file = new File(worldsPath+name);
        if(!file.exists()) file.mkdirs();
        Loader.saveJSON(worldsPath+name+"/world.wdata", worldData);
        for(Map.Entry<Coordinate,WorldChunk> e:worldChunkList.entrySet()){
            WorldChunk worldChunk = e.getValue();
            SerChunkData serChunkData = new SerChunkData(worldChunk.getWidth(), worldChunk.getHeight(), worldChunk.getChunkCoord(),worldChunk.getLayers());
            Loader.saveChunkData(serChunkData,worldsPath+name+"/"+worldChunk.getChunkCoord().getX()+"-"+worldChunk.getChunkCoord().getY()+".cdata");
        }
        System.out.println("Saved World "+name);

    }
     /**
     * Loads a world from the given Worldname
     */
    public World(String name) throws IOException {
        worldChunkList = new HashMap<>();
        this.name = name;
        JSONObject loaded = Loader.loadJSON(worldsPath+name+"/world.wdata");
        this.id = loaded.getInt("id");
        File folder = new File(worldsPath+name);
        for(String fileName : folder.list()){
            if(fileName.endsWith("wdata")) continue;
            WorldChunk worldChunk = new WorldChunk(worldsPath+name+"/"+fileName);
            worldChunkList.put(worldChunk.getChunkCoord(), worldChunk);
        }
        System.out.println("Loaded World "+name);
    }


    public WorldChunk createNewWorldChunk(int width,int height,int x,int y){
        if(worldChunkList.containsKey(new Coordinate(x,y))) throw new IllegalArgumentException("World already contains this Chunk.");
        WorldChunk worldChunk = new WorldChunk(width,height,x,y);
        worldChunkList.put(worldChunk.getChunkCoord(),worldChunk );
        return worldChunk;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Map<Coordinate, WorldChunk> getWorldChunkList() {
        return worldChunkList;
    }
}
