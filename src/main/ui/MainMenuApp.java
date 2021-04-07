package ui;

import exceptions.IllegalNameException;
import model.Game;
import model.GameList;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

// Citation: Based on the CPSC 210 TellerApp Example
// Main Menu application
public class MainMenuApp {
    private static final String JSON_STORE = "./data/gamelist.json";
    private GameList savedGames;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: constructs main menu and runs application
    public MainMenuApp() {
        input = new Scanner(System.in);
        savedGames = new GameList("My saved games");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runMenu();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runMenu() {
        boolean keepGoing = true;
        String command;

        init();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }
        System.out.println("\nUntil next time...");
    }

    // MODIFIES: this
    // EFFECTS: initializes games
    private void init() {
        savedGames = new GameList("Saved Games");
        input = new Scanner(System.in);
    }


    // EFFECTS: displays menu options to user
    private void displayMenu() {
        System.out.println("Main Menu");
        System.out.println("\ts - Select Existing Game");
        System.out.println("\tc - Create New Game");
        System.out.println("\td - Delete Game");
        System.out.println("\tl - Load Games");
        System.out.println("\tq - Quit Game");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if ("s".equals(command)) {
            selectGame();
        } else if ("c".equals(command)) {
            createNewGame();
        } else if ("d".equals(command)) {
            deleteGame();
        } else if ("l".equals(command)) {
            loadGameList();
        } else {
            System.out.println("Please enter a valid selection");
        }
    }

    // EFFECTS: displays game options to user
    private void displayGameOptions() {
        System.out.println("Select one from the following options:");
        System.out.println("\tp - Play Game");
        System.out.println("\tr - Rename Saved Game");
        System.out.println("\tc - Change Difficulty Level");
        System.out.println("\ts - Save Games to File");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processGameOptions(String command, String gameName) {
        if ("p".equals(command)) {
            System.out.println("Launching Game: " + gameName + ".");
        } else if ("r".equals(command)) {
            renameGame(gameName);
        } else if ("c".equals(command)) {
            changeGameLevel(gameName);
        } else if ("s".equals(command)) {
            saveGameList();
        } else {
            System.out.println("Please enter a valid selection");
        }
    }

    private void printSavedGames() {
        ArrayList<String> gameNames;
        gameNames = savedGames.viewGameNames();

        System.out.println("Select one from the following saved games:");
        for (String s : gameNames) {
            System.out.println("\t- " + s);
        }
    }

    // EFFECTS: allows user to select an existing saved game
    private void selectGame() {
        String selection;

        if (savedGames.size() == 0) {
            System.out.println("You currently have no saved games.");
        } else {
            printSavedGames();
            selection = input.next();
            if (savedGames.hasGameWithName(selection)) {
                displayGameOptions();
                String command = input.next();
                processGameOptions(command, selection);
            } else {
                System.out.println("There is no existing game with that name.");
                selectGame();
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: changes a game's name
    public void renameGame(String gameName) {
        String selection;
        System.out.println("What would you like to rename your saved game?");
        selection = input.next();
        savedGames.changeGameName(gameName, selection);
    }

    // EFFECTS: displays level of game difficulties to user
    private void displayGameDifficulty() {
        System.out.println("Select from one of the following game difficulties:");
        System.out.println("\t- Easy");
        System.out.println("\t- Medium");
        System.out.println("\t- Hard");
    }

    // MODIFIES: this
    // EFFECTS: changes a game's difficulty level
    public void changeGameLevel(String gameName) {
        displayGameDifficulty();
        String selection = input.next();
        selection = selection.toLowerCase();

        switch (selection) {
            case "easy":
                System.out.println(gameName + "'s difficulty is set to Easy.");
                savedGames.getGameWithName(gameName).setLevel("Easy");
                break;
            case "medium":
                System.out.println(gameName + "'s difficulty is set to Medium.");
                savedGames.getGameWithName(gameName).setLevel("Medium");
                break;
            case "hard":
                System.out.println(gameName + "'s difficulty is set to Hard.");
                savedGames.getGameWithName(gameName).setLevel("Hard");
                break;
            default:
                System.out.println("Please enter a valid selection.");
                changeGameLevel(gameName);
                break;
        }
    }

    // EFFECTS: allows user to create a new game
    private void createNewGame() {
        Game g = null;
        try {
            g = new Game("Untitled", "Easy");
        } catch (IllegalNameException e) {
            System.out.println("Game name can't be empty!");
        }
        System.out.println("Name:");
        String name = input.next();
        if (savedGames.hasGameWithName(name)) {
            System.out.println("There already exists a game with that name. Please select another one.");
            createNewGame();
        } else {
            g.setName(name);
            savedGames.addGame(g);
            System.out.println("New game created: " + name);
        }
    }

    // EFFECTS: allows user to delete a saved game
    private void deleteGame() {
        String selection;

        if (savedGames.size() == 0) {
            System.out.println("You currently have no saved games.");
        } else {
            printSavedGames();
            selection = input.next();
            if (savedGames.hasGameWithName(selection)) {
                Game g = savedGames.getGameWithName(selection);
                savedGames.deleteGame(g);
                System.out.println(selection + " successfully deleted.");
            } else {
                System.out.println("There is no existing game with that name.");
            }
        }
    }

    // EFFECTS: saves savedGames to file
    private void saveGameList() {
        try {
            jsonWriter.open();
            jsonWriter.write(savedGames);
            jsonWriter.close();
            System.out.println("Saved " + savedGames.getGameListName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECT: loads saved games from file
    private void loadGameList() {
        try {
            savedGames = jsonReader.read();
            System.out.println("Loaded " + savedGames.getGameListName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        } catch (IllegalNameException e) {
            System.out.println("Game or level names cannot be empty");
        }
    }
}
