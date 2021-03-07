package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents a saved game having a name, a game mode and a difficulty level
public class Game implements Writable {
    private String name;              // name of saved game
    private String level;             // difficulty level

    // REQUIRES: name has a non-zero length
    // EFFECTS: constructs a new game with given name and difficulty level set as "easy"
    public Game(String name, String level) {
        this.name = name;
        this.level = level;
    }

    // getters
    public String getName() {
        return name;
    }

    public String getLevel() {
        return level;
    }

    // setters
    public void setLevel(String level) {
        this.level = level;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("level", level);
        return json;
    }
}
