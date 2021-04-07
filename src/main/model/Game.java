package model;

import exceptions.IllegalNameException;
import org.json.JSONObject;
import persistence.Writable;

// Represents a saved game with a name and a difficulty level
public class Game implements Writable {
    private String name;              // name of saved game
    private String level;             // difficulty level

    // EFFECTS: if given name or level is an empty string, throws IllegalNameException
    //          otherwise, constructs a new game with given name and given difficulty level
    public Game(String name, String level) throws IllegalNameException {
        if (name.equals("") || level.equals("")) {
            throw new IllegalNameException();
        } else {
            this.name = name;
            this.level = level;
        }
    }

    // EFFECTS: if given name is an empty string, throws IllegalNameException
    //          otherwise, constructs new game with given name and difficulty level set as "easy"
    public Game(String name) throws IllegalNameException {
        if (name.equals("")) {
            throw new IllegalNameException();
        } else {
            this.name = name;
            this.level = "Easy";
        }
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
