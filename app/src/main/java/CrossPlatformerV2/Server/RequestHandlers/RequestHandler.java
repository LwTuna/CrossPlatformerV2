package CrossPlatformerV2.Server.RequestHandlers;

import CrossPlatformerV2.Server.sessions.SessionUser;
import io.javalin.websocket.WsMessageContext;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public interface RequestHandler {

    void handleRequest(SessionUser sessionUser, JSONObject message, WsMessageContext ctx) throws Exception;

}
