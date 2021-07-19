package CrossPlatformerV2.Server;

import CrossPlatformerV2.Server.RequestHandlers.LoginHandler;
import CrossPlatformerV2.Server.RequestHandlers.RequestHandler;
import CrossPlatformerV2.Server.sessions.SessionManager;
import CrossPlatformerV2.Server.sessions.SessionUser;
import CrossPlatformerV2.database.DatabaseManager;
import CrossPlatformerV2.game.Game;
import io.javalin.websocket.WsContext;
import io.javalin.websocket.WsHandler;
import io.javalin.websocket.WsMessageContext;
import io.javalin.websocket.WsMessageHandler;
import org.json.JSONObject;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class WebsocketHandler implements Consumer<WsHandler> {

    private Server server;
    private Game game;


    private Map<String, RequestHandler> handlers = new HashMap<>();


    public WebsocketHandler(Server server) {
        this.server = server;
        game = server.getGame();

        handlers.put("login", new LoginHandler());
    }

    @Override
    public void accept(WsHandler wsHandler) {
        wsHandler.onConnect(ctx -> {
            SessionUser sessionUser = SessionManager.getUserData(ctx.getSessionId());

            if(!sessionUser.isLoggedIn()){
                ctx.send(new JSONObject().put("key","websocketResponse").put("loggedIn", false).toString());
            }else{
                game.logInUser(sessionUser);
                ctx.send(new JSONObject().put("key","websocketResponse").put("loggedIn", true).toString());
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
            processMessage(sessionUser,new JSONObject(ctx.message()),ctx);
        });
    }

    private void processMessage(SessionUser sessionUser, JSONObject message, WsMessageContext ctx){
        try {
            handlers.getOrDefault(message.getString("key"),
                    (sessionUser1, message1, ctx1) ->
                            System.out.println("Cannot process Request: "+message.get("key"))).handleRequest(sessionUser,message,ctx);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
