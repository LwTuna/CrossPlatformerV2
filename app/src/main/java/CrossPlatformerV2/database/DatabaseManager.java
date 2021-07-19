package CrossPlatformerV2.database;

import org.json.JSONObject;

public class DatabaseManager {

    public static void createConnection(){

    }

    public static JSONObject login(String username, String password){
        return new JSONObject().put("id",0);
    }

    public static boolean register(String username,String password,String email){
        return true;
    }

}
