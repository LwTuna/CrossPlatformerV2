package CrossPlatformerV2.Server;

import CrossPlatformerV2.Server.sessions.SessionManager;
import CrossPlatformerV2.Server.sessions.SessionUser;
import CrossPlatformerV2.game.Game;
import io.javalin.websocket.WsContext;
import io.javalin.websocket.WsHandler;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class WebsocketHandler implements Consumer<WsHandler> {

    private Server server;
    private Game game;


    public WebsocketHandler(Server server) {
        this.server = server;
        game = server.getGame();
    }

    @Override
    public void accept(WsHandler wsHandler) {
        wsHandler.onConnect(ctx -> {
            SessionUser sessionUser = SessionManager.getUserData(ctx.getSessionId());

            if(!sessionUser.isLoggedIn()){
                ctx.send(new JSONObject().put("key","websocketResponse").put("success", false).put("error","not logged in").toString());
                ctx.session.close();
                return;
            }else{
                game.logInUser(sessionUser);
                ctx.send(new JSONObject().put("key","websocketResponse").put("success", true).toString());
            }

        });
        wsHandler.onClose(ctx -> {
            SessionUser sessionUser = SessionManager.getUserData(ctx.getSessionId());

            if(sessionUser.isLoggedIn()){
                game.logOutUser(sessionUser);
            }
        });
        wsHandler.onMessage(ctx -> {
            SessionUser sessionUser = SessionManager.getUserData(ctx.getSessionId());
            game.processMessage(sessionUser,new JSONObject(ctx.message()));
        });
    }
}
