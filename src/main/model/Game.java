package model;

// Represents a saved game having a name, a game mode and a difficulty level
public class Game {
    private String name;              // name of saved game
    private String level;             // difficulty level

    // REQUIRES: name has a non-zero length
    //           no game can share the same name
    // EFFECTS: constructs a new game with given name and difficulty level set as "easy"
    public Game(String name) {
        this.name = name;
        level = "easy";
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

}
