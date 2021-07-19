package CrossPlatformerV2.game.entities;

import CrossPlatformerV2.game.utils.Coordinate;

public class Player {

    private int worldId;
    private Coordinate chunkCoord;

    private Coordinate playerCoord;
    private int playerId;

    public Player(int worldId, Coordinate chunkCoord, Coordinate playerCoord, int playerId) {
        this.worldId = worldId;
        this.chunkCoord = chunkCoord;
        this.playerCoord = playerCoord;
        this.playerId = playerId;
    }

    public int getWorldId() {
        return worldId;
    }

    public Coordinate getChunkCoord() {
        return chunkCoord;
    }

    public Coordinate getPlayerCoord() {
        return playerCoord;
    }

    public int getPlayerId() {
        return playerId;
    }
}
