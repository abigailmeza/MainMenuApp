package ui;

import model.Game;
import model.GameList;

import java.util.ArrayList;
import java.util.Scanner;

// Citation: Based on the CPSC 210 TellerApp Example
// Main Menu application
public class MainMenuApp {
    private GameList savedGames;
    private Scanner input;

    // EFFECTS: Runs main menu
    public MainMenuApp() {
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
    // EFFECTS: processes user command
    private void processCommand(String command) {
        switch (command) {
            case "s":
                selectGame();
                break;
            case "c":
                createNewGame();
                break;
            case "d":
                deleteGame();
                break;
            default:
                System.out.println("Please enter a valid selection");
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes games
    private void init() {
        savedGames = new GameList();
        input = new Scanner(System.in);
    }

    // EFFECTS: displays menu options to user
    private void displayMenu() {
        System.out.println("Main Menu");
        System.out.println("\ts - Select Existing Game");
        System.out.println("\tc - Create New Game");
        System.out.println("\td - Delete Game");
        System.out.println("\tq - Quit Game");
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

    private void printSavedGames() {
        ArrayList<String> gameNames;
        gameNames = savedGames.viewGameNames();

        System.out.println("Select one from the following saved games:");
        for (String s : gameNames) {
            System.out.println("\t- " + s);
        }
    }

    // EFFECTS: displays game options to user
    private void displayGameOptions() {
        System.out.println("Select one from the following options:");
        System.out.println("\tp - Play Game");
        System.out.println("\tr - Rename Saved Game");
        System.out.println("\tl - Change Difficulty Level");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processGameOptions(String command, String gameName) {
        if ("p".equals(command)) {
            System.out.println("Launching Game: " + gameName + ".");
        } else if ("r".equals(command)) {
            renameGame(gameName);
        } else if ("l".equals(command)) {
            changeGameLevel(gameName);
        } else {
            System.out.println("Please enter a valid selection");
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

    // MODIFIES: this
    // EFFECTS: changes a game's difficulty level
    public void changeGameLevel(String gameName) {
        displayGameDifficulty();

        String selection = input.next();
        selection = selection.toLowerCase();

        switch (selection) {
            case "easy":
                System.out.println(gameName + "'s difficulty is set to easy.");
                savedGames.changeGameLevel(gameName, selection);
                break;
            case "medium":
                System.out.println(gameName + "'s difficulty is set to medium.");
                savedGames.changeGameLevel(gameName, selection);
                break;
            case "hard":
                savedGames.changeGameLevel(gameName, selection);
                System.out.println(gameName + "'s difficulty is set to hard.");
                break;
            default:
                System.out.println("Please enter a valid selection.");
                changeGameLevel(gameName);
                break;
        }
    }

    // EFFECTS: displays level of game difficulties to user
    private void displayGameDifficulty() {
        System.out.println("Select from one of the following game difficulties:");
        System.out.println("\t- Easy");
        System.out.println("\t- Medium");
        System.out.println("\t- Hard");
    }

    // EFFECTS: allows user to create a new game
    private void createNewGame() {
        Game g = new Game("Untitled");
        System.out.println("Name:");
        String name = input.next();
        if (savedGames.hasGameWithName(name)) {
            System.out.println("There exists a game with that name. Please select another one.");
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
}
