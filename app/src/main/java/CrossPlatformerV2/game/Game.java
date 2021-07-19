package CrossPlatformerV2.game;

import CrossPlatformerV2.Server.Server;
import CrossPlatformerV2.Server.sessions.SessionUser;
import CrossPlatformerV2.game.entities.Player;
import CrossPlatformerV2.game.persistent.Loader;
import CrossPlatformerV2.game.world.World;
import CrossPlatformerV2.game.world.WorldChunk;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Game {
    private Server server;
    
    private HashMap<Integer,World> worldList;

    private Map<Integer, WorldChunk> players = new HashMap<>();

    public Game(Server server) {
        this.server = server;
        
        loadWorlds();
    }

    private void loadWorlds() {
        worldList = new HashMap<>();

        File worldFolder = new File(World.worldsPath.substring(0,World.worldsPath.length()-1));
        if(!worldFolder.exists()) worldFolder.mkdirs();
        for(String file : worldFolder.list()){
            try {
                World world = new World(file);
                worldList.put(world.getId(),world);
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(0);
            }

        }
    }

    public World createNewWorld(String name){
        World world = new World(worldList.size(), name);
        worldList.put(world.getId(), world);
        return world;
    }

    public void saveAllWorlds(){
        for(World world:worldList.values()){
            try {
                world.saveWorld();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void start() {
    }

    public void stop() {
    }

    public void logInUser(SessionUser sessionUser) {
        Player player = Loader.loadPlayerData(sessionUser.getPlayerId());
        WorldChunk chunk = worldList.get(player.getWorldId()).getWorldChunkList().get(player.getChunkCoord());
        players.put(player.getPlayerId(),chunk);
        chunk.joinPlayer(player.getPlayerId(),player);
    }

    public void logOutUser(SessionUser sessionUser) {
        WorldChunk chunk = players.remove(sessionUser.getPlayerId());
        Player player = chunk.leave(sessionUser.getPlayerId());
        Loader.savePlayer(player);
    }

    public void processMessage(SessionUser sessionUser, JSONObject object) {
        System.out.println(object.toString(1));
    }
}
