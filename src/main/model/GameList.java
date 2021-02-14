package model;

import java.util.ArrayList;

// Represents a list of saved games
public class GameList {
    ArrayList<Game> savedGames;

    public GameList() {
        savedGames = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds game to savedGames if game does not have identical name in savedGames
    public void addGame(Game g) {
        if (!hasGameWithName(g.getName()) && !savedGames.contains(g)) {
            savedGames.add(g);
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

    // MODIFIES: this
    // EFFECTS: changes game with given name's difficult level to lvl
    public void changeGameLevel(String name, String lvl) {
        for (Game g : savedGames) {
            if (hasGameWithName(name)) {
                g.setLevel(lvl);
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

}
