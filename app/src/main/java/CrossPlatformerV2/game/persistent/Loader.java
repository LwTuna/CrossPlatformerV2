package CrossPlatformerV2.game.persistent;

import CrossPlatformerV2.game.entities.Player;
import CrossPlatformerV2.game.utils.Coordinate;
import org.json.JSONObject;

import java.io.*;

public class Loader {

    public static SerChunkData loadChunkData(String path) throws IOException {
        return SerChunkData.fromJSON(loadJSON(path));

    }

    public static void saveChunkData(SerChunkData chunkData, String path) throws IOException {
        saveJSON(path, chunkData.toJSON());
    }

    public static void saveJSON(String path,JSONObject object) throws IOException {
        File file = new File(path);
        if(!file.exists()) file.createNewFile();

        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));

        bufferedWriter.write(object.toString());
        bufferedWriter.close();
    }

    public static JSONObject loadJSON(String path) throws IOException {
        File file = new File(path);
        if(!file.exists()) throw new FileNotFoundException();

        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        StringBuilder data = new StringBuilder();
        String line;

        while ((line = bufferedReader.readLine())!= null){
            data.append(line);
        }
        return new JSONObject(data.toString());
    }

    public static Player loadPlayerData(int userId) {
        return new Player(0,new Coordinate(0,0),new Coordinate(5,5),userId);//TODO
    }

    public static void savePlayer(Player player){
        //TODO
    }
}
