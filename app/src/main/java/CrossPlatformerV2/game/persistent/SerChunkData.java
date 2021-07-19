package CrossPlatformerV2.game.persistent;

import CrossPlatformerV2.game.utils.Coordinate;
import CrossPlatformerV2.game.world.Tiles.Tile;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SerChunkData {

    int width,height;
    Coordinate chunkCoord;
    List<Tile[][]> layers;

    public SerChunkData(int width, int height, Coordinate chunkCoord, List<Tile[][]> layers) {
        this.width = width;
        this.height = height;
        this.chunkCoord = chunkCoord;
        this.layers = layers;
    }

    public JSONObject toJSON(){
        JSONObject object = new JSONObject();
        object.put("width",width);
        object.put("height",height);
        object.put("x",chunkCoord.getX());
        object.put("y",chunkCoord.getY());
        JSONArray layersJSON = new JSONArray();
        for (Tile[][] layer:layers) {
            JSONArray layerJSON = new JSONArray();
            for(int x = 0;x<layer.length;x++){
                JSONArray lx = new JSONArray();
                for(int y=0;y<layer[x].length;y++){
                    lx.put(layer[x][y].getTileData());
                }
                layerJSON.put(lx);
            }
            layersJSON.put(layerJSON);
        }
        object.put("layers",layersJSON);
        return object;
    }

    public static SerChunkData fromJSON(JSONObject object){
        int width = object.getInt("width");
        int height = object.getInt("height");
        JSONArray layersJSON = object.getJSONArray("layers");
        List<Tile[][]> layers = new ArrayList<>();
        for(int i=0;i<layersJSON.length();i++){
            Tile[][] tiles = new Tile[width][height];
            for(int x=0;x<width;x++){
                for(int y=0;y<height;y++){
                    //layers.layer.x.y
                    JSONObject tileData = layersJSON.getJSONArray(i).getJSONArray(x).getJSONObject(y);
                    tiles[x][y] = Tile.getFromId(tileData.getInt("id"));//TODO set Tile Data from dynamic Tiles
                }
            }
            layers.add(tiles);
        }
        return new SerChunkData(width,height,new Coordinate(object.getInt("x"),object.getInt("y")),layers);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Coordinate getChunkCoord() {
        return chunkCoord;
    }

    public List<Tile[][]> getLayers() {
        return layers;
    }
}
