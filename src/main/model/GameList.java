package model;

import java.util.ArrayList;

public class GameList {
    ArrayList<Game> savedGames;

    public GameList() {
        savedGames = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds new game to savedGames
    public void addGame(Game g) {
        if (!savedGames.contains(g) && !hasGame(g.getName())) {
            savedGames.add(g);
        }
    }

    // EFFECTS: returns true if savedGames contains game with given name
    public boolean hasGame(String name) {
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

    // REQUIRES: savedGames must have game at position i
    // MODIFIES: this, Game g
    // EFFECTS: changes g's name in savedGames at position i
    public void changeGameName(Integer i, String name) {
        Game g = savedGames.get(i);
        g.setName(name);
    }

    // REQUIRES: savedGames must have game at position i
    // MODIFIES: this, Game g
    // EFFECTS: changes g's difficulty level in savedGames at position i
    public void changeGameLevel(Integer i, String level) {
        Game g = savedGames.get(i);
        g.setLevel(level);
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

    // EFFECTS: returns number of games in savedGames
    public int size() {
        return savedGames.size();
    }

    // EFFECTS: returns true if g is in savedGames, otherwise false
    public boolean contains(Game g) {
        return savedGames.contains(g);
    }

}
