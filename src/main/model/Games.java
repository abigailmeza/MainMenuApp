package model;

import java.util.ArrayList;

public class Games {
    private ArrayList<Game> savedGames;

    public Games() {
        savedGames = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds new game to savedGames
    public void createNewGame(Game newGame) {
        if (savedGames.isEmpty() || !hasSameName(newGame)) {
            savedGames.add(newGame);
        }
    }

    // EFFECTS: returns true if any game in savedGames has the same name as newGame
    //          otherwise, returns false
    public boolean hasSameName(Game newGame) {
        for (Game g: savedGames) {
            if (g.getName().equals(newGame.getName())) {
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

    // EFFECTS: returns number of games in savedGames
    public int size() {
        return savedGames.size();
    }

    // EFFECTS: returns true if g is in savedGames, otherwise false
    public boolean contains(Game g) {
        return savedGames.contains(g);
    }

}
