package CrossPlatformerV2.game.world.Tiles;

import org.json.JSONObject;

public class StaticTile extends Tile{


    @Override
    public JSONObject getTileData() {
        return new JSONObject().put("id",id);
    }
}
