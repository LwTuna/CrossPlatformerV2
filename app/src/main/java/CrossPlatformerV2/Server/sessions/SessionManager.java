package CrossPlatformerV2.Server.sessions;

import java.util.HashMap;
import java.util.Map;

public abstract class SessionManager {

    private static Map<String, SessionUser> users = new HashMap<>();

    public static SessionUser getUserData(String sessionId){
        return users.getOrDefault(sessionId,null);
    }

    public static SessionUser connectUser(String sessionId, SessionUser sessionUser){
        users.put(sessionId,sessionUser);
        return sessionUser;
    }

}
