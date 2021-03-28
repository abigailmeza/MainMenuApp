package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Represents a list of saved games
public class GameList implements Writable {
    private String name;
    private ArrayList<Game> savedGames;

    public GameList(String name) {
        this.name = name;
        savedGames = new ArrayList<>();
    }

    // EFFECTS: returns name of GameList
    public String getGameListName() {
        return name;
    }

    // EFFECTS: returns an unmodifiable list of games in this game list
    public List<Game> getGames() {
        return Collections.unmodifiableList(savedGames);
    }

    // MODIFIES: this
    // EFFECTS: adds game to savedGames if game does not have identical name in savedGames
    public void addGame(Game g) {
        if (!savedGames.contains(g)) {
            if (!hasGameWithName(g.getName())) {
                savedGames.add(g);
            }
        }
    }

    // EFFECTS: returns true if savedGames contains game with given name
    public boolean hasGameWithName(String name) {
        for (Game g : savedGames) {
            if (g.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: deletes game from savedGames
    public void deleteGame(Game g) {
        savedGames.remove(g);
    }

    // MODIFIES: this
    // EFFECTS: changes game with given name to newName
    public void changeGameName(String name, String newName) {
        for (Game g : savedGames) {
            if (hasGameWithName(name)) {
                g.setName(newName);
            }
        }
    }

    // EFFECTS: returns game in savedGames with given name
    public Game getGameWithName(String name) {
        for (Game g : savedGames) {
            if (g.getName().equals(name)) {
                return g;
            }
        }
        return null;
    }

    // EFFECTS: returns a list of all the game names in savedGames
    public ArrayList<String> viewGameNames() {
        ArrayList<String> gameNames = new ArrayList<>();

        for (Game g : savedGames) {
            gameNames.add(g.getName());
        }
        return gameNames;
    }

    // EFFECTS: returns size of savedGames
    public int size() {
        return savedGames.size();
    }

    // EFFECTS: returns true if g is in savedGames, otherwise returns false
    public boolean contains(Game g) {
        return savedGames.contains(g);
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("games", gamesToJson());
        return json;
    }

    private JSONArray gamesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Game g : savedGames) {
            jsonArray.put(g.toJson());
        }

        return jsonArray;
    }

}
