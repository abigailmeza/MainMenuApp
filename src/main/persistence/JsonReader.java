package persistence;

import jdk.jfr.Category;
import model.Game;
import model.GameList;
import org.json.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Citation: Based on the CPSC 210 JsonSerializationDemo
// Represents a reader that reads game list from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads game list from file and returns it;
    //          throws IOException if an error occurs reading data from file
    public GameList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseGameList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses game list from Json object and returns it
    private GameList parseGameList(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        GameList gl = new GameList(name);
        addGames(gl, jsonObject);
        return gl;
    }

    // REQUIRES: gl
    // EFFECTS: parses games from JSON object and adds them to game list
    private void addGames(GameList gl, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("games");
        for (Object json : jsonArray) {
            JSONObject nextGame = (JSONObject) json;
            addGame(gl, nextGame);
        }
    }

    // MODIFIES: gl
    // EFFECTS: parses game from JSON object and adds it to workroom
    private void addGame(GameList gl, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String level = jsonObject.getString("level");
        Game game = new Game(name, level);
        gl.addGame(game);
    }
}
