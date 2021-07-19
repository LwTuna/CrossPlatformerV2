package CrossPlatformerV2.Server.sessions;

public class SessionUser {

    private boolean loggedIn = false;
    private int playerId;
    private String sessionId;

    public void setLoggedIn(int userId,String sessionId){
        loggedIn = true;
        this.playerId = userId;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public int getPlayerId() {
        return playerId;
    }
}
