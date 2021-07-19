package CrossPlatformerV2.Server;

import CrossPlatformerV2.Server.sessions.SessionManager;
import CrossPlatformerV2.Server.sessions.SessionUser;
import CrossPlatformerV2.database.DatabaseManager;
import CrossPlatformerV2.game.Game;
import CrossPlatformerV2.game.world.World;
import io.javalin.Javalin;
import io.javalin.websocket.WsHandler;
import org.json.JSONObject;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;

public class Server{

    private Javalin javalin;
    private Game game;

    public Server() {
        DatabaseManager.createConnection();
        initGame();
        initServer();

    }

    private void initGame() {
        game = new Game(this);
        game.start();

    }

    private void initServer() {
        javalin = Javalin.create(config ->{
            config.addStaticFiles("/public");
        }).start(7070);

        javalin.ws("/game", new WebsocketHandler(this));

    }




    public void stopAll() {
        game.stop();
        javalin.stop();
    }

    public Game getGame() {
        return game;
    }
}
