package CrossPlatformerV2.game.world.Tiles;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public abstract class Tile {

    protected int id;
    private static int nextId=-1;

    public Tile() {
        this.id = nextId++;
    }

    public abstract JSONObject getTileData();


    private static List<Tile> tileList = Arrays.asList(new Tile[]{new StaticTile(),new StaticTile(),new StaticTile(),new StaticTile(),new StaticTile()});

    public static Tile getFromId(int id){
        return tileList.get(id);
    }

}
