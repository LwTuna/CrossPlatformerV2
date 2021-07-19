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

        javalin.ws("game", new WebsocketHandler(this));
        javalin.post("login",ctx -> {
            String sessionId = ctx.req.getSession().getId();
            var userData = SessionManager.getUserData(sessionId);
            if(userData.isLoggedIn()){
                ctx.result(new JSONObject().put("success",false).put("error","already Logged in").toString());

            }else {
                JSONObject request = new JSONObject(URLDecoder.decode(ctx.queryString(), StandardCharsets.UTF_8.toString()));
                JSONObject userObject = DatabaseManager.login(request.getString("username"),request.getString("password"));
                if(userObject == null){
                    ctx.result(new JSONObject().put("success",false).put("error","wrong username or password").toString());
                }else{
                    userData.setLoggedIn(userObject.getInt("id"),sessionId);
                    ctx.status(200);
                    ctx.result(new JSONObject().put("success",true).toString());
                }
            }


        });
    }




    public void stopAll() {
        game.stop();
        javalin.stop();
    }

    public Game getGame() {
        return game;
    }
}
