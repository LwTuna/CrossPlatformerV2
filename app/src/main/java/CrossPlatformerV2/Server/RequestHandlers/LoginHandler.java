package CrossPlatformerV2.Server.RequestHandlers;

import CrossPlatformerV2.Server.sessions.SessionManager;
import CrossPlatformerV2.Server.sessions.SessionUser;
import CrossPlatformerV2.database.DatabaseManager;
import io.javalin.websocket.WsMessageContext;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class LoginHandler implements RequestHandler{
    @Override
    public void handleRequest(SessionUser sessionUser, JSONObject message, WsMessageContext ctx) throws UnsupportedEncodingException {
        if(sessionUser.isLoggedIn()){
            ctx.send(new JSONObject().put("success",false).put("error","already Logged in").toString());
        }else {
            JSONObject request = new JSONObject(ctx.message());
            JSONObject userObject = DatabaseManager.login(request.getString("username"),request.getString("password"));
            if(userObject == null){
                ctx.send(new JSONObject().put("success",false).put("error","wrong username or password").toString());
            }else{
                sessionUser.setLoggedIn(userObject.getInt("id"), ctx.getSessionId());
                ctx.send(new JSONObject().put("success",true).toString());
            }
        }
    }

        /*
    javalin.post("login",ctx -> {
                else {
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
     */
}
